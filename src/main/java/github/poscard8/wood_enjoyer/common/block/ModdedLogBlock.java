package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.util.ModdedWoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ModdedLogBlock extends RotatedPillarBlock {

    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

    private final ModdedWoodType type;

    public ModdedLogBlock(ModdedWoodType woodType) {
        super(woodType.getProperties());
        this.type = woodType;
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(NATURAL, true));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> definition) {
        definition.add(AXIS, NATURAL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis()).setValue(NATURAL, false);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return type == ModdedWoodType.CHESTNUT && state.getValue(NATURAL) ? new Random().nextInt(2) : 0;
    }
}
