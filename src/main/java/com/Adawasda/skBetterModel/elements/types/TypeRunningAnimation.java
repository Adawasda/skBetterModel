package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.RunningAnimation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeRunningAnimation {

    private TypeRunningAnimation() {}

    public static void register() {
        if (Classes.getExactClassInfo(RunningAnimation.class) != null) return;
        Classes.registerClass(new ClassInfo<>(RunningAnimation.class, "runninganimation")
                .user("running ?animations?")
                .name("Running Animation")
                .description("Represents a currently playing animation on a model.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable RunningAnimation parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(RunningAnimation animation, int flags) {
                        return animation.name();
                    }

                    @Override
                    public @NotNull String toVariableNameString(RunningAnimation animation) {
                        return "runninganimation_" + animation.name();
                    }
                })
        );
    }
}
