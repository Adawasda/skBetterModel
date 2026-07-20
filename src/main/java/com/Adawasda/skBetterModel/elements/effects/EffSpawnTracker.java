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

public class EffSpawnTracker extends Effect {

    private Expression<String> modelExpr;
    private Expression<Entity> entityExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffSpawnTracker.class)
                .supplier(EffSpawnTracker::new)
                .addPatterns("spawn [bm|bettermodel] [tracker] %string% (on|for|to) %entity%")
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.modelExpr = (Expression<String>) expressions[0];
        this.entityExpr = (Expression<Entity>) expressions[1];
        return true;
    }

    @Override
    protected void execute(Event event) {
        String model = modelExpr.getSingle(event);
        Entity entity = entityExpr.getSingle(event);
        if (model == null || entity == null) return;

        TrackerController controller = TrackerController.fromModel(entity, model);
        if (!controller.isValid()) return;

        EntityTracker tracker = controller.getTracker();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "spawn tracker " + modelExpr.toString(event, debug)
                + " on " + entityExpr.toString(event, debug);
    }
}
