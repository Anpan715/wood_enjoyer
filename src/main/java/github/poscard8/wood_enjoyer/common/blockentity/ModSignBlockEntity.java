package github.poscard8.wood_enjoyer.common.blockentity;

import github.poscard8.wood_enjoyer.init.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModSignBlockEntity extends SignBlockEntity {

    public ModSignBlockEntity(BlockPos position, BlockState state) {
        super(position, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.MODDED_SIGN.get();
    }
}
