package com.Adawasda.skBetterModel.elements.expressions.properties.bone;

import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.bone.RenderedBone;

public class ExprBoneName extends SimplePropertyExpression<RenderedBone, String> {

    public static void register() {
        PropertyExpression.register(ExprBoneName.class, String.class, "bone name", "renderedbone");
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @Nullable String convert(RenderedBone bone) {
        if (bone == null || bone.name() == null) return null;
        return bone.name().name();
    }

    @Override
    protected String getPropertyName() {
        return "bone name";
    }
}
