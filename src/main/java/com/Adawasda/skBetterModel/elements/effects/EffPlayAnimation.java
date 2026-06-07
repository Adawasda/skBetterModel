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
import utils.EntityTrackerController;

@SuppressWarnings("unchecked")
public class EffPlayAnimation extends Effect {

    private Expression<String> animationName;
    private Expression<Object> entityTracker;
    private Expression<Entity> entity;
    private int matchedPattern;
    private EntityTrackerController controller;

	public static void register(@NotNull SyntaxRegistry registry) {
		registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffPlayAnimation.class)
				.supplier(EffPlayAnimation::new)
				.addPatterns(
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object%",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %object% with play once",

                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity%",
                    "play [the] [bm|bettermodel] animation %string% [for|to] %entity% with play once"
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

        
        this.matchedPattern = matchedPattern;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "play animation " + this.animationName + " to " + this.entityTracker.toString();
    }

    @Override
    protected void execute(Event event) {
        if (matchedPattern == 0 || matchedPattern == 1) {
            this.controller = new EntityTrackerController((EntityTracker) this.entityTracker.getSingle(event));
        } else {
            this.controller = new EntityTrackerController(this.entity.getSingle(event));
        }

        if (this.controller.getTracker() == null) {
            throw new RuntimeException("No entity tracker found for " + this.entity.toString(event, true));
        }

        if (matchedPattern == 0 || matchedPattern == 2) {
            controller.getTracker().animate(this.animationName.getSingle(event), AnimationModifier.DEFAULT);
        } else {
            controller.getTracker().animate(this.animationName.getSingle(event), AnimationModifier.DEFAULT_WITH_PLAY_ONCE);
        }
    }
    
}
