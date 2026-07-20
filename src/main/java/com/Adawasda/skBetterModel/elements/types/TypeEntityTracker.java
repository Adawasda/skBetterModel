package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.tracker.EntityTracker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeEntityTracker {

    private TypeEntityTracker() {}

    public static void register() {
        if (Classes.getExactClassInfo(EntityTracker.class) != null) return;
        Classes.registerClass(new ClassInfo<>(EntityTracker.class, "entitytracker")
                .user("entity ?trackers?")
                .name("Entity Tracker")
                .description("Represents a BetterModel entity tracker bound to an entity.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable EntityTracker parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(EntityTracker tracker, int flags) {
                        return "entity tracker[" + tracker.name() + "]";
                    }

                    @Override
                    public @NotNull String toVariableNameString(EntityTracker tracker) {
                        return "entitytracker_" + tracker.name();
                    }
                })
        );
    }
}
