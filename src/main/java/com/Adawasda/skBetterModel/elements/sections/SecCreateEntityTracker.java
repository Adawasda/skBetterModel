package com.Adawasda.skBetterModel.elements.sections;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.lang.entry.util.ExpressionEntryData;
import org.skriptlang.skript.registration.SyntaxInfo;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Section;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;

import org.skriptlang.skript.lang.entry.EntryContainer;
import org.skriptlang.skript.lang.entry.EntryValidator;

import kr.toxicity.model.api.animation.AnimationIterator;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.tracker.EntityTracker;

import com.Adawasda.skBetterModel.core.TrackerController;
import com.Adawasda.skBetterModel.utils.ColorUtils;
import com.Adawasda.skBetterModel.utils.PluginConfig;

public class SecCreateEntityTracker extends Section {

    private static @Nullable EntityTracker lastCreated;

    public static @Nullable EntityTracker getLastCreated() {
        return lastCreated;
    }

    private Expression<String> modelNameExpr;

    private Expression<Entity> entityExpr;
    private Expression<OfflinePlayer> playerExpr;
    private Expression<Vector> scaleExpr;
    private Expression<String> startAnimationExpr;
    private Expression<Vector> offsetExpr;
    private Expression<Number> brightnessExpr;
    private Expression<Boolean> glowExpr;
    private Expression<Color> glowColorExpr;
    private Expression<Number> viewRangeExpr;
    private Expression<Color> tintExpr;
    private Expression<Billboard> billboardExpr;

    private Expression<Number> minBodyExpr;
    private Expression<Number> maxBodyExpr;
    private Expression<Number> minHeadExpr;
    private Expression<Number> maxHeadExpr;
    private Expression<Boolean> bodyUnevenExpr;
    private Expression<Boolean> headUnevenExpr;
    private Expression<Number> rotationDurationExpr;
    private Expression<Number> rotationDelayExpr;

