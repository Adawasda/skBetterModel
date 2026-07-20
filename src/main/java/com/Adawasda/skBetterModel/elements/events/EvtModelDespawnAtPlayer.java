package com.Adawasda.skBetterModel.elements.events;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.bukkit.registration.BukkitSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import kr.toxicity.model.api.bukkit.event.BetterModelBukkitEvent;
import kr.toxicity.model.api.event.ModelDespawnAtPlayerEvent;

public class EvtModelDespawnAtPlayer extends SkriptEvent {

    public static void register(@NotNull SyntaxRegistry registry) {
        registry.register(BukkitSyntaxInfos.Event.KEY,
                BukkitSyntaxInfos.Event.builder(EvtModelDespawnAtPlayer.class, "bettermodel model despawn at player event")
                        .supplier(EvtModelDespawnAtPlayer::new)
                        .addEvent(BetterModelBukkitEvent.class)
                        .addPatterns("[bm|bettermodel] model despawn[ing] [at player]")
                        .build());
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "bettermodel model despawn at player event";
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return (event instanceof BetterModelBukkitEvent bmEvent)
                && bmEvent.is(ModelDespawnAtPlayerEvent.class);
    }
}
