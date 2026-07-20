package com.Adawasda.skBetterModel.elements.expressions.properties.tracker;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import com.Adawasda.skBetterModel.core.TrackerController;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.tracker.EntityTracker;

public class ExprTrackerGlow extends SimplePropertyExpression<EntityTracker, Boolean> {

    public static void register() {
        PropertyExpression.register(ExprTrackerGlow.class, Boolean.class, "[bm] glow[ing]", "entitytracker");
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public @Nullable Boolean convert(EntityTracker tracker) {
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
        for (EntityTracker tracker : getExpr().getArray(event)) {
            TrackerController controller = TrackerController.wrap(tracker);
            boolean value = (mode == ChangeMode.SET && delta != null) && (Boolean) delta[0];
            controller.setGlow(value);
        }
    }

    @Override
    protected String getPropertyName() {
        return "glowing";
    }
}