    private static final EntryValidator VALIDATOR = EntryValidator.builder()
            .addEntryData(new ExpressionEntryData<>("entity", null, false, Entity.class))
            .addEntryData(new ExpressionEntryData<>("player", null, true, OfflinePlayer.class))
            .addEntryData(new ExpressionEntryData<>("start animation", null, true, String.class))
            .addEntryData(new ExpressionEntryData<>("scale", null, true, Vector.class))
            .addEntryData(new ExpressionEntryData<>("offset", null, true, Vector.class))
            .addEntryData(new ExpressionEntryData<>("brightness", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("glow", null, true, Boolean.class))
            .addEntryData(new ExpressionEntryData<>("glow color", null, true, Color.class))
            .addEntryData(new ExpressionEntryData<>("view range", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("tint", null, true, Color.class))
            .addEntryData(new ExpressionEntryData<>("billboard", null, true, Billboard.class))
            .addEntryData(new ExpressionEntryData<>("min body", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("max body", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("min head", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("max head", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("body uneven", null, true, Boolean.class))
            .addEntryData(new ExpressionEntryData<>("head uneven", null, true, Boolean.class))
            .addEntryData(new ExpressionEntryData<>("rotation duration", null, true, Number.class))
            .addEntryData(new ExpressionEntryData<>("rotation delay", null, true, Number.class))
            .build();

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(SyntaxRegistry.SECTION, SyntaxInfo.builder(SecCreateEntityTracker.class)
                .supplier(SecCreateEntityTracker::new)
                .addPatterns("create [bm|bettermodel] entity tracker with model %string%")
                .build());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create entity tracker with model " +
                (modelNameExpr != null ? modelNameExpr.toString(event, debug) : "null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern,
                        Kleenean isDelayed, ParseResult parseResult,
                        SectionNode sectionNode, List<TriggerItem> triggerItems) {

        modelNameExpr = (Expression<String>) expressions[0];
        if (modelNameExpr == null) {
            Skript.error("Model name is required.");
            return false;
        }

        EntryContainer container = VALIDATOR.validate(sectionNode);
        if (container == null) return false;

        entityExpr = (Expression<Entity>) container.getOptional("entity", Expression.class, true);
        playerExpr = (Expression<OfflinePlayer>) container.getOptional("player", Expression.class, true);
        startAnimationExpr = (Expression<String>) container.getOptional("start animation", Expression.class, true);
        scaleExpr = (Expression<Vector>) container.getOptional("scale", Expression.class, true);
        offsetExpr = (Expression<Vector>) container.getOptional("offset", Expression.class, true);
        brightnessExpr = (Expression<Number>) container.getOptional("brightness", Expression.class, true);
        glowExpr = (Expression<Boolean>) container.getOptional("glow", Expression.class, true);
        glowColorExpr = (Expression<Color>) container.getOptional("glow color", Expression.class, true);
        viewRangeExpr = (Expression<Number>) container.getOptional("view range", Expression.class, true);
        tintExpr = (Expression<Color>) container.getOptional("tint", Expression.class, true);
        billboardExpr = (Expression<Billboard>) container.getOptional("billboard", Expression.class, true);
        minBodyExpr = (Expression<Number>) container.getOptional("min body", Expression.class, true);
        maxBodyExpr = (Expression<Number>) container.getOptional("max body", Expression.class, true);
        minHeadExpr = (Expression<Number>) container.getOptional("min head", Expression.class, true);
        maxHeadExpr = (Expression<Number>) container.getOptional("max head", Expression.class, true);
        bodyUnevenExpr = (Expression<Boolean>) container.getOptional("body uneven", Expression.class, true);
        headUnevenExpr = (Expression<Boolean>) container.getOptional("head uneven", Expression.class, true);
        rotationDurationExpr = (Expression<Number>) container.getOptional("rotation duration", Expression.class, true);
        rotationDelayExpr = (Expression<Number>) container.getOptional("rotation delay", Expression.class, true);

        if (entityExpr == null) {
            Skript.error("Entity entry is required.");
            return false;
        }

        return true;
    }

    @Override
    @Nullable
    protected TriggerItem walk(Event event) {
        execute(event);
        return walk(event, false);
    }

    private void execute(Event event) {
        String model = modelNameExpr.getSingle(event);
        Entity entity = entityExpr.getSingle(event);
        if (model == null || entity == null) return;

        PluginConfig config = PluginConfig.get();

        OfflinePlayer player = resolve(playerExpr, event);
        TrackerController controller;

        if (player != null) {
            controller = TrackerController.fromLimb(entity, model, player);
        } else {
            controller = TrackerController.fromModel(entity, model);
        }

        if (!controller.isValid()) return;

        applyScale(controller, event);
        applyBrightness(controller, event);
        applyGlow(controller, event);
        applyGlowColor(controller, event);
        applyViewRange(controller, event);
        applyTint(controller, event);
        applyBillboard(controller, event);
        applyRotatorData(controller, event, config);
        applyOffset(controller, event);
        applyStartAnimation(controller, event);

        lastCreated = controller.getTracker();
    }

    private void applyScale(TrackerController controller, Event event) {
        Vector scale = resolve(scaleExpr, event);
        if (scale != null) {
            controller.setScale((float) scale.getX());
        }
    }

    private void applyBrightness(TrackerController controller, Event event) {
        Number brightness = resolve(brightnessExpr, event);
        if (brightness != null) {
            int val = brightness.intValue();
            controller.setBrightness(val, val);
        }
    }

    private void applyGlow(TrackerController controller, Event event) {
        Boolean glow = resolve(glowExpr, event);
        if (glow != null) {
            controller.setGlow(glow);
        }
    }

    private void applyGlowColor(TrackerController controller, Event event) {
        Color color = resolve(glowColorExpr, event);
        if (color != null) {
            controller.setGlowColor(ColorUtils.fromSkriptColor(color));
        }
    }

    private void applyViewRange(TrackerController controller, Event event) {
        Number range = resolve(viewRangeExpr, event);
        if (range != null) {
            controller.setViewRange(range.intValue());
        }
    }

    private void applyTint(TrackerController controller, Event event) {
        Color color = resolve(tintExpr, event);
        if (color != null) {
            controller.setTint(ColorUtils.fromSkriptColor(color));
        }
    }

    private void applyBillboard(TrackerController controller, Event event) {
        Billboard billboard = resolve(billboardExpr, event);
        if (billboard != null) {
            controller.setBillboard(billboard);
        }
    }

    private void applyRotatorData(TrackerController controller, Event event, PluginConfig config) {
        float minBody = resolveFloat(minBodyExpr, event, config.getMinBody());
        float maxBody = resolveFloat(maxBodyExpr, event, config.getMaxBody());
        float minHead = resolveFloat(minHeadExpr, event, config.getMinHead());
        float maxHead = resolveFloat(maxHeadExpr, event, config.getMaxHead());
        boolean bodyUneven = resolveBoolean(bodyUnevenExpr, event, config.isBodyUneven());
        boolean headUneven = resolveBoolean(headUnevenExpr, event, config.isHeadUneven());
        int rotDuration = resolveInt(rotationDurationExpr, event, config.getRotationDuration());
        int rotDelay = resolveInt(rotationDelayExpr, event, config.getRotationDelay());

        controller.setRotatorData(data -> {
            data.setMinBody(minBody);
            data.setMaxBody(maxBody);
            data.setMinHead(minHead);
            data.setMaxHead(maxHead);
            data.setBodyUneven(bodyUneven);
            data.setHeadUneven(headUneven);
            data.setRotationDuration(rotDuration);
            data.setRotationDelay(rotDelay);
        });
    }

    private void applyOffset(TrackerController controller, Event event) {
        Vector offset = resolve(offsetExpr, event);
        if (offset != null) {
            controller.setOffset(offset.toVector3f());
        }
    }

    private void applyStartAnimation(TrackerController controller, Event event) {
        String animation = resolve(startAnimationExpr, event);
        if (animation != null) {
            controller.animate(animation,
                    AnimationModifier.builder()
                            .override(true)
                            .start(0)
                            .end(0)
                            .type(AnimationIterator.Type.PLAY_ONCE)
                            .build(),
                    true);
        }
    }

    private static <T> @Nullable T resolve(@Nullable Expression<T> expr, Event event) {
        return expr != null ? expr.getSingle(event) : null;
    }

    private static float resolveFloat(@Nullable Expression<Number> expr, Event event, float fallback) {
        if (expr == null) return fallback;
        Number val = expr.getSingle(event);
        return val != null ? val.floatValue() : fallback;
    }

    private static int resolveInt(@Nullable Expression<Number> expr, Event event, int fallback) {
        if (expr == null) return fallback;
        Number val = expr.getSingle(event);
        return val != null ? val.intValue() : fallback;
    }

    private static boolean resolveBoolean(@Nullable Expression<Boolean> expr, Event event, boolean fallback) {
        if (expr == null) return fallback;
        Boolean val = expr.getSingle(event);
        return val != null ? val : fallback;
    }
}
