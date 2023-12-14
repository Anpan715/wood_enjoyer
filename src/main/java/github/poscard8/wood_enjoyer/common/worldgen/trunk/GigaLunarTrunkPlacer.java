package github.poscard8.wood_enjoyer.common.worldgen.trunk;

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
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GigaLunarTrunkPlacer extends TrunkPlacer {

    public static final Codec<GigaLunarTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> trunkPlacerParts(instance).apply(instance, GigaLunarTrunkPlacer::new));

    public GigaLunarTrunkPlacer(int height, int random1, int random2) {
        super(height, random1, random2);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeGen.GIGA_LUNAR_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer,
                                                            RandomSource random, int height, BlockPos position, TreeConfiguration treeConfig) {

        Function<BlockState, BlockState> func = Function.identity();
        Set<BlockPos> noRootPositions = Set.of(position, position.north(), position.south(), position.west(), position.east(),
                position.north().west(), position.north().east(), position.south().west(), position.south().east());

        for (BlockPos pos : noRootPositions) {
            setEndstoneAt(biConsumer, pos.below(), func);
        }

        for (int l = 0; l < 35; ++l) {
            float f = random.nextFloat() * 2.0F;

            int i = (int) (2.0F + Mth.cos(f) * (l / 8.0F));
            int k = (int) (2.0F + Mth.sin(f) * (l / 8.0F));
            BlockPos blockpos = position.offset(getNegative(i), 0, getNegative(k));

            if (!noRootPositions.contains(blockpos)) {
                setRootsAt(levelReader, biConsumer, blockpos, func);
            }
        }

        for (int i = 0; i < height; i++) {
            this.placeLogs(levelReader, biConsumer, random, position.above(i), treeConfig);
        }

        this.placeTop(levelReader, biConsumer, random, position.above(height), ModBlocks.LUNAR_WOOD.get(), treeConfig);

        for (int j = height - 4 - random.nextInt(4); j > 0; j -= random.nextInt(3)) {
            Pair<Integer, Integer> branchOffset = getBranchOffset();

            BlockPos blockpos = position.offset(branchOffset.getFirst(), j, branchOffset.getSecond());
            this.placeBlock(levelReader, biConsumer, blockpos, ModBlocks.LUNAR_WOOD.get(), func);
            this.placeBlock(levelReader, biConsumer, blockpos.below(), ModBlocks.LUNAR_WOOD.get(), func);

        }

        return List.of(new FoliagePlacer.FoliageAttachment(position.above(height), 1, true));
    }

    protected void placeLogs(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource random,
                             BlockPos position, TreeConfiguration treeConfig) {

        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                BlockPos pos = position.offset(i, 0, k);

                if (this.validTreePos(levelReader, pos)) {
                    biConsumer.accept(pos, treeConfig.trunkProvider.getState(random, pos));
                }
            }
        }
    }

    protected void placeTop(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource random,
                            BlockPos position, Block block, TreeConfiguration treeConfig) {

        for (int i = -1; i <= 1; i++) {
            for (int k = -1; k <= 1; k++) {
                int j = random.nextInt(3);
                BlockPos pos = position.offset(i, 0, k);

                if (this.validTreePos(levelReader, pos)) {
                    if (j == 2) {
                        biConsumer.accept(pos, treeConfig.trunkProvider.getState(random, pos));
                        biConsumer.accept(pos.above(), block.defaultBlockState());
                    } else if (j == 1) {
                        biConsumer.accept(pos, block.defaultBlockState());
                    }
                }
            }
        }
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

        int offset = random.nextInt(2) * 4 - 2;
        int orthogonalOffset = random.nextInt(2) * 2 - 1;
        int sidePicker = random.nextInt(2);

        return sidePicker == 1 ? Pair.of(orthogonalOffset, offset) : Pair.of(offset, orthogonalOffset);
    }
}
