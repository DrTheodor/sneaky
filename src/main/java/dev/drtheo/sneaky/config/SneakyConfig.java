package dev.drtheo.sneaky.config;

import dev.drtheo.sneaky.SneakyClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SneakyConfig {

    private static final Path path = FabricLoader.getInstance().getConfigDir().resolve("sneaky.cfg");
    private static boolean keepSneak = false;

    static {
        SneakyConfig.read();
    }

    public static boolean shouldKeepSneak() {
        return keepSneak;
    }

    public static void shouldKeepSneak(boolean keepSneak) {
        SneakyConfig.keepSneak = keepSneak;
    }

    public static void read() {
        if (!Files.exists(path))
            SneakyConfig.save();

        try (InputStream stream = Files.newInputStream(path)) {
            Properties properties = new Properties();
            properties.load(stream);

            SneakyConfig.shouldKeepSneak(Boolean.parseBoolean(properties.getProperty("keepSneak")));
        } catch (IOException e) {
            SneakyClient.LOGGER.error("Failed to read config for sneaky!");
            e.printStackTrace();
        }
    }

    public static void save() {
        try (OutputStream stream = Files.newOutputStream(path)) {
            Properties properties = new Properties();
            properties.put("keepSneak", Boolean.toString(keepSneak));

            properties.store(stream, null);
        } catch (IOException e) {
            SneakyClient.LOGGER.error("Failed to write config for sneaky!");
            e.printStackTrace();
        }
    }
}
