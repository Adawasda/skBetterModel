package com.Adawasda.skBetterModel.elements.expressions.properties.bone;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.bone.RenderedBone;

public class ExprBoneEnchanted extends SimplePropertyExpression<RenderedBone, Boolean> {

    public static void register() {
        PropertyExpression.register(ExprBoneEnchanted.class, Boolean.class, "bone enchant[ed]", "renderedbone");
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public @Nullable Boolean convert(RenderedBone bone) {
        return false;
    }

    @Override
    public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
        return switch (mode) {
            case SET, RESET -> new Class[]{Boolean.class};
            default -> null;
        };
    }

    @Override
    public void change(Event event, Object @Nullable [] delta, ChangeMode mode) {
        for (RenderedBone bone : getExpr().getArray(event)) {
            boolean value = (mode == ChangeMode.SET && delta != null) && (Boolean) delta[0];
            bone.enchant(b -> b.equals(bone), value);
        }
    }

    @Override
    protected String getPropertyName() {
        return "bone enchanted";
    }
}
