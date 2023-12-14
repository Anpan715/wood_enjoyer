package github.poscard8.wood_enjoyer.common.mixin;

import github.poscard8.wood_enjoyer.common.util.ModTags;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@SuppressWarnings("ALL")
@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow
    static void add(Map<Item, Integer> map, ItemLike itemLike, int ticks) {
    }

    @Shadow
    static void add(Map<Item, Integer> map, TagKey<Item> tag, int ticks) {
    }

    @Inject(method = "getFuel", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void registerBurnTimes(CallbackInfoReturnable<Map<Item, Integer>> ci, Map<Item, Integer> map) {
        add(map, ModTags.Items.FIREWOODS, 1200);
        add(map, ModBlocks.BAMBOO_FIREWOOD, 600);
    }

}
