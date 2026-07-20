package com.Adawasda.skBetterModel.elements.effects;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import kr.toxicity.model.api.tracker.EntityTracker;

import com.Adawasda.skBetterModel.core.TrackerController;

public class EffStopAnimation extends Effect {

    private Expression<String> animationNameExpr;
    private Expression<Object> targetExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffStopAnimation.class)
                .supplier(EffStopAnimation::new)
                .addPatterns("stop [the] [bm|bettermodel] animation %string% (of|for|from) %object%")
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.animationNameExpr = (Expression<String>) expressions[0];
        this.targetExpr = (Expression<Object>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String name = animationNameExpr.getSingle(event);
        Object target = targetExpr.getSingle(event);
        if (name == null || target == null) return;

        EntityTracker tracker = null;
        if (target instanceof EntityTracker et) {
            tracker = et;
        } else if (target instanceof Entity entity) {
            TrackerController controller = TrackerController.fromExisting(entity);
            tracker = controller.getTracker();
        }

        if (tracker != null) {
            tracker.stopAnimation(name);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "stop animation " + animationNameExpr.toString(event, debug)
                + " of " + targetExpr.toString(event, debug);
    }
}
