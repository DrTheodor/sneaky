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
    private static int holdToToggle = -1;

    static {
        SneakyConfig.read();
    }

    public static boolean shouldKeepSneak() {
        return keepSneak;
    }

    public static void shouldKeepSneak(boolean keepSneak) {
        SneakyConfig.keepSneak = keepSneak;
    }

    public static int holdToToggle() {
        return holdToToggle;
    }

    public static void holdToToggle(int holdToToggle) {
        SneakyConfig.holdToToggle = holdToToggle;
    }

    public static boolean shouldHoldToToggle() {
        return holdToToggle > 0;
    }

    public static void read() {
        if (!Files.exists(path))
            SneakyConfig.save();

        try (InputStream stream = Files.newInputStream(path)) {
            Properties properties = new Properties();
            properties.load(stream);

            SneakyConfig.shouldKeepSneak(Boolean.parseBoolean(properties.getProperty("keepSneak")));
            SneakyConfig.holdToToggle(Integer.parseInt(properties.getProperty("holdToToggle")));
        } catch (IOException e) {
            SneakyClient.LOGGER.error("Failed to read config for sneaky!", e);
        }
    }

    public static void save() {
        try (OutputStream stream = Files.newOutputStream(path)) {
            Properties properties = new Properties();
            properties.put("keepSneak", Boolean.toString(keepSneak));
            properties.put("holdToToggle", Integer.toString(holdToToggle));

            properties.store(stream, null);
        } catch (IOException e) {
            SneakyClient.LOGGER.error("Failed to write config for sneaky!", e);
        }
    }
}
