package com.Adawasda.skBetterModel.elements.expressions.properties.animation;

import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.animation.RunningAnimation;

public class ExprAnimationName extends SimplePropertyExpression<RunningAnimation, String> {

    public static void register() {
        PropertyExpression.register(ExprAnimationName.class, String.class, "animation name", "runninganimation");
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @Nullable String convert(RunningAnimation animation) {
        return animation != null ? animation.name() : null;
    }

    @Override
    protected String getPropertyName() {
        return "animation name";
    }
}
