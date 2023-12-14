package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class LunarRootsBlock extends BushBlock {

    public LunarRootsBlock(Properties property) {
        super(property);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos position, CollisionContext ctx) {
        return BlockUtils.shape(14, 4, 14);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos position) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.END_STONE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos position, PathComputationType pathComputation) {
        return false;
    }
}
