package com.Adawasda.skBetterModel.elements.sections;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;

import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Color;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.bukkit.platform.BukkitAdapter;
import kr.toxicity.model.api.platform.PlatformEntity;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.tracker.ModelScaler;
import kr.toxicity.model.api.tracker.TrackerUpdateAction;
import utils.EntityTrackerController;
import utils.utils;

@SuppressWarnings("unused")
public class SecCreateEntityTracker extends Section {

    public static EntityTracker lastCreatedEntityTracker;

    private Expression<String> modelName;

    private Expression<Entity> entityExpr;
    private Expression<Vector> scaleExpr;

    private Expression<Number> brightnessExpr;
    private Expression<Boolean> glowExpr;
    private Expression<Color> glowColorExpr;
    private Expression<Number> viewRangeExpr;
    private Expression<Color> tintExpr;


    private static final EntryValidator validator = EntryValidator.builder()
            .addEntryData(new ExpressionEntryData<>("entity", null, true, Entity.class))
            .addEntryData(new ExpressionEntryData<>("scale", null, true, Vector.class))
            .addEntryData(new ExpressionEntryData<>("brightness", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("glow", null, true, Boolean.class))
            .addEntryData(new ExpressionEntryData<>("glow color", null, true, Color.class))
            .addEntryData(new ExpressionEntryData<>("view range", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("tint", null, true, Color.class))
            .build();

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.SECTION, SyntaxInfo.builder(SecCreateEntityTracker.class)
                .supplier(SecCreateEntityTracker::new)
                .addPatterns("create entity tracker with model %string%")
                .build()
        );
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create entity tracker with model " +
                (modelName != null ? modelName.toString(event, debug) : "null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern,
                        Kleenean isDelayed, ParseResult parseResult,
                        SectionNode sectionNode, List<TriggerItem> triggerItems) {

        EntryContainer container = validator.validate(sectionNode);

        modelName = (Expression<String>) expressions[0];

        if (modelName == null) {
            Skript.error("No model name provided.");
            return false;
        }

        if (container == null)
            return false;

        entityExpr = (Expression<Entity>) container.getOptional("entity", Expression.class, true);
        scaleExpr = (Expression<Vector>) container.getOptional("scale", Expression.class, true);

        brightnessExpr = (Expression<Number>) container.getOptional("brightness", Expression.class, true);
        glowExpr = (Expression<Boolean>) container.getOptional("glow", Expression.class, true);
        glowColorExpr = (Expression<Color>) container.getOptional("glow color", Expression.class, true);
        viewRangeExpr = (Expression<Number>) container.getOptional("view range", Expression.class, true);
        tintExpr = (Expression<Color>) container.getOptional("tint", Expression.class, true);

        
        return true;
    }

    @Override
    @Nullable
    protected TriggerItem walk(Event event) {
        execute(event);
        return walk(event, false);
    }

    private void execute(Event event) {

        String model = modelName.getSingle(event);
        Entity entity = entityExpr.getSingle(event);

        if (model == null || entity == null)
            return;

        EntityTrackerController controller =
                new EntityTrackerController(entity, model);

        if (scaleExpr != null && scaleExpr.getSingle(event) != null) {
            float scale = (float) scaleExpr.getSingle(event).getX();
            controller.setScale(scale);
        }

        if (brightnessExpr != null && brightnessExpr.getSingle(event) != null) {
            int brightness = brightnessExpr.getSingle(event).intValue();
            controller.setBrightness(brightness, brightness);
        }

        if (glowExpr != null && glowExpr.getSingle(event) != null) {
            controller.setGlow(glowExpr.getSingle(event));
        }

        if (glowColorExpr != null && glowColorExpr.getSingle(event) != null) {
            Color c = glowColorExpr.getSingle(event);
            controller.setGlowColor(utils.rgbToInt(c.getRed(), c.getGreen(), c.getBlue()));
        }

        if (viewRangeExpr != null && viewRangeExpr.getSingle(event) != null) {
            controller.setViewRange(viewRangeExpr.getSingle(event).intValue());
        }

        if (tintExpr != null && tintExpr.getSingle(event) != null) {
            Color c = tintExpr.getSingle(event);
            controller.setTint(utils.rgbToInt(c.getRed(), c.getGreen(), c.getBlue()));
        }

        lastCreatedEntityTracker = controller.getTracker();
    }
}