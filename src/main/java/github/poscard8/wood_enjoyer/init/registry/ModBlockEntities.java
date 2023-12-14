package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.blockentity.ModHangingSignBlockEntity;
import github.poscard8.wood_enjoyer.common.blockentity.ModSignBlockEntity;
import github.poscard8.wood_enjoyer.common.blockentity.StumpBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> ALL = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WoodEnjoyer.ID);

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MODDED_SIGN = ALL.register("sign", () -> BlockEntityType.Builder
            .of(ModSignBlockEntity::new,
                    ModBlocks.WALNUT_SIGN.get(), ModBlocks.WALNUT_SIGN.getOther(),
                    ModBlocks.CHESTNUT_SIGN.get(), ModBlocks.CHESTNUT_SIGN.getOther(),
                    ModBlocks.LUNAR_SIGN.get(), ModBlocks.LUNAR_SIGN.getOther())
            .build(null));

    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MODDED_HANGING_SIGN = ALL.register("hanging_sign", () -> BlockEntityType.Builder
            .of(ModHangingSignBlockEntity::new,
                    ModBlocks.WALNUT_HANGING_SIGN.get(), ModBlocks.WALNUT_HANGING_SIGN.getOther(),
                    ModBlocks.CHESTNUT_HANGING_SIGN.get(), ModBlocks.CHESTNUT_HANGING_SIGN.getOther(),
                    ModBlocks.LUNAR_HANGING_SIGN.get(), ModBlocks.LUNAR_HANGING_SIGN.getOther())
            .build(null));

    public static final RegistryObject<BlockEntityType<StumpBlockEntity>> STUMP = ALL.register("stump", () -> BlockEntityType.Builder.of(StumpBlockEntity::new, ModBlocks.STUMP.get()).build(null));


}
