package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.item.ChiselItem;
import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class WoodSculptureBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Model> MODEL = EnumProperty.create("model", Model.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final List<VoxelShape> SHAPES = List.of(
            BlockUtils.SetShapes.CREEPER,
            BlockUtils.SetShapes.ENDERMAN,
            BlockUtils.SetShapes.BLAZE,
            BlockUtils.SetShapes.VEX,
            BlockUtils.SetShapes.WOLF,
            BlockUtils.SetShapes.RABBIT,
            BlockUtils.SetShapes.VILLAGER,
            BlockUtils.SetShapes.SKULL,
            BlockUtils.SetShapes.ANGEL,
            BlockUtils.SetShapes.HEART,
            BlockUtils.SetShapes.GEM,
            BlockUtils.SetShapes.AXE,
            BlockUtils.SetShapes.SWORD,
            BlockUtils.SetShapes.AXE,
            BlockUtils.SetShapes.HAMMER,
            BlockUtils.SetShapes.BOOK,
            BlockUtils.SetShapes.NOTE,
            BlockUtils.SetShapes.DICE,
            BlockUtils.SetShapes.CHESS_PIECE,
            BlockUtils.SetShapes.CHESS_PIECE,
            BlockUtils.SetShapes.CHESS_PIECE,
            BlockUtils.SetShapes.CHESS_PIECE,
            BlockUtils.SetShapes.CHESS_PIECE,
            BlockUtils.SetShapes.CHESS_PIECE
    );

    public WoodSculptureBlock(Properties property) {
        super(property);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(MODEL, Model.CREEPER).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> def) {
        def.add(FACING, WATERLOGGED, MODEL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, BlockUtils.waterCheck(ctx.getLevel(), ctx.getClickedPos()));
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos position, CollisionContext ctx) {
        return BlockUtils.withDirection(SHAPES.get(state.getValue(MODEL).ordinal()), state.getValue(FACING));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {

        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();

        if (item instanceof ChiselItem chisel) {
            if (chisel.getSelectedModel(stack) != state.getValue(MODEL)) {

                stack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                level.playSound(player, position, ModSounds.WOOD_CARVE.get(), SoundSource.BLOCKS, 1, 1);

                if (((ChiselItem) item).getSelectedSculptureId(stack) == 0) {
                    level.setBlock(position, state.setValue(MODEL, state.getValue(MODEL).next()), 2);
                } else {
                    level.setBlock(position, state.setValue(MODEL, chisel.getSelectedModel(stack)), 2);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public Item asItem() {

        for (BlockWrapper wrapper : BlockUtils.SCULPTURES.values()) {
            if (Objects.equals(wrapper.get(), this)) {
                return wrapper.asItem();
            }
        }
        return ModBlocks.CUT_OAK_PLANKS.asItem();
    }

    public enum Model implements StringRepresentable {
        CREEPER,
        ENDERMAN,
        BLAZE,
        VEX,
        WOLF,
        RABBIT,
        VILLAGER,
        SKULL,
        ANGEL,
        HEART,
        GEM,
        AXE,
        SWORD,
        ANCHOR,
        HAMMER,
        BOOK,
        NOTE,
        DICE,
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING;

        @Override
        public String getSerializedName() {
            return this.toString();
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        public Component getTranslatable() {
            return Component.translatable("sculpture.wood_enjoyer." + this).withStyle(ChatFormatting.GRAY);
        }

        public Model next() {
            return this.ordinal() == 23 ? CREEPER : Arrays.stream(Model.values()).filter(model -> model.ordinal() == this.ordinal() + 1).findFirst().get();
        }

        public static Model byInt(int ordinal) {
            return Arrays.stream(Model.values()).filter(model -> model.ordinal() == ordinal).findFirst().get();
        }

    }

}
