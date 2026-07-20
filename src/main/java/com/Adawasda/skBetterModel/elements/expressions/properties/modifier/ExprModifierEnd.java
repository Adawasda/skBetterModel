package com.Adawasda.skBetterModel.elements.expressions.properties.modifier;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.animation.AnimationModifier;

public class ExprModifierEnd extends SimplePropertyExpression<AnimationModifier.Builder, Number> {

    public static void register() {
        PropertyExpression.register(ExprModifierEnd.class, Number.class, "end", "modifier");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(AnimationModifier.Builder builder) {
        return builder.build().end();
    }

    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        return switch (mode) {
            case SET, RESET, DELETE -> new Class[]{Number.class};
            default -> null;
        };
    }

    @Override
    public void change(Event event, Object @Nullable [] delta, ChangeMode mode) {
        for (AnimationModifier.Builder builder : getExpr().getArray(event)) {
            int value = (mode == ChangeMode.SET && delta != null)
                    ? ((Number) delta[0]).intValue() : 0;
            builder.end(value);
        }
    }

    @Override
    protected String getPropertyName() {
        return "end";
    }
}
