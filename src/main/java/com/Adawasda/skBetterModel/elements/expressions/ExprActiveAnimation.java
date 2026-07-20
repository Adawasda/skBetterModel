package com.Adawasda.skBetterModel.elements.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import com.Adawasda.skBetterModel.core.TrackerController;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.animation.RunningAnimation;
import kr.toxicity.model.api.tracker.EntityTracker;

public class ExprActiveAnimation extends SimpleExpression<RunningAnimation> {

    private Expression<Object> targetExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprActiveAnimation.class, RunningAnimation.class)
                        .supplier(ExprActiveAnimation::new)
                        .addPatterns("[active|running] animation of %object%")
                        .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.targetExpr = (Expression<Object>) expressions[0];
        return true;
    }

    @Override
    protected RunningAnimation @Nullable [] get(Event event) {
        Object target = targetExpr.getSingle(event);
        if (target == null) return null;

        TrackerController controller;
        if (target instanceof EntityTracker tracker) {
            controller = TrackerController.wrap(tracker);
        } else if (target instanceof Entity entity) {
            controller = TrackerController.fromExisting(entity);
        } else {
            return null;
        }

        RunningAnimation animation = controller.getRunningAnimation();
        return animation != null ? new RunningAnimation[]{animation} : null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends RunningAnimation> getReturnType() {
        return RunningAnimation.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "active animation of " + targetExpr.toString(event, debug);
    }
}
