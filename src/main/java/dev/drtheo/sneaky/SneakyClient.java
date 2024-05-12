package dev.drtheo.sneaky;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SneakyClient implements ClientModInitializer {

    public static final String ID = "sneaky";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("You are now sneaky! \uD83E\uDD2B");
    }
}
