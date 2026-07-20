package com.Adawasda.skBetterModel.elements.effects;

import org.bukkit.entity.Player;
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
import kr.toxicity.model.api.tracker.EntityHideOption;

import com.Adawasda.skBetterModel.core.TrackerController;

public class EffDisguise extends Effect {

    private Expression<Player> playerExpr;
    private Expression<EntityTracker> trackerExpr;
    private boolean undisguise;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EFFECT, SyntaxInfo.builder(EffDisguise.class)
                .supplier(EffDisguise::new)
                .addPatterns(
                        "disguise %player% as [bm|bettermodel] %entitytracker%",
                        "undisguise %player% from [bm|bettermodel] %entitytracker%"
                )
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.playerExpr = (Expression<Player>) expressions[0];
        this.trackerExpr = (Expression<EntityTracker>) expressions[1];
        this.undisguise = (matchedPattern == 1);
        return true;
    }

    @Override
    protected void execute(Event event) {
        Player player = playerExpr.getSingle(event);
        EntityTracker tracker = trackerExpr.getSingle(event);
        if (player == null || tracker == null) return;

        TrackerController controller = TrackerController.wrap(tracker);
        if (!controller.isValid()) return;

        if (undisguise) {
            tracker.close();
        } else {
            tracker.update(
                    kr.toxicity.model.api.tracker.TrackerUpdateAction.glow(false)
            );
            EntityHideOption hideOption = EntityHideOption.builder()
                    .or(new EntityHideOption(true, true, true, true))
                    .build();
            tracker.forceUpdate(true);
        }
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return (undisguise ? "undisguise " : "disguise ")
                + playerExpr.toString(event, debug)
                + (undisguise ? " from " : " as ")
                + trackerExpr.toString(event, debug);
    }
}
