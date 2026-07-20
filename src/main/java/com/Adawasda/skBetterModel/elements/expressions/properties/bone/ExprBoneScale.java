package com.Adawasda.skBetterModel.elements.expressions.properties.bone;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.bone.RenderedBone;

public class ExprBoneScale extends SimplePropertyExpression<RenderedBone, Number> {

    public static void register() {
        PropertyExpression.register(ExprBoneScale.class, Number.class, "bone scale", "renderedbone");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(RenderedBone bone) {
        return 1.0f;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        return switch (mode) {
            case SET, RESET -> new Class[]{Number.class};
            default -> null;
        };
    }

    @Override
    public void change(Event event, Object @Nullable [] delta, ChangeMode mode) {
        for (RenderedBone bone : getExpr().getArray(event)) {
            float value = (mode == ChangeMode.SET && delta != null)
                    ? ((Number) delta[0]).floatValue() : 1f;
            bone.scale(() -> value);
        }
    }

    @Override
    protected String getPropertyName() {
        return "bone scale";
    }
}
