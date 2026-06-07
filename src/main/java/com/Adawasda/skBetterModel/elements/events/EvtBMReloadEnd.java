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
import kr.toxicity.model.api.event.PluginStartReloadEvent;

public class EvtBMReloadEnd extends SkriptEvent {
	public static void register(@NotNull SyntaxRegistry registry) {
		registry.register(BukkitSyntaxInfos.Event.KEY, BukkitSyntaxInfos.Event.builder(EvtBMReloadEnd.class, "bettermodel reload end event")
				.supplier(EvtBMReloadEnd::new)
				.addEvent(BetterModelBukkitEvent.class)
				.addPatterns("(bm|bettermodel) reload start[ing]")
				.build());
	}
    
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "bettermodel reload end event";
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        BetterModelBukkitEvent e = (BetterModelBukkitEvent) event;
        return e.is(PluginStartReloadEvent.class);
    }
    
}
