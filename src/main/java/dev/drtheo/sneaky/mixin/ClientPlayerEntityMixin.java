package dev.drtheo.sneaky.mixin;

import dev.drtheo.sneaky.config.SneakyConfig;
import dev.drtheo.sneaky.mixininterface.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow public Input input;

    @Shadow @Final protected MinecraftClient client;
    @Unique private boolean stickySneak = false;

    @Unique private int sneakTime = 0;

    /**
     * @author DrTheo_
     * @reason Overwrite provides better control here.
     */
    @Overwrite
    public boolean isSneaking() {
        boolean shouldSneak = ((IMinecraftClientMixin) this.client).sneaky$shouldSneak();
        boolean isSneaking = this.input != null && this.input.sneaking;

        if (SneakyConfig.shouldKeepSneak() && shouldSneak)
            this.stickySneak = true;

        if (SneakyConfig.shouldHoldToToggle() && this.sneakTime > 0)
            this.stickySneak = this.sneakTime >= SneakyConfig.holdToToggle();

        if (this.stickySneak && isSneaking)
            this.stickySneak = false;

        return this.stickySneak || isSneaking || shouldSneak;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void tickMovement(CallbackInfo ci) {
        if (this.input == null)
            return;

        if (this.input.sneaking) {
            this.sneakTime += 1;
        } else {
            this.sneakTime = 0;
        }
    }
}
