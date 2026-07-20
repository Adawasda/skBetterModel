package com.Adawasda.skBetterModel.elements.expressions;

import java.util.List;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.tracker.EntityTrackerRegistry;

public class ExprAllEntityTrackers extends SimpleExpression<EntityTracker> {

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprAllEntityTrackers.class, EntityTracker.class)
                        .supplier(ExprAllEntityTrackers::new)
                        .addPatterns("all [bm|bettermodel] entity trackers")
                        .build());
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<EntityTracker> getReturnType() {
        return EntityTracker.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "all entity trackers";
    }

    @Override
    protected EntityTracker[] get(Event event) {
        List<EntityTracker> trackers = EntityTrackerRegistry.registries().stream()
                .flatMap(registry -> registry.trackers().stream())
                .toList();
        return trackers.toArray(new EntityTracker[0]);
    }
}
