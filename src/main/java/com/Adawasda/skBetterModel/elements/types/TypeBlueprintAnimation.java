package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeBlueprintAnimation {

    public static void register() {
        if (Classes.getExactClassInfo(BlueprintAnimation.class) == null) {
            Classes.registerClass(new ClassInfo<>(BlueprintAnimation.class, "blueprintanimation")
                .user("blueprint ?animations?")
                .name("Blueprint Animation")
                .description("Represents a blueprint animation.")
                .parser(new Parser<BlueprintAnimation>() {
                    @Override
                    public @Nullable BlueprintAnimation parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(BlueprintAnimation o, int flags) {
                        return o.name();
                    }

                    @Override
                    public @NotNull String toVariableNameString(BlueprintAnimation o) {
                        return o.name();
                    }
                })
            );
        }
    }
}