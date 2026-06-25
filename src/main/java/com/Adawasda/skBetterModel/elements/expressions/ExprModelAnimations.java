package com.Adawasda.skBetterModel.elements.expressions;

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

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprModelAnimations.class, BlueprintAnimation.class)
                .supplier(ExprModelAnimations::new)
                .addPatterns("[all] animations of %modelrenderer%")
                .build());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.modelExpr = (Expression<ModelRenderer>) expressions[0];
        return true;
    }

    @Override
    protected BlueprintAnimation @Nullable [] get(Event event) {
        ModelRenderer model = modelExpr.getSingle(event);
        if (model == null) return null;

        return model.animations().values().toArray(new BlueprintAnimation[0]);
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
        return "animations of " + modelExpr.toString(event, debug);
    }
}