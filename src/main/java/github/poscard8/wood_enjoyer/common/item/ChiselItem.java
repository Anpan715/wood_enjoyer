package github.poscard8.wood_enjoyer.common.item;

import github.poscard8.wood_enjoyer.common.block.WoodSculptureBlock;
import github.poscard8.wood_enjoyer.common.config.WoodEnjoyerConfig;
import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.common.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChiselItem extends Item {

    protected static final DispenseItemBehavior DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        protected ItemStack execute(BlockSource blockSource, ItemStack stack) {

            Direction direction1 = blockSource.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos targetPos = blockSource.getPos().relative(direction1);

            Direction direction2 = direction1.getAxis() == Direction.Axis.Y ? Direction.NORTH : direction1;
            Level level = blockSource.getLevel();
            BlockState state = level.getBlockState(targetPos);
            Block block = state.getBlock();

            Item item = stack.getItem();

            boolean flag1 = WoodEnjoyerConfig.CHISEL_DISPENSE_BEHAVIOR.get();
            boolean flag2 = state.is(ModTags.Blocks.CARVEABLE);

            return flag1 && flag2 ? BlockUtils.dispenserCarve(block, level, targetPos, state, stack, (ChiselItem) item, direction2)
                    : super.execute(blockSource, stack);
        }
    };

    public ChiselItem(Properties property) {
        super(property);
        DispenserBlock.registerBehavior(this, DISPENSE_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        Player player = ctx.getPlayer();
        assert player != null;
        ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        Item item = stack.getItem();

        if (item instanceof ChiselItem chisel && player.isCrouching()) {

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, ctx.getClickedPos(), stack);
            }

            BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());

            if (state.getBlock() instanceof WoodSculptureBlock) {
                chisel.setSelectedModel(stack, state.getValue(WoodSculptureBlock.MODEL));
            } else {
                chisel.setSelectedModel(stack, null);
            }
        }
        return super.useOn(ctx);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (this.getSelectedSculptureId(stack) == 0) {
            components.add(Component.translatable("tooltip.wood_enjoyer.chisel_desc_0").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("tooltip.wood_enjoyer.chisel_desc_1").withStyle(ChatFormatting.GRAY));
        } else {
            components.add(Component.literal("Set to: " + this.getSelectedModel(stack).getCapitalizedName()).withStyle(ChatFormatting.GRAY));
        }
    }

    public int getSelectedSculptureId(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Sculpture");
    }

    public WoodSculptureBlock.Model getSelectedModel(ItemStack stack) {
        return this.getSelectedSculptureId(stack) == 0 ? null : WoodSculptureBlock.Model.byInt(stack.getOrCreateTag().getInt("Sculpture") - 1);
    }

    public void setSelectedModel(ItemStack stack, @Nullable WoodSculptureBlock.Model model) {
        stack.getOrCreateTag().putInt("Sculpture", model == null ? 0 : model.ordinal() + 1);
    }


}
