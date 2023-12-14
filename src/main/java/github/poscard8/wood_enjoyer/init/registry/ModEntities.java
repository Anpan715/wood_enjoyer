package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.entity.ModdedBoat;
import github.poscard8.wood_enjoyer.common.entity.ModdedChestBoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModEntities {

    public static final DeferredRegister<EntityType<?>> ALL = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WoodEnjoyer.ID);

    public static final RegistryObject<EntityType<ModdedBoat>> MODDED_BOAT = ALL.register("boat", () -> EntityType.Builder.<ModdedBoat>of(ModdedBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("wood_enjoyer:boat"));
    public static final RegistryObject<EntityType<ModdedChestBoat>> MODDED_CHEST_BOAT = ALL.register("chest_boat", () -> EntityType.Builder.<ModdedChestBoat>of(ModdedChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build("wood_enjoyer:chest_boat"));

}
