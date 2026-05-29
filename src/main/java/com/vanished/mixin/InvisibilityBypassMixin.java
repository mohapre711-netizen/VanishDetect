package com.vanished.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class InvisibilityBypassMixin {

    // إلغاء إخفاء اللاعب في شاشتك عند استخدام جرعة الاختفاء
    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void cancelPlayerInvisibility(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            
            if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                cir.setReturnValue(false);
            }
        }
    }

    // تشغيل الإطار الأبيض (Glowing Outline) حول اللاعب المخفي فقط لتتميزه
    @Inject(method = "hasGlowingOutline", at = @At("HEAD"), cancellable = true)
    private void forceGlowingOnInvisiblePlayers(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            
            if (player.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                cir.setReturnValue(true);
            }
        }
    }
}

