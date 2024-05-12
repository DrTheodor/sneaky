package dev.drtheo.sneaky.menu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.drtheo.sneaky.config.SneakyConfig;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.AbstractFieldBuilder;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SneakyModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(screen)
                    .setSavingRunnable(SneakyConfig::save)
                    .setTitle(Text.translatable("name.sneaky.translation"));

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("name.sneaky.translation"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(SneakyModMenu.entry(
                    entryBuilder, ConfigEntryBuilder::startBooleanToggle,
                    SneakyConfig::shouldKeepSneak, SneakyConfig::shouldKeepSneak,
                    "keep_sneak", false
            ));

            general.addEntry(SneakyModMenu.entry(
                    entryBuilder, ConfigEntryBuilder::startFloatField,
                    (f) -> SneakyConfig.holdToToggle((int) (f * 20)),
                    () -> (float) (SneakyConfig.holdToToggle() / 20),
                    "hold_to_toggle", -1f)
            );

            return builder.build();
        };
    }

    private static <T> AbstractConfigListEntry<?> entry(ConfigEntryBuilder builder, BuilderCreator<T> f, Consumer<T> save, Supplier<T> get, String name, T def) {
        AbstractFieldBuilder<T, ?, ?> result = f.create(builder, Text.translatable("option.sneaky." + name), get.get());
        result.setTooltip(Text.translatable("option.sneaky." + name + ".description"));
        result.setDefaultValue(def);

        result.setSaveConsumer(save);
        return result.build();
    }

    @FunctionalInterface
    private interface BuilderCreator<T> {
        AbstractFieldBuilder<T, ?, ?> create(ConfigEntryBuilder builder, Text text, T value);
    }
}
