package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class FirewoodBlock extends Block {

    public static final IntegerProperty COUNT = IntegerProperty.create("count", 1, 4);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    protected static final List<VoxelShape> X_SHAPES = List.of(
            BlockUtils.SetShapes.FIREWOOD_X_1,
            BlockUtils.SetShapes.SLAB,
            BlockUtils.SetShapes.FIREWOOD_X_3,
            BlockUtils.SetShapes.FIREWOOD_X_4
    );

    protected static final List<VoxelShape> Y_SHAPES = List.of(
            BlockUtils.SetShapes.FIREWOOD_Y_1,
            BlockUtils.SetShapes.FIREWOOD_Y_2,
            BlockUtils.SetShapes.FIREWOOD_Y_3,
            BlockUtils.SetShapes.FIREWOOD_Y_4
    );

    protected static final List<VoxelShape> Z_SHAPES = List.of(
            BlockUtils.SetShapes.FIREWOOD_Z_1,
            BlockUtils.SetShapes.SLAB,
            BlockUtils.SetShapes.FIREWOOD_Z_3,
            BlockUtils.SetShapes.FIREWOOD_Z_4
    );

    private final boolean isVanillaLog;

    public FirewoodBlock(Block log) {
        this(Properties.copy(log), true);
    }

    public FirewoodBlock(Properties property, boolean isVanillaLog) {
        super(property);
        this.registerDefaultState(this.stateDefinition.any().setValue(COUNT, 1).setValue(AXIS, Direction.Axis.Y));
        this.isVanillaLog = isVanillaLog;
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos position, CollisionContext ctx) {

        switch (state.getValue(AXIS)) {
            default -> {
                return X_SHAPES.get(state.getValue(COUNT) - 1);
            }
            case Y -> {
                return Y_SHAPES.get(state.getValue(COUNT) - 1);
            }
            case Z -> {
                return Z_SHAPES.get(state.getValue(COUNT) - 1);
            }
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> def) {
        def.add(COUNT, AXIS);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (!state.is(this)) {
            return this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis());
        }

        return state.getValue(COUNT) < 4 ? state.setValue(COUNT, state.getValue(COUNT) + 1) : this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis());
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return !ctx.isSecondaryUseActive() && ctx.getItemInHand().getItem() == this.asItem() && state.getValue(COUNT) < 4 || super.canBeReplaced(state, ctx);
    }

    public ResourceLocation getLogLocation() {
        String blockName = this.getDescriptionId().substring(19);
        String typeName = blockName.substring(0, blockName.length() - 9);
        String namespace = this.isVanillaLog ? ResourceLocation.DEFAULT_NAMESPACE : WoodEnjoyer.ID;
        String suffix;

        switch (typeName) {
            default -> suffix = "_log";
            case "crimson", "warped" -> suffix = "_stem";
            case "bamboo" -> suffix = "_block";
        }

        return new ResourceLocation(namespace, typeName + suffix);
    }

}
