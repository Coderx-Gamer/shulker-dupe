package net.shulker.dupe.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulker.dupe.SharedVariables.shouldDupe;
import static net.shulker.dupe.SharedVariables.shouldDupeAll;
import static net.shulker.dupe.Util.quickMoveAllItems;
import static net.shulker.dupe.Util.quickMoveItem;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(at = @At("TAIL"), method = "send(Lnet/minecraft/network/Packet;)V")
    public void send(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof PlayerActionC2SPacket) {
            if (((PlayerActionC2SPacket) packet).getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
                if (shouldDupe) {
                    quickMoveItem(0);
                    shouldDupe = false;
                } else if (shouldDupeAll) {
                    quickMoveAllItems();
                    shouldDupeAll = false;
                }
            }
        }
    }
}
