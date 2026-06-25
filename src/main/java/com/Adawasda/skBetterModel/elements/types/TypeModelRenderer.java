package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.data.renderer.ModelRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeModelRenderer {

    public static void register() {
        if (Classes.getExactClassInfo(ModelRenderer.class) == null) {
            Classes.registerClass(new ClassInfo<>(ModelRenderer.class, "modelrenderer")
                .user("model ?renderers?")
                .name("Model Renderer")
                .description("Represents a model renderer")
                .parser(new Parser<ModelRenderer>() {
                    @Override
                    public @Nullable ModelRenderer parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(ModelRenderer o, int flags) {
                        return "model renderer";
                    }

                    @Override
                    public @NotNull String toVariableNameString(ModelRenderer o) {
                        return "modelrenderer_" + System.identityHashCode(o);
                    }
                })
            );
        }
    }
}