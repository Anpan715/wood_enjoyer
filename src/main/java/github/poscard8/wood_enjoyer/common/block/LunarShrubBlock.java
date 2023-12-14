package github.poscard8.wood_enjoyer.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class LunarShrubBlock extends SaplingBlock {

    public LunarShrubBlock(AbstractTreeGrower treeGrower, Properties property) {
        super(treeGrower, property);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos position) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.END_STONE);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos position, BlockState state, boolean flag) {
        return level.getBlockState(position.below()).is(Blocks.END_STONE);
    }

    @Override
    public void advanceTree(ServerLevel serverLevel, BlockPos position, BlockState state, RandomSource random) {

        if (!serverLevel.getBlockState(position.below()).is(Blocks.END_STONE)) {
            return;
        }
        super.advanceTree(serverLevel, position, state, random);
    }
}
