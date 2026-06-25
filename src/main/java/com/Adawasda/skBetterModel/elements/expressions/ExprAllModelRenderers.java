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
import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.data.renderer.ModelRenderer;

public class ExprAllModelRenderers extends SimpleExpression<ModelRenderer> {

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprAllModelRenderers.class, ModelRenderer.class)
                .supplier(ExprAllModelRenderers::new)
                .addPatterns("all [bm|bettermodel] [renderer] models")
                .build());
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    protected ModelRenderer @Nullable [] get(Event event) {
        return BetterModel.models().toArray(new ModelRenderer[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ModelRenderer> getReturnType() {
        return ModelRenderer.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "all blueprints";
    }
}