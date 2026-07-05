package com.Adawasda.skBetterModel.elements.expressions.properties.modifier;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.animation.AnimationModifier;

public class ExprModifierEnd extends SimplePropertyExpression<AnimationModifier.Builder, Number> {

    public static void register() {
        PropertyExpression.register(ExprModifierEnd.class, Number.class, "end", "modifierbuilder");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(AnimationModifier.Builder from) {
        return from.build().end();
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
                    builder.end(value);
                }
            }
            case RESET, DELETE -> {
                for (AnimationModifier.Builder builder : builders) {
                    builder.end(0);
                }
            }
            default -> {}
        }
    }

    @Override
    protected String getPropertyName() {
        return "end";
    }
}