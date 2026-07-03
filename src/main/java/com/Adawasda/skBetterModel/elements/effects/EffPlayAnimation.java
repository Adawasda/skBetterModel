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
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.tracker.EntityTracker;
import com.Adawasda.skBetterModel.utils.EntityTrackerController;

@SuppressWarnings("unchecked")
public class EffPlayAnimation extends Effect {

    private Expression<String> animationName;
    private Expression<Object> entityTracker;
    private Expression<Entity> entity;
    private int matchedPattern;
    private EntityTrackerController controller;
    private Expression<Boolean> isForcedExpr;
    private Boolean isForced;

	public static void register(@NotNull SyntaxRegistry registry) {
		registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffPlayAnimation.class)
				.supplier(EffPlayAnimation::new)
				.addPatterns(
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object% [with force %-boolean%]",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object% with play once [with force %-boolean%]",

                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity% [with force %-boolean%]",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity% with play once [with force %-boolean%]"
                )
				.build());
	}

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.animationName = (Expression<String>) expressions[0];
        
        if (matchedPattern == 0 || matchedPattern == 1) { // matchedPattern == 1 is for "play animation %string% for %entity%"
            this.entityTracker = (Expression<Object>) expressions[1];
        } else {
            this.entity = (Expression<Entity>) expressions[1];
        }

        isForcedExpr = (Expression<Boolean>) expressions[2];

        
        this.matchedPattern = matchedPattern;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String target = entityTracker != null ? entityTracker.toString(event, debug) : entity.toString(event, debug);
        return "play animation " + animationName.toString(event, debug) + " to " + target;
    }

    @Override
    protected void execute(Event event) {
        if (isForcedExpr != null && isForcedExpr.getSingle(event) != null) 
            isForced = isForcedExpr.getSingle(event);
        else 
            isForced = false;

        if (matchedPattern == 0 || matchedPattern == 1) {
            Object obj = this.entityTracker.getSingle(event);
            if (obj instanceof EntityTracker tracker) {
                this.controller = new EntityTrackerController(tracker);
            } else if (obj instanceof Entity entity) {
                this.controller = new EntityTrackerController(entity);
            } else {
                throw new RuntimeException("Expected an EntityTracker or Entity, got: " + (obj == null ? "null" : obj.getClass().getName()));
            }
        } else {
            this.controller = new EntityTrackerController(this.entity.getSingle(event));
        }

        if (this.controller.getTracker() == null) {
            throw new RuntimeException("No entity tracker found for entity");
        }

        if (matchedPattern == 0 || matchedPattern == 2) {
            controller.animate(this.animationName.getSingle(event), AnimationModifier.DEFAULT, isForced);
        } else {
            controller.animate(this.animationName.getSingle(event), AnimationModifier.DEFAULT_WITH_PLAY_ONCE, isForced);
        }
    }
    
}
