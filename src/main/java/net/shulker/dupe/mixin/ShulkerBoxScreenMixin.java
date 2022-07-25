package net.shulker.dupe.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.shulker.dupe.MainClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.shulker.dupe.SharedVariables.*;

@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin extends Screen {
    public ShulkerBoxScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V")
    public void renderScreen(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (isFra()) {
            setFra(false);
            MainClient.thex = this.width;
            MainClient.they = this.height;
            this.addDrawableChild(new ButtonWidget(this.width/2-90, this.height/2+35-145, 50, 20, Text.translatable("Dupe"), (button) -> {
                if (shouldDupeAll) shouldDupeAll = false;
                shouldDupe = true;
            }));
            this.addDrawableChild(new ButtonWidget(this.width/2+40, this.height/2+35-145, 50, 20, Text.translatable("Dupe All"), (button) -> {
                if (shouldDupe) shouldDupe = false;
                shouldDupeAll = true;
            }));
        }

        if (this.width != MainClient.thex || this.height != MainClient.they) {
            setFra(true);
        }

    }

    public boolean isFra() {
        return MainClient.fra;
    }

    public void setFra(boolean fra) {
        MainClient.fra = fra;
    }
}
