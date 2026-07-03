package com.Adawasda.skBetterModel.elements.expressions;

import org.bukkit.entity.Entity;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.animation.RunningAnimation;
import kr.toxicity.model.api.tracker.EntityTracker;
import com.Adawasda.skBetterModel.utils.EntityTrackerController;

@SuppressWarnings("unchecked")
public class ExprActiveAnimation extends SimpleExpression<RunningAnimation> {

    private Expression<Object> expr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprActiveAnimation.class, RunningAnimation.class)
                .supplier(ExprActiveAnimation::new)
                .addPatterns("[active|running] animation of %object%")
                .build());
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.expr = (Expression<Object>) expressions[0];
        return true;
    }

    @Override
    protected RunningAnimation @Nullable [] get(Event event) {
        Object obj = expr.getSingle(event);
        if (obj == null) return null;

        EntityTracker tracker;
        if (obj instanceof EntityTracker entityTracker) {
            tracker = entityTracker;
        } else if (obj instanceof Entity entity) {
            tracker = new EntityTrackerController(entity).getTracker();
        } else {
            return null;
        }

        if (tracker == null) return null;

        RunningAnimation animation = tracker.getPipeline().runningAnimation();
        if (animation == null) return null;

        return new RunningAnimation[]{animation};
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
        return "active animation of " + expr.toString(event, debug);
    }
}