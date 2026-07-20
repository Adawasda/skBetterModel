package com.Adawasda.skBetterModel.elements.expressions.properties.tracker;

import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import com.Adawasda.skBetterModel.core.TrackerController;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import kr.toxicity.model.api.tracker.EntityTracker;

public class ExprTrackerViewRange extends SimplePropertyExpression<EntityTracker, Number> {

    public static void register() {
        PropertyExpression.register(ExprTrackerViewRange.class, Number.class, "[bm] view range", "entitytracker");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public @Nullable Number convert(EntityTracker tracker) {
        return 0;
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
        for (EntityTracker tracker : getExpr().getArray(event)) {
            TrackerController controller = TrackerController.wrap(tracker);
            int value = (mode == ChangeMode.SET && delta != null)
                    ? ((Number) delta[0]).intValue() : 0;
            controller.setViewRange(value);
        }
    }

    @Override
    protected String getPropertyName() {
        return "view range";
    }
}
