package dev.drtheo.sneaky.menu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.drtheo.sneaky.config.SneakyConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

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

            general.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.sneaky.keep_sneak"), SneakyConfig.shouldKeepSneak())
                    .setDefaultValue(false)
                    .setTooltip(Text.translatable("option.sneaky.keep_sneak.description"))
                    .setSaveConsumer(SneakyConfig::shouldKeepSneak)
                    .build());


            return builder.build();
        };
    }
}
