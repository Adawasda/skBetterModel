package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.RunningAnimation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeRunningAnimation {

    public static void register() {
        if (Classes.getExactClassInfo(RunningAnimation.class) == null) {
            Classes.registerClass(new ClassInfo<>(RunningAnimation.class, "runninganimation")
                .user("running ?animations?")
                .name("Running Animation")
                .description("Represents a currently running animation on a model.")
                .parser(new Parser<RunningAnimation>() {
                    @Override
                    public @Nullable RunningAnimation parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(RunningAnimation o, int flags) {
                        return o.name();
                    }

                    @Override
                    public @NotNull String toVariableNameString(RunningAnimation o) {
                        return o.name();
                    }
                })
            );
        }
    }
}