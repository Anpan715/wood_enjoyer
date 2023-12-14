package github.poscard8.wood_enjoyer.common.worldgen.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModTreeGen;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LunarTrunkPlacer extends GiantTrunkPlacer {

    public static final Codec<LunarTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> trunkPlacerParts(instance).apply(instance, LunarTrunkPlacer::new));

    public LunarTrunkPlacer(int height, int random1, int random2) {
        super(height, random1, random2);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeGen.LUNAR_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer,
                                                            RandomSource random, int height, BlockPos position, TreeConfiguration treeConfig) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.addAll(super.placeTrunk(levelReader, biConsumer, random, height, position, treeConfig));
        Function<BlockState, BlockState> func = Function.identity();

        setEndstoneAt(biConsumer, position.below(), func);
        setEndstoneAt(biConsumer, position.east().below(), func);
        setEndstoneAt(biConsumer, position.south().below(), func);
        setEndstoneAt(biConsumer, position.south().east().below(), func);


        for (int l = 0; l < 30; ++l) {
            float f = random.nextFloat() * 2.0F;

            int i = (int) (1.5F + Mth.cos(f) * (l / 8.0F));
            int k = (int) (1.5F + Mth.sin(f) * (l / 8.0F));
            BlockPos blockpos = position.offset(getNegative(i), 0, getNegative(k));

            if (!Set.of(position, position.east(), position.south(), position.south().east()).contains(blockpos)) {
                setRootsAt(levelReader, biConsumer, blockpos, Function.identity());
            }
        }

        for (int j = height - 4 - random.nextInt(4); j > 0; j -= random.nextInt(4)) {
            Pair<Integer, Integer> branchOffset = getBranchOffset();

            BlockPos blockpos = position.offset(branchOffset.getFirst(), j, branchOffset.getSecond());
            this.placeBlock(levelReader, biConsumer, blockpos, ModBlocks.LUNAR_WOOD.get(), func);
            this.placeBlock(levelReader, biConsumer, blockpos.below(), ModBlocks.LUNAR_WOOD.get(), func);

        }
        return list;
    }

    protected void placeBlock(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, BlockPos position,
                              Block block, Function<BlockState, BlockState> func) {
        if (this.validTreePos(levelReader, position)) {
            biConsumer.accept(position, func.apply(block.defaultBlockState()));
        }
    }

    protected static void setEndstoneAt(BiConsumer<BlockPos, BlockState> biConsumer, BlockPos position, Function<BlockState, BlockState> func) {
        biConsumer.accept(position, func.apply(Blocks.END_STONE.defaultBlockState()));
    }

    protected static void setRootsAt(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, BlockPos position, Function<BlockState, BlockState> func) {
        if (levelReader.isStateAtPosition(position, BlockBehaviour.BlockStateBase::isAir)) {
            biConsumer.accept(position, func.apply(ModBlocks.LUNAR_ROOTS.get().defaultBlockState()));
        }
    }

    protected static int getNegative(int num) {
        return new Random().nextInt(2) == 1 ? num * -1 : num;
    }

    protected static Pair<Integer, Integer> getBranchOffset() {
        Random random = new Random();

        int offset = random.nextInt(2) == 1 ? 2 : -1;
        int orthogonalOffset = random.nextInt(2);
        int sidePicker = random.nextInt(2);

        return sidePicker == 1 ? Pair.of(orthogonalOffset, offset) : Pair.of(offset, orthogonalOffset);
    }


}
