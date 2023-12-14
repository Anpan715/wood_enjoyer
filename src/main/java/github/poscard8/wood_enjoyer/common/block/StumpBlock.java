package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.blockentity.StumpBlockEntity;
import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class StumpBlock extends BaseEntityBlock {

    public static final List<ItemLike> LOGS = List.of(
            Blocks.OAK_LOG, Blocks.SPRUCE_LOG,
            Blocks.BIRCH_LOG, Blocks.JUNGLE_LOG,
            Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG,
            Blocks.MANGROVE_LOG, Blocks.CHERRY_LOG,
            Blocks.BAMBOO_BLOCK, Blocks.CRIMSON_STEM,
            Blocks.WARPED_STEM, ModBlocks.WALNUT_LOG,
            ModBlocks.CHESTNUT_LOG, ModBlocks.LUNAR_LOG
    );

    public static final List<ItemLike> FIREWOODS = List.of(
            ModBlocks.OAK_FIREWOOD, ModBlocks.SPRUCE_FIREWOOD,
            ModBlocks.BIRCH_FIREWOOD, ModBlocks.JUNGLE_FIREWOOD,
            ModBlocks.ACACIA_FIREWOOD, ModBlocks.DARK_OAK_FIREWOOD,
            ModBlocks.MANGROVE_FIREWOOD, ModBlocks.CHERRY_FIREWOOD,
            ModBlocks.BAMBOO_FIREWOOD, ModBlocks.CRIMSON_FIREWOOD,
            ModBlocks.WARPED_FIREWOOD, ModBlocks.WALNUT_FIREWOOD,
            ModBlocks.CHESTNUT_FIREWOOD, ModBlocks.LUNAR_FIREWOOD
    );

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;

    protected static final VoxelShape BASE_SHAPE = BlockUtils.shape(16, 6, 16);
    protected static final VoxelShape NORTH_SHAPE = Block.box(2, 0, -2, 14, 6, 0);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(2, 0, 16, 14, 6, 18);
    protected static final VoxelShape WEST_SHAPE = Block.box(-2, 0, 2, 0, 6, 14);
    protected static final VoxelShape EAST_SHAPE = Block.box(16, 0, 2, 18, 6, 14);

    protected static final Map<BooleanProperty, VoxelShape> SHAPE_MAP = Map.of(NORTH, NORTH_SHAPE, SOUTH, SOUTH_SHAPE, WEST, WEST_SHAPE, EAST, EAST_SHAPE);

    public StumpBlock(Properties property) {
        super(property.dynamicShape());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, true)
                .setValue(SOUTH, true)
                .setValue(WEST, true)
                .setValue(EAST, true)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> def) {
        def.add(NORTH, SOUTH, WEST, EAST);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos position, CollisionContext ctx) {

        VoxelShape shape = BASE_SHAPE;

        for (BooleanProperty side : SHAPE_MAP.keySet()) {
            if (state.getValue(side)) {
                shape = Shapes.or(shape, SHAPE_MAP.get(side));
            }
        }
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos position, CollisionContext ctx) {
        return this.getShape(state, level, position, ctx);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor levelAccessor, BlockPos position, BlockPos position2) {
        return this.getStateForPlacement(levelAccessor, position);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return getStateForPlacement(ctx.getLevel(), ctx.getClickedPos());
    }

    @Nullable
    public BlockState getStateForPlacement(BlockGetter level, BlockPos position) {
        BlockPos north = position.north();
        BlockPos south = position.south();
        BlockPos west = position.west();
        BlockPos east = position.east();
        BlockState northState = level.getBlockState(north);
        BlockState southState = level.getBlockState(south);
        BlockState westState = level.getBlockState(west);
        BlockState eastState = level.getBlockState(east);

        return this.defaultBlockState()
                .setValue(NORTH, !northState.isFaceSturdy(level, north, Direction.NORTH) && !(northState.getBlock() instanceof StumpBlock))
                .setValue(SOUTH, !southState.isFaceSturdy(level, south, Direction.SOUTH) && !(southState.getBlock() instanceof StumpBlock))
                .setValue(WEST, !westState.isFaceSturdy(level, west, Direction.WEST) && !(westState.getBlock() instanceof StumpBlock))
                .setValue(EAST, !eastState.isFaceSturdy(level, east, Direction.EAST) && !(eastState.getBlock() instanceof StumpBlock));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack stack2 = player.getItemInHand(InteractionHand.OFF_HAND);


        boolean flag = LOGS.stream().map(ItemLike::asItem).toList().contains(stack2.getItem());
        boolean flag2 = stack.getItem() instanceof AxeItem;
        boolean flag3 = LOGS.stream().map(ItemLike::asItem).toList().contains(stack.getItem());

        BlockEntity blockEntity = level.getBlockEntity(position);
        if (blockEntity instanceof StumpBlockEntity stump) {

            if (stump.getLogCount() == 4) {
                stack2.getUseAnimation();
                return this.dropFirewoodUse(stump, level, player, position);
            } else if (flag && stump.getLogCount() == 0) {
                stack2.getUseAnimation();
                return this.logUse(stump, level, player, stack2);
            } else if (flag2) {
                return this.axeUse(stump, level, player, stack);
            } else if (flag3 && stump.getLogCount() == 0) {
                return this.logUse(stump, level, player, stack);
            }
        }
        return super.use(state, level, position, player, hand, hitResult);
    }

    public InteractionResult dropFirewoodUse(StumpBlockEntity stump, Level level, Player player, BlockPos position) {

        if (!player.isCreative()) {
            level.addFreshEntity(new ItemEntity(level, position.getX() + 0.5F, position.getY() + 0.5F, position.getZ() + 0.5F, new ItemStack(stump.getFirewood(), 4), 0.05F, 0.05F, 0.05F));
        }
        stump.setLogCount(0);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public InteractionResult logUse(StumpBlockEntity stump, Level level, Player player, ItemStack stack) {

        stump.setLog(stack.getItem());
        stump.setRotation(player);
        if (!player.isCreative()) {
            stack.shrink(1);
        }

        level.playSound(player, stump.getBlockPos(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1, 1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public InteractionResult axeUse(StumpBlockEntity stump, Level level, Player player, ItemStack stack) {

        if (stump.getLogCount() > 0 && stump.getLogCount() < 4) {

            stump.chop();
            stack.getUseAnimation();
            stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            level.playSound(player, stump.getBlockPos(), ModSounds.WOOD_CHOP.get(), SoundSource.BLOCKS, 1, 1);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos position, BlockState state2, boolean flag) {

        if (!state.is(state2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(position);
            if (blockEntity instanceof StumpBlockEntity stump) {
                level.addFreshEntity(new ItemEntity(level, position.getX() + 0.5F, position.getY() + 0.5F, position.getZ() + 0.5F, stump.getLoot()));
            }
            super.onRemove(state, level, position, state2, flag);
        }
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos position) {
        return 0.4F;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new StumpBlockEntity(position, state);
    }

}
