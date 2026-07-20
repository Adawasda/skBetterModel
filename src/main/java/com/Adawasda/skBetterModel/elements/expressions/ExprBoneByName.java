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

public class ExprBoneByName extends SimpleExpression<RenderedBone> {

    private Expression<String> nameExpr;
    private Expression<Object> targetExpr;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprBoneByName.class, RenderedBone.class)
                        .supplier(ExprBoneByName::new)
                        .addPatterns("[bm|bettermodel] bone %string% of %object%")
                        .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.nameExpr = (Expression<String>) expressions[0];
        this.targetExpr = (Expression<Object>) expressions[1];
        return true;
    }

    @Override
    protected RenderedBone @Nullable [] get(Event event) {
        String name = nameExpr.getSingle(event);
        Object target = targetExpr.getSingle(event);
        if (name == null || target == null) return null;

        TrackerController controller;
        if (target instanceof EntityTracker tracker) {
            controller = TrackerController.wrap(tracker);
        } else if (target instanceof Entity entity) {
            controller = TrackerController.fromExisting(entity);
        } else {
            return null;
        }

        RenderedBone bone = controller.getBone(name);
        return bone != null ? new RenderedBone[]{bone} : null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends RenderedBone> getReturnType() {
        return RenderedBone.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "bone " + nameExpr.toString(event, debug) + " of " + targetExpr.toString(event, debug);
    }
}
