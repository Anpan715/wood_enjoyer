package github.poscard8.wood_enjoyer.common.mixin;

import github.poscard8.wood_enjoyer.common.item.HandleItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ALL")
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Shadow
    private DataSlot cost;

    @Shadow
    private int repairItemCountCost;

    private Container inputSlots = ((ItemCombinerMenuInvoker) this).getInputSlots();
    private ResultContainer resultSlots = ((ItemCombinerMenuInvoker) this).getResultSlots();

    @Inject(method = "createResult", at = @At("RETURN"), cancellable = true)
    private void allowHandleApply(CallbackInfo ci) {

        ItemStack slot1 = this.inputSlots.getItem(0);
        ItemStack slot2 = this.inputSlots.getItem(1);
        HandleItem handle = null;

        if (slot1.isEmpty() || slot2.isEmpty()) {
            return;
        }

        if (HandleItem.isApplicable(slot1)) {

            if (slot2.getItem() instanceof HandleItem) {

                handle = (HandleItem) slot2.getItem();
                if (slot1.getOrCreateTag().getInt("Handle") == handle.getIdentifier()) {
                    return;
                }

                this.resultSlots.setItem(0, HandleItem.apply(slot1, handle));
                this.repairItemCountCost = 1;
                this.cost.set(1);

            } else if (slot2.getItem() == Items.STICK) {

                if (slot1.getOrCreateTag().getInt("Handle") == 0) {
                    return;
                }

                this.resultSlots.setItem(0, HandleItem.clear(slot1));
                this.repairItemCountCost = 1;
                this.cost.set(1);

            }
        }
    }

}
