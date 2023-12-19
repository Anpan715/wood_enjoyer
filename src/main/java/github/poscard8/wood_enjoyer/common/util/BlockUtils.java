package github.poscard8.wood_enjoyer.common.util;

import com.google.common.collect.ImmutableMap;
import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.block.WoodSculptureBlock;
import github.poscard8.wood_enjoyer.common.item.ChiselItem;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public abstract class BlockUtils {

    public static final Map<BlockWrapper, BlockWrapper> STRIPPED_MODDED_LOGS = Map.of(
            ModBlocks.WALNUT_LOG, ModBlocks.STRIPPED_WALNUT_LOG,
            ModBlocks.WALNUT_WOOD, ModBlocks.STRIPPED_WALNUT_WOOD,
            ModBlocks.CHESTNUT_LOG, ModBlocks.STRIPPED_CHESTNUT_LOG,
            ModBlocks.CHESTNUT_WOOD, ModBlocks.STRIPPED_CHESTNUT_WOOD,
            ModBlocks.LUNAR_LOG, ModBlocks.STRIPPED_LUNAR_LOG,
            ModBlocks.LUNAR_WOOD, ModBlocks.STRIPPED_LUNAR_WOOD
    );

    public static final ImmutableMap<BlockWrapper, BlockWrapper> SCULPTURES = new ImmutableMap.Builder<BlockWrapper, BlockWrapper>()
            .put(ModBlocks.CUT_OAK_PLANKS, ModBlocks.OAK_SCULPTURE)
            .put(ModBlocks.CUT_SPRUCE_PLANKS, ModBlocks.SPRUCE_SCULPTURE)
            .put(ModBlocks.CUT_BIRCH_PLANKS, ModBlocks.BIRCH_SCULPTURE)
            .put(ModBlocks.CUT_JUNGLE_PLANKS, ModBlocks.JUNGLE_SCULPTURE)
            .put(ModBlocks.CUT_ACACIA_PLANKS, ModBlocks.ACACIA_SCULPTURE)
            .put(ModBlocks.CUT_DARK_OAK_PLANKS, ModBlocks.DARK_OAK_SCULPTURE)
            .put(ModBlocks.CUT_MANGROVE_PLANKS, ModBlocks.MANGROVE_SCULPTURE)
            .put(ModBlocks.CUT_CHERRY_PLANKS, ModBlocks.CHERRY_SCULPTURE)
            .put(ModBlocks.CUT_BAMBOO_PLANKS, ModBlocks.BAMBOO_SCULPTURE)
            .put(ModBlocks.CUT_CRIMSON_PLANKS, ModBlocks.CRIMSON_SCULPTURE)
            .put(ModBlocks.CUT_WARPED_PLANKS, ModBlocks.WARPED_SCULPTURE)
            .put(ModBlocks.CUT_WALNUT_PLANKS, ModBlocks.WALNUT_SCULPTURE)
            .put(ModBlocks.CUT_CHESTNUT_PLANKS, ModBlocks.CHESTNUT_SCULPTURE)
            .put(ModBlocks.CUT_LUNAR_PLANKS, ModBlocks.LUNAR_SCULPTURE).build();

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

    public static VoxelShape shape(double x, double y, double z) {
        return Block.box(8 - x / 2.0F, 0, 8 - z / 2.0F, 8 + x / 2.0F, y, 8 + z / 2.0F);
    }

    public static VoxelShape cube(double size) {
        return shape(size, size, size);
    }

    public static VoxelShape withCoordinateCheck(double x1, double y1, double z1, double x2, double y2, double z2) {

        if (1 >= x1 && x1 > x2) {
            x1 = 1 - x1;
            x2 = 1 - x2;
        }
        if (1 >= y1 && y1 > y2) {
            y1 = 1 - y1;
            y2 = 1 - y2;
        }
        if (1 >= z1 && z1 > z2) {
            z1 = 1 - z1;
            z2 = 1 - z2;
        }

        return Shapes.box(x1, y1, z1, x2, y2, z2);
    }

    public static VoxelShape withDirection(VoxelShape shape, Direction direction) {
        AtomicReference<VoxelShape> placeholder = new AtomicReference<>(shape(0, 0, 0));

        switch (direction) {
            default -> {
                return shape;
            }
            case EAST ->
                    shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> placeholder.set(Shapes.or(placeholder.get(), withCoordinateCheck(z2, y1, x1, z1, y2, x2))));
            case WEST ->
                    shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> placeholder.set(Shapes.or(placeholder.get(), withCoordinateCheck(z1, y1, x2, z2, y2, x1))));
            case SOUTH ->
                    shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> placeholder.set(Shapes.or(placeholder.get(), withCoordinateCheck(x2, y1, z2, x1, y2, z1))));
        }
        return placeholder.get();
    }

    @Nullable
    public static Block fromLocation(String path) {
        return fromLocation(new ResourceLocation(WoodEnjoyer.ID, path));
    }

    @Nullable
    public static Block fromLocation(ResourceLocation location) {
        return ForgeRegistries.BLOCKS.getValue(location);
    }

    @Nullable
    public static BlockWrapper getWrapper(Block block) {
        ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);

        for (BlockWrapper wrapper : ModBlocks.ALL) {
            if (Objects.equals(wrapper.getResourceLocation(), location)) {
                return wrapper;
            }
        }
        return null;
    }

    public static Block getStrippedVariant(Block block) {
        if (STRIPPED_MODDED_LOGS.containsKey(getWrapper(block))) {
            return STRIPPED_MODDED_LOGS.get(getWrapper(block)).get();
        } else {
            throw new RuntimeException("Block can not be stripped.");
        }
    }

    public static Block getCarvedVariant(Block block) {
        if (SCULPTURES.containsKey(getWrapper(block))) {
            return SCULPTURES.get(getWrapper(block)).get();
        } else if (SCULPTURES.containsValue(getWrapper(block))) {
            return block;
        } else {
            throw new RuntimeException("Block can not be carved.");
        }
    }

    public static ItemStack dispenserCarve(Block block, Level level, BlockPos position, BlockState state,
                                           ItemStack stack, ChiselItem chisel, Direction direction) {

        WoodSculptureBlock.Model nextModel;
        boolean waterlogged;

        try {
            nextModel = state.getValue(WoodSculptureBlock.MODEL).next();
        } catch (IllegalArgumentException exception) {
            nextModel = WoodSculptureBlock.Model.CREEPER;
        }

        try {
            waterlogged = state.getValue(WoodSculptureBlock.WATERLOGGED);
        } catch (IllegalArgumentException exception) {
            waterlogged = false;
        }

        stack.hurt(1, level.getRandom(), null);
        if (stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.setCount(0);
        }

        level.playSound(null, position, ModSounds.WOOD_CARVE.get(), SoundSource.BLOCKS, 1, 1);
        if (chisel.getSelectedSculptureId(stack) == 0) {
            level.setBlock(position, BlockUtils.getCarvedVariant(block).defaultBlockState()
                    .setValue(WoodSculptureBlock.FACING, direction)
                    .setValue(WoodSculptureBlock.MODEL, nextModel)
                    .setValue(WoodSculptureBlock.WATERLOGGED, waterlogged), 2);
        } else {
            level.setBlock(position, BlockUtils.getCarvedVariant(block).defaultBlockState()
                    .setValue(WoodSculptureBlock.FACING, direction)
                    .setValue(WoodSculptureBlock.MODEL, chisel.getSelectedModel(stack))
                    .setValue(WoodSculptureBlock.WATERLOGGED, waterlogged), 2);
        }
        return stack;
    }

    public static boolean waterCheck(Level level, BlockPos position) {
        return level.getFluidState(position).getType() == Fluids.WATER;
    }

    public static class SetShapes {

        public static final VoxelShape
                BLOCK = cube(16),
                SLAB = shape(16, 8, 16),

                FIREWOOD_X_1 = shape(16, 8, 8),
                FIREWOOD_X_3 = Shapes.or(shape(16, 8, 16), shape(16, 8, 8).move(0, 0.5F, 0.0625F)),
                FIREWOOD_X_4 = Shapes.or(shape(16, 8, 16), shape(16, 8, 13).move(0, 0.5F, -0.09375F)),
                FIREWOOD_Y_1 = shape(8, 16, 8),
                FIREWOOD_Y_2 = Shapes.or(shape(8, 16, 8).move(0.0625F, 0, 0.25F), shape(8, 16, 8).move(-0.0625F, 0, -0.25F)),
                FIREWOOD_Y_3 = Shapes.or(shape(8, 16, 8).move(0.0625F, 0, 0.25F), shape(16, 16, 8).move(0, 0, -0.25F)),
                FIREWOOD_Y_4 = Shapes.or(shape(13, 16, 8).move(-0.09375F, 0, 0.25F), shape(16, 16, 8).move(0, 0, -0.25F)),
                FIREWOOD_Z_1 = shape(8, 8, 16),
                FIREWOOD_Z_3 = Shapes.or(shape(16, 8, 16), shape(8, 8, 16).move(0.0625F, 0.5F, 0)),
                FIREWOOD_Z_4 = Shapes.or(shape(16, 8, 16), shape(13, 8, 16).move(-0.09375F, 0.5F, 0)),

                CREEPER = Shapes.or(shape(6, 3, 8), shape(6, 16, 6)),
                ENDERMAN = shape(6, 16, 12),
                BLAZE = shape(8, 15, 8),
                VEX = shape(10, 12, 6).move(0, 0, 0.0625F),
                WOLF = shape(6, 10, 16),
                RABBIT = shape(8, 9, 8).move(0, 0, 0.0625F),
                VILLAGER = shape(10, 13, 8),
                SKULL = shape(8, 10, 10).move(0, 0, 0.0625F),
                ANGEL = Shapes.or(shape(16, 10, 6), shape(8, 4, 8).move(0, 0.625F, 0)),
                HEART = shape(12, 11, 6),
                GEM = shape(12, 8, 12),
                AXE = shape(10, 16, 4),
                SWORD = shape(4, 16, 4),
                HAMMER = Shapes.or(shape(10, 5, 6), shape(2, 16, 2)),
                BOOK = shape(16, 5, 10),
                NOTE = shape(10, 10, 4),
                DICE = cube(10),
                CHESS_PIECE = Shapes.or(shape(6, 2, 6), shape(3, 13, 3));
    }

}
