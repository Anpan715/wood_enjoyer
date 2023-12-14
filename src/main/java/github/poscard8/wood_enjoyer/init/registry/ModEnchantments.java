package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.enchantment.BlockTransformEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModEnchantments {

    public static final DeferredRegister<Enchantment> ALL = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, WoodEnjoyer.ID);

    public static final RegistryObject<Enchantment> MILLING = ALL.register("milling", () -> new BlockTransformEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

}
