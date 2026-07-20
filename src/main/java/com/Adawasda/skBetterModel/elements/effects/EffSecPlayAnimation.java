package com.Adawasda.skBetterModel.elements.effects;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.EffectSection;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.util.SectionUtils;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

import kr.toxicity.model.api.animation.AnimationIterator;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;
import kr.toxicity.model.api.tracker.EntityTracker;

import com.Adawasda.skBetterModel.core.TrackerController;
import com.Adawasda.skBetterModel.utils.PluginConfig;

public class EffSecPlayAnimation extends EffectSection {

    public static class AnimationModifierEvent extends Event {

        private final AnimationModifier.Builder modifierBuilder;
        private final BlueprintAnimation animation;

        public AnimationModifierEvent(AnimationModifier.Builder builder, BlueprintAnimation animation) {
            this.modifierBuilder = builder;
            this.animation = animation;
        }

        public AnimationModifier.Builder getModifierBuilder() { return modifierBuilder; }
        public BlueprintAnimation getAnimation() { return animation; }
        public AnimationModifier buildModifier() { return modifierBuilder.build(); }

        @Override
        @NotNull
        public HandlerList getHandlers() {
            throw new IllegalStateException();
        }
    }

    private Expression<String> animationNameExpr;
    private Expression<Object> targetExpr;
    private Expression<Boolean> forcedExpr;
    private boolean playOnce;

    @Nullable
    private Trigger trigger;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.SECTION, SyntaxInfo.builder(EffSecPlayAnimation.class)
                .supplier(EffSecPlayAnimation::new)
                .addPatterns(
                        "play [the] [bm|bettermodel] animation %string% (for|to) %object% [with force %-boolean%]",
                        "play [the] [bm|bettermodel] animation %string% (for|to) %object% with play once [with force %-boolean%]"
                )
                .build());

        EventValues.registerEventValue(AnimationModifierEvent.class, AnimationModifier.Builder.class,
                AnimationModifierEvent::getModifierBuilder);
        EventValues.registerEventValue(AnimationModifierEvent.class, BlueprintAnimation.class,
                AnimationModifierEvent::getAnimation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult,
                        @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        this.playOnce = (matchedPattern == 1);
        this.animationNameExpr = (Expression<String>) expressions[0];
        this.targetExpr = (Expression<Object>) expressions[1];
        this.forcedExpr = (Expression<Boolean>) expressions[2];

        if (sectionNode != null) {
            trigger = SectionUtils.loadLinkedCode("play animation", (beforeLoading, afterLoading) ->
                    loadCode(sectionNode, "play animation", beforeLoading, afterLoading, AnimationModifierEvent.class));
            return trigger != null;
        }

        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        boolean isForced = (forcedExpr != null && Boolean.TRUE.equals(forcedExpr.getSingle(event)));

        TrackerController controller = resolveController(event);
        if (controller == null || !controller.isValid()) return super.walk(event, false);

        String animName = animationNameExpr.getSingle(event);
        if (animName == null) return super.walk(event, false);

        PluginConfig config = PluginConfig.get();
        AnimationModifier.Builder builder = AnimationModifier.builder()
                .start(config.getStart())
                .end(config.getEnd())
                .speed(config.getSpeed())
                .override(config.isOverride());

        if (playOnce) {
            builder.type(AnimationIterator.Type.PLAY_ONCE);
        }

        if (trigger != null) {
            BlueprintAnimation blueprint = controller.getAnimations() != null
                    ? controller.getAnimations().get(animName) : null;
            AnimationModifierEvent modEvent = new AnimationModifierEvent(builder, blueprint);
            Variables.withLocalVariables(event, modEvent, () -> TriggerItem.walk(trigger, modEvent));
        }

        controller.animate(animName, builder.build(), isForced);
        return super.walk(event, false);
    }

    private @Nullable TrackerController resolveController(Event event) {
        Object target = targetExpr.getSingle(event);
        if (target == null) return null;

        if (target instanceof EntityTracker tracker)
            return TrackerController.wrap(tracker);
        if (target instanceof Entity entity)
            return TrackerController.fromExisting(entity);
        return null;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "play animation " + animationNameExpr.toString(event, debug)
                + " to " + targetExpr.toString(event, debug);
    }
}
