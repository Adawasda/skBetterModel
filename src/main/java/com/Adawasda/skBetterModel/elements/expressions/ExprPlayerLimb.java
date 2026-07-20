package com.Adawasda.skBetterModel.elements.expressions;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.player.PlayerLimb;

public class ExprPlayerLimb extends SimpleExpression<PlayerLimb> {

    private static final PlayerLimb[] LIMBS = {
            PlayerLimb.HEAD,
            PlayerLimb.CHEST,
            PlayerLimb.LEFT_ARM,
            PlayerLimb.RIGHT_ARM,
            PlayerLimb.LEFT_FOREARM,
            PlayerLimb.RIGHT_FOREARM,
            PlayerLimb.HIP,
            PlayerLimb.WAIST,
            PlayerLimb.CHEST,
            PlayerLimb.LEFT_LEG,
            PlayerLimb.RIGHT_LEG,
            PlayerLimb.LEFT_FORELEG,
            PlayerLimb.RIGHT_FORELEG
    };

    private PlayerLimb limb;

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(
                SyntaxRegistry.EXPRESSION,
                DefaultSyntaxInfos.Expression.builder(ExprPlayerLimb.class, PlayerLimb.class)
                        .supplier(ExprPlayerLimb::new)
                        .addPatterns(
                                "head",
                                "chest",
                                "left arm",
                                "right arm",
                                "left forearm",
                                "right forearm",
                                "hip",
                                "waist",
                                "chest",
                                "left leg",
                                "right leg",
                                "left foreleg",
                                "right foreleg"
                        )
                        .build()
        );
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        limb = LIMBS[matchedPattern];
        return true;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends PlayerLimb> getReturnType() {
        return PlayerLimb.class;
    }

    @Override
    protected PlayerLimb @Nullable [] get(Event event) {
        return new PlayerLimb[]{limb};
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return limb.name().toLowerCase().replace('_', ' ');
    }
}