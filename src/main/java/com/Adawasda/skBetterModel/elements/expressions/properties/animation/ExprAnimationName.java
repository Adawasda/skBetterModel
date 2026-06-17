package com.Adawasda.skBetterModel.elements.expressions.properties.animation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;


import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.animation.RunningAnimation;

public class ExprAnimationName extends SimplePropertyExpression<RunningAnimation, String> {

	public static void register(@NotNull SyntaxRegistry registry) {
        PropertyExpression.register(ExprAnimationName.class, String.class, "animation name", "Object");
	}

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @Nullable String convert(RunningAnimation from) {
        return from.name();
    }

    @Override
    protected String getPropertyName() {
        return "name";
    }
    


}
