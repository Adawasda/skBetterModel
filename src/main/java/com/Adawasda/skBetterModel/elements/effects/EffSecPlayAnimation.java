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
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;
import kr.toxicity.model.api.tracker.EntityTracker;
import com.Adawasda.skBetterModel.utils.EntityTrackerController;

@SuppressWarnings("unchecked")
public class EffSecPlayAnimation extends EffectSection {

    public static class AnimationModifierEvent extends Event {

        private final AnimationModifier modifier;
        private final BlueprintAnimation animation;

        public AnimationModifierEvent(AnimationModifier modifier, BlueprintAnimation animation) {
            this.modifier = modifier;
            this.animation = animation;
        }

        public AnimationModifier getModifier() { return modifier; }
        public BlueprintAnimation getAnimation() { return animation; }

        @Override
        @NotNull
        public HandlerList getHandlers() {
            throw new IllegalStateException();
        }
    }

    private Expression<String> animationName;
    private Expression<Object> entityTrackerExpr;
    private Expression<Entity> entityExpr;
    private Expression<Boolean> isForcedExpr;
    private int matchedPattern;

    @Nullable
    private Trigger trigger;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.SECTION, SyntaxInfo.builder(EffSecPlayAnimation.class)
                .supplier(EffSecPlayAnimation::new)
                .addPatterns(
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object% [with force %-boolean%]",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object% with play once [with force %-boolean%]",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity% [with force %-boolean%]",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity% with play once [with force %-boolean%]"
                )
                .build());

        EventValues.registerEventValue(AnimationModifierEvent.class, AnimationModifier.class, AnimationModifierEvent::getModifier);
        EventValues.registerEventValue(AnimationModifierEvent.class, BlueprintAnimation.class, AnimationModifierEvent::getAnimation);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult, @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
        this.matchedPattern = matchedPattern;
        this.animationName = (Expression<String>) expressions[0];

        if (matchedPattern == 0 || matchedPattern == 1) {
            this.entityTrackerExpr = (Expression<Object>) expressions[1];
        } else {
            this.entityExpr = (Expression<Entity>) expressions[1];
        }

        this.isForcedExpr = (Expression<Boolean>) expressions[2];

        if (sectionNode != null) {
            trigger = SectionUtils.loadLinkedCode("play animation", (beforeLoading, afterLoading)
                    -> loadCode(sectionNode, "play animation", beforeLoading, afterLoading, AnimationModifierEvent.class));
            return trigger != null;
        }

        return true;
    }

    @Override
    protected @Nullable TriggerItem walk(Event event) {
        boolean isForced = isForcedExpr != null && isForcedExpr.getSingle(event) != null
                ? isForcedExpr.getSingle(event)
                : false;

        EntityTrackerController controller;

        if (matchedPattern == 0 || matchedPattern == 1) {
            Object obj = entityTrackerExpr.getSingle(event);
            if (obj instanceof EntityTracker tracker) {
                controller = new EntityTrackerController(tracker);
            } else if (obj instanceof Entity entity) {
                controller = new EntityTrackerController(entity);
            } else {
                return super.walk(event, false);
            }
        } else {
            Entity entity = entityExpr.getSingle(event);
            if (entity == null) return super.walk(event, false);
            controller = new EntityTrackerController(entity);
        }

        if (controller.getTracker() == null) return super.walk(event, false);

        String animName = animationName.getSingle(event);
        if (animName == null) return super.walk(event, false);

        AnimationModifier modifier = (matchedPattern == 0 || matchedPattern == 2)
                ? AnimationModifier.DEFAULT
                : AnimationModifier.DEFAULT_WITH_PLAY_ONCE;

        if (trigger != null) {
            BlueprintAnimation blueprintAnimation = controller.getAnimations() != null
                    ? controller.getAnimations().get(animName)
                    : null;

            AnimationModifierEvent modifierEvent = new AnimationModifierEvent(modifier, blueprintAnimation);
            Variables.withLocalVariables(event, modifierEvent, () -> TriggerItem.walk(trigger, modifierEvent));
            modifier = modifierEvent.getModifier();
        }

        controller.animate(animName, modifier, isForced);

        return super.walk(event, false);
    }
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String target = entityTrackerExpr != null
                ? entityTrackerExpr.toString(event, debug)
                : entityExpr.toString(event, debug);
        return "play animation " + animationName.toString(event, debug) + " to " + target;
    }
}