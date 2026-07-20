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
import kr.toxicity.model.api.bone.RenderedBone;
import kr.toxicity.model.api.tracker.EntityTracker;

import java.util.Collection;

public class ExprTrackerBones extends SimpleExpression<RenderedBone> {

    private Expression<Object> targetExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprTrackerBones.class, RenderedBone.class)
                        .supplier(ExprTrackerBones::new)
                        .addPatterns("[all] bones of %object%")
                        .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.targetExpr = (Expression<Object>) expressions[0];
        return true;
    }

    @Override
    protected RenderedBone @Nullable [] get(Event event) {
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

        Collection<RenderedBone> bones = controller.getBones();
        return bones != null ? bones.toArray(new RenderedBone[0]) : null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends RenderedBone> getReturnType() {
        return RenderedBone.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "bones of " + targetExpr.toString(event, debug);
    }
}
