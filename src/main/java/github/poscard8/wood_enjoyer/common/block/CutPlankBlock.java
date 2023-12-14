package github.poscard8.wood_enjoyer.common.block;

import github.poscard8.wood_enjoyer.common.item.ChiselItem;
import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.init.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Set;

@SuppressWarnings("deprecation")
public class CutPlankBlock extends Block {

    public CutPlankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos position, Player player, InteractionHand hand, BlockHitResult hitResult) {

        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        Direction direction = Set.of(Direction.UP, Direction.DOWN).contains(hitResult.getDirection()) ? Direction.NORTH : hitResult.getDirection();

        if (item instanceof ChiselItem chisel) {

            stack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
            level.playSound(player, position, ModSounds.WOOD_CARVE.get(), SoundSource.BLOCKS, 1, 1);
            if (chisel.getSelectedSculptureId(stack) == 0) {
                level.setBlock(position, BlockUtils.getCarvedVariant(this).defaultBlockState().setValue(WoodSculptureBlock.FACING, direction), 2);
            } else {
                level.setBlock(position, BlockUtils.getCarvedVariant(this).defaultBlockState().setValue(WoodSculptureBlock.FACING, direction)
                        .setValue(WoodSculptureBlock.MODEL, chisel.getSelectedModel(stack)), 2);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
