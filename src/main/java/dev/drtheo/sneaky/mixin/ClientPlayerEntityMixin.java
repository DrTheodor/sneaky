package dev.drtheo.sneaky.mixin;

import dev.drtheo.sneaky.config.SneakyConfig;
import dev.drtheo.sneaky.mixininterface.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow public Input input;

    private boolean stickySneak = false;

    /**
     * @author DrTheo_
     * @reason Overwrite provides better control here.
     */
    @Overwrite
    public boolean isSneaking() {
        boolean shouldSneak = ((IMinecraftClientMixin) MinecraftClient.getInstance()).shouldSneak();
        boolean isSneaking = this.input != null && this.input.sneaking;

        if (SneakyConfig.shouldKeepSneak() && shouldSneak)
            this.stickySneak = true;

        if (this.stickySneak && isSneaking)
            this.stickySneak = false;

        return this.stickySneak || isSneaking || shouldSneak;
    }
}
