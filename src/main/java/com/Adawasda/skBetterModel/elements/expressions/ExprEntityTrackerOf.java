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
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.tracker.EntityTrackerRegistry;

public class ExprEntityTrackerOf extends SimpleExpression<EntityTracker> {

    private Expression<Entity> entityExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprEntityTrackerOf.class, EntityTracker.class)
                        .supplier(ExprEntityTrackerOf::new)
                        .addPatterns("[bm|bettermodel] entity tracker of %entity%")
                        .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.entityExpr = (Expression<Entity>) expressions[0];
        return true;
    }

    @Override
    protected EntityTracker @Nullable [] get(Event event) {
        Entity entity = entityExpr.getSingle(event);
        if (entity == null) return null;

        EntityTrackerRegistry registry = EntityTrackerRegistry.registry(entity.getUniqueId());
        if (registry == null) return null;

        EntityTracker tracker = registry.first();
        return tracker != null ? new EntityTracker[]{tracker} : null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EntityTracker> getReturnType() {
        return EntityTracker.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "entity tracker of " + entityExpr.toString(event, debug);
    }
}
