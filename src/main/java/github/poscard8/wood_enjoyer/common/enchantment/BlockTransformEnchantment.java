package github.poscard8.wood_enjoyer.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class BlockTransformEnchantment extends Enchantment {

    public BlockTransformEnchantment(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.create("AXE", item -> item instanceof AxeItem), slots);
    }

    @Override
    public Map<EquipmentSlot, ItemStack> getSlotItems(LivingEntity p_44685_) {
        return super.getSlotItems(p_44685_);
    }

    public int getMinCost(int i) {
        return 15 + (i - 1) * 9;
    }

    public int getMaxCost(int i) {
        return super.getMinCost(i) + 50;
    }

    public boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment) && enchantment != Enchantments.SILK_TOUCH;
    }


}
