package net.shulker.dupe;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class Util {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public static void log(String msg) {
        CLIENT.player.sendMessage(Text.of("[Shulker Dupe]: " + msg), false);
    }
    public static void quickMoveAllItems() {
        for (int i = 0; i < 27; i++) {
            quickMoveItem(i);
        }
    }

    public static void quickMoveItem(int slot) {
        if (CLIENT.player.currentScreenHandler instanceof ShulkerBoxScreenHandler) {
            ShulkerBoxScreenHandler screenHandler = (ShulkerBoxScreenHandler) CLIENT.player.currentScreenHandler;
            Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
            stack.put(slot, screenHandler.getSlot(slot).getStack());
            CLIENT.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(screenHandler.syncId, 0, slot, 0, SlotActionType.QUICK_MOVE, screenHandler.getSlot(0).getStack(), (Int2ObjectMap) stack));
        }
    }
}
