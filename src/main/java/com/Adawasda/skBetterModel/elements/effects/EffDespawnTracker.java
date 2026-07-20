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

public class EffDespawnTracker extends Effect {

    private Expression<Object> targetExpr;
    private boolean fullClose;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffDespawnTracker.class)
                .supplier(EffDespawnTracker::new)
                .addPatterns(
                        "despawn [the] [bm|bettermodel] tracker (of|from) %object%",
                        "close [the] [bm|bettermodel] tracker (of|from) %object%"
                )
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.targetExpr = (Expression<Object>) expressions[0];
        this.fullClose = (matchedPattern == 1);
        return true;
    }

    @Override
    protected void execute(Event event) {
        Object target = targetExpr.getSingle(event);
        if (target == null) return;

        TrackerController controller;
        if (target instanceof EntityTracker tracker) {
            controller = TrackerController.wrap(tracker);
        } else if (target instanceof Entity entity) {
            controller = TrackerController.fromExisting(entity);
        } else {
            return;
        }

        if (fullClose) {
            controller.close();
        } else {
            controller.despawn();
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return (fullClose ? "close" : "despawn") + " tracker of " + targetExpr.toString(event, debug);
    }
}
