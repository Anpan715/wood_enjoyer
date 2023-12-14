package github.poscard8.wood_enjoyer.common.mixin;

import github.poscard8.wood_enjoyer.common.config.WoodEnjoyerConfig;
import github.poscard8.wood_enjoyer.common.item.HandleItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@SuppressWarnings("ALL")
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    abstract CompoundTag getOrCreateTag();

    @Shadow
    abstract Item getItem();

    private ItemStack self = (ItemStack) (Object) this;

    private boolean arcaneFlag() {
        return this.getOrCreateTag().getInt("Handle") == 1;
    }

    private boolean stableFlag() {
        return this.getOrCreateTag().getInt("Handle") == 2;
    }

    @Inject(method = "getTooltipLines", at = @At("TAIL"), cancellable = true)
    private void addHandleTooltip(@Nullable Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> ci) {
        List<Component> components = ci.getReturnValue();
        int handleValue = this.getOrCreateTag().getInt("Handle");
        if (handleValue > 0 && handleValue < HandleItem.ALL.size() + 1) {
            components.add(1, HandleItem.ALL.get(handleValue - 1).getTooltip());

        }
        ci.setReturnValue(components);
    }


    @Inject(method = "getMaxDamage", at = @At("TAIL"), cancellable = true)
    private void setMaxDamage(CallbackInfoReturnable<Integer> ci) {
        if (this.stableFlag()) {
            ci.setReturnValue(Math.round((float) (ci.getReturnValue() * WoodEnjoyerConfig.LUNAR_HANDLE_BUFF.get())));
        }
    }

    @Inject(method = "getBarWidth", at = @At("TAIL"), cancellable = true)
    private void setBarWidth(CallbackInfoReturnable<Integer> ci) {
        if (this.stableFlag()) {
            ci.setReturnValue(HandleItem.getStableBarWidth(self));
        }
    }

    @Inject(method = "getBarColor", at = @At("TAIL"), cancellable = true)
    private void setBarColor(CallbackInfoReturnable<Integer> ci) {
        if (this.stableFlag()) {
            ci.setReturnValue(HandleItem.getStableBarColor(self));
        }
    }


}
