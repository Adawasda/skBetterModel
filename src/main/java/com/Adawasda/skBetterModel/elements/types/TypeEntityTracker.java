package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.tracker.EntityTracker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeEntityTracker {

    public static void register() {
        if (Classes.getExactClassInfo(EntityTracker.class) == null) {
            Classes.registerClass(new ClassInfo<>(EntityTracker.class, "entitytracker")
                .user("entity ?trackers?")
                .name("Entity Tracker")
                .description("Represents a BetterModel entity tracker.")
                .parser(new Parser<EntityTracker>() {
                    @Override
                    public @Nullable EntityTracker parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(EntityTracker o, int flags) {
                        return "entity tracker";
                    }

                    @Override
                    public @NotNull String toVariableNameString(EntityTracker o) {
                        return "entitytracker_" + System.identityHashCode(o);
                    }
                })
            );
        }
    }
}