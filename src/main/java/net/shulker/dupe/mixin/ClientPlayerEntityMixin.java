package net.shulker.dupe.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.message.ChatMessageSigner;
import net.minecraft.text.Text;
import net.shulker.dupe.MainClient;
import net.shulker.dupe.SharedVariables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Shadow public abstract void sendMessage(Text message);

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        MainClient.tick();
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessagePacket", cancellable = true)
    public void sendChatMessagePacket(ChatMessageSigner signer, String message, Text preview, CallbackInfo ci) {
        if (message.equalsIgnoreCase("^toggleshulkerdupe")) {
            SharedVariables.enabled = !SharedVariables.enabled;
            sendMessage(Text.of(SharedVariables.enabled ? "Will now show dupe buttons in shulker box." : "Will no longer show dupe buttons in shulker box."));
            ci.cancel();
        }
    }
}
