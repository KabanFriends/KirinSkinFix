package io.github.kabanfriends.kirinskinfix.mixin;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.PlayerSkinTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerSkinTexture.class, priority = 0)
public class MixinPlayerSkinTexture {

    private static final String STRIP_COLOR = "stripColor(Lnet/minecraft/client/texture/NativeImage;IIII)V";
    private static final String STRIP_ALPHA = "stripAlpha(Lnet/minecraft/client/texture/NativeImage;IIII)V";

    @Inject(method = STRIP_ALPHA, at = @At("HEAD"))
    private static void stripAlpha(NativeImage image, int x1, int y1, int x2, int y2, CallbackInfo info) {
        System.out.println("strip test");
        for(int i = x1; i < x2; ++i) {
            for(int j = y1; j < y2; ++j) {
                image.setColor(i, j, image.getColor(i, j) | -16777216);
            }
        }
    }

    @Inject(method = STRIP_COLOR, at = @At("HEAD"))
    private static void stripColor(NativeImage image, int x1, int y1, int x2, int y2, CallbackInfo info) {
        int l;
        int m;
        for(l = x1; l < x2; ++l) {
            for(m = y1; m < y2; ++m) {
                int k = image.getColor(l, m);
                if ((k >> 24 & 255) < 128) {
                    return;
                }
            }
        }

        for(l = x1; l < x2; ++l) {
            for(m = y1; m < y2; ++m) {
                image.setColor(l, m, image.getColor(l, m) & 16777215);
            }
        }
    }
}
