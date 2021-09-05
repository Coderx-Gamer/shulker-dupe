package net.shulker.dupe;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

import static net.shulker.dupe.SharedVariables.*;
import static net.shulker.dupe.Util.CLIENT;
import static net.shulker.dupe.Util.log;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    }

    public static void tick() {
        if (shouldDupe || shouldDupeAll) {
            HitResult hit = CLIENT.crosshairTarget;
            if (hit instanceof BlockHitResult) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                if (CLIENT.world.getBlockState(blockHit.getBlockPos()).getBlock() instanceof ShulkerBoxBlock && CLIENT.player.currentScreenHandler instanceof ShulkerBoxScreenHandler) {
                    CLIENT.interactionManager.updateBlockBreakingProgress(blockHit.getBlockPos(), Direction.DOWN);
                } else {
                    log("You need to have a shulker box screen open and look at a shulker box.");
                    CLIENT.player.closeHandledScreen();
                    shouldDupe = false;
                    shouldDupeAll = false;
                }
            }
        }
    }
}
