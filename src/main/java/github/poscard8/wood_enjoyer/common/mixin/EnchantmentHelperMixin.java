package github.poscard8.wood_enjoyer.common.mixin;

import github.poscard8.wood_enjoyer.common.config.WoodEnjoyerConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ALL")
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getEnchantmentCost", at = @At("RETURN"), cancellable = true)
    private static void setArcaneModifier(RandomSource random, int i, int bookshelfCount, ItemStack stack, CallbackInfoReturnable<Integer> ci) {

        int buff = Math.round(WoodEnjoyerConfig.WARPED_HANDLE_BUFF.get() * (i + 1.0F) / 3.0F);
        if (stack.getOrCreateTag().getInt("Handle") == 1) {
            ci.setReturnValue(ci.getReturnValue() + buff);
        }
    }


}
