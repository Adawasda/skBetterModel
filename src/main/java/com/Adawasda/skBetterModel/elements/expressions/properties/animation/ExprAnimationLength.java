package com.Adawasda.skBetterModel.elements.expressions.properties.animation;

import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;

public class ExprAnimationLength extends SimplePropertyExpression<BlueprintAnimation, Float> {

    public static void register() {
        PropertyExpression.register(ExprAnimationLength.class, Float.class, "animation length", "animation");
    }

    @Override
    public Class<? extends Float> getReturnType() {
        return Float.class;
    }

    @Override
    public @Nullable Float convert(BlueprintAnimation animation) {
        return animation != null ? animation.length() : null;
    }

    @Override
    protected String getPropertyName() {
        return "animation length";
    }
}
