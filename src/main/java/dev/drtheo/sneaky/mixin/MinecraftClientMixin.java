package dev.drtheo.sneaky.mixin;

import dev.drtheo.sneaky.mixininterface.IMinecraftClientMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements IMinecraftClientMixin {

    @Unique
    private boolean shouldSneak = false;

    @Shadow @Nullable public ClientPlayerEntity player;

    @Inject(method = "setScreen", at = @At("TAIL"))
    public void setScreen(Screen screen, CallbackInfo ci) {
        if (this.player == null)
            return;

        this.shouldSneak = screen != null && this.player.isSneaking();
    }

    public boolean sneaky$shouldSneak() {
        return this.shouldSneak;
    }
}
