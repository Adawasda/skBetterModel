package com.Adawasda.skBetterModel.elements.expressions.properties.modifier;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.animation.AnimationModifier;

public class ExprModifierStart extends SimplePropertyExpression<AnimationModifier.Builder, Number> {

    public static void register() {
        PropertyExpression.register(ExprModifierStart.class, Number.class, "start", "modifier");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(AnimationModifier.Builder from) {
        return from.build().start();
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
        AnimationModifier.Builder[] builders = getExpr().getArray(event);
        if (builders.length == 0) return;

        switch (mode) {
            case SET -> {
                int value = delta != null ? ((Number) delta[0]).intValue() : 0;
                for (AnimationModifier.Builder builder : builders) {
                    builder.start(value);
                }
            }
            case RESET, DELETE -> {
                for (AnimationModifier.Builder builder : builders) {
                    builder.start(0);
                }
            }
            default -> {}
        }
    }

    @Override
    protected String getPropertyName() {
        return "start";
    }
}