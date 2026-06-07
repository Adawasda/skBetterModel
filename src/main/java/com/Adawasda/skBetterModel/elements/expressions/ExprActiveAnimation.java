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
import utils.EntityTrackerController;

@SuppressWarnings("unchecked")
public class ExprActiveAnimation extends SimpleExpression<RunningAnimation> {

    private Expression<Object> entityTrackerExpr;
    private Expression<Entity> entityExpr;
    int matchedPattern;

	public static void register(@NotNull SyntaxRegistry registry) {
		registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprActiveAnimation.class, RunningAnimation.class)
				.supplier(ExprActiveAnimation::new)
				.addPatterns(
                    "[active] animation of %object%",
                    "[active] animation of %entity%"

                ) // [example] is optional in syntax
				.build());
	}

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class getReturnType() {
        return RunningAnimation.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.matchedPattern = matchedPattern;
        
        switch (matchedPattern) {
            case 0 -> this.entityTrackerExpr = (Expression<Object>) expressions[0];
            case 1 -> this.entityExpr = (Expression<Entity>) expressions[0];
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "active animation of " + this.entityTrackerExpr.toString(event, debug);
    }

    @Override
    protected RunningAnimation @Nullable [] get(Event event) {
        EntityTracker entityTracker;
        switch (matchedPattern) {
            case 1 -> entityTracker = (EntityTracker) this.entityTrackerExpr.getSingle(event);
            default -> entityTracker = new EntityTrackerController(entityExpr.getSingle(event)).getTracker();
        }
        
        
        RunningAnimation animation = entityTracker.getPipeline().runningAnimation();
        return new RunningAnimation[]{animation};

    }
    
}
