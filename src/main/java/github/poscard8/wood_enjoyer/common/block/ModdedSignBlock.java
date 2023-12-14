package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.blockentity.ModHangingSignBlockEntity;
import github.poscard8.wood_enjoyer.common.blockentity.ModSignBlockEntity;
import github.poscard8.wood_enjoyer.common.util.ModdedWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModdedSignBlock {

    public static class Standing extends StandingSignBlock {

        public Standing(Properties property, ModdedWoodType woodType) {
            super(property, woodType.getRecord());
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
            return new ModSignBlockEntity(position, state);
        }

    }

    public static class Wall extends WallSignBlock {

        public Wall(Properties property, ModdedWoodType woodType) {
            super(property, woodType.getRecord());
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
            return new ModSignBlockEntity(position, state);
        }

    }

    public static class Hanging extends CeilingHangingSignBlock {

        public Hanging(Properties property, ModdedWoodType woodType) {
            super(property, woodType.getRecord());
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
            return new ModHangingSignBlockEntity(position, state);
        }

    }

    public static class WallHanging extends WallHangingSignBlock {

        public WallHanging(Properties property, ModdedWoodType woodType) {
            super(property, woodType.getRecord());
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
            return new ModHangingSignBlockEntity(position, state);
        }

    }

}
