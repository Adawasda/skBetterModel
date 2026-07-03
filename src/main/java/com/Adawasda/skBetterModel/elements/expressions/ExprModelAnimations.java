package com.Adawasda.skBetterModel.elements.expressions;

import kr.toxicity.model.api.tracker.EntityTracker;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;
import kr.toxicity.model.api.data.renderer.ModelRenderer;

public class ExprModelAnimations extends SimpleExpression<BlueprintAnimation> {

    private Expression<ModelRenderer> modelExpr;
    private Expression<EntityTracker> trackerExpr;
    private int matchedPattern;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprModelAnimations.class, BlueprintAnimation.class)
                .supplier(ExprModelAnimations::new)
                .addPatterns(
                    "[all] animations of %modelrenderer%",
                    "[all] animations of %entitytracker%"
                )
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.matchedPattern = matchedPattern;
        if (matchedPattern == 0) {
            this.modelExpr = (Expression<ModelRenderer>) expressions[0];
        } else {
            this.trackerExpr = (Expression<EntityTracker>) expressions[0];
        }
        return true;
    }

    @Override
    protected BlueprintAnimation @Nullable [] get(Event event) {
        if (matchedPattern == 0) {
            ModelRenderer model = modelExpr.getSingle(event);
            if (model == null) return null;
            return model.animations().values().toArray(new BlueprintAnimation[0]);
        } else {
            EntityTracker tracker = trackerExpr.getSingle(event);
            if (tracker == null) return null;
            var animations = tracker.renderer().animations();
            if (animations == null) return null;
            return animations.values().toArray(new BlueprintAnimation[0]);
        }
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends BlueprintAnimation> getReturnType() {
        return BlueprintAnimation.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        String target = matchedPattern == 0
            ? modelExpr.toString(event, debug)
            : trackerExpr.toString(event, debug);
        return "animations of " + target;
    }
}