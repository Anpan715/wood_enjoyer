package github.poscard8.wood_enjoyer.common.mixin;

import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ALL")
@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @Inject(method = "getAxeStrippingState", at = @At("HEAD"), remap = false, cancellable = true)
    private static void addStrippableLogs(BlockState state, CallbackInfoReturnable<BlockState> ci) {
        BlockWrapper wrapper = BlockUtils.getWrapper(state.getBlock());

        if (wrapper != null && BlockUtils.STRIPPED_LOGS.containsKey(wrapper)) {
            ci.setReturnValue(BlockUtils.getStrippedVariant(state.getBlock()).defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
        }
    }


}
