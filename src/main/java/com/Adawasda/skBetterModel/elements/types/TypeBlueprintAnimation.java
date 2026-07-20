package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeBlueprintAnimation {

    private TypeBlueprintAnimation() {}

    public static void register() {
        if (Classes.getExactClassInfo(BlueprintAnimation.class) != null) return;
        Classes.registerClass(new ClassInfo<>(BlueprintAnimation.class, "animation")
                .user("animations?")
                .name("Blueprint Animation")
                .description("Represents a blueprint animation definition.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable BlueprintAnimation parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(BlueprintAnimation animation, int flags) {
                        return animation.name();
                    }

                    @Override
                    public @NotNull String toVariableNameString(BlueprintAnimation animation) {
                        return "animation_" + animation.name();
                    }
                })
        );
    }
}
