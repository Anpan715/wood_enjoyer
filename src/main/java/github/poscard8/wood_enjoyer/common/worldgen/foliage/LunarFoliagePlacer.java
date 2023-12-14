package github.poscard8.wood_enjoyer.common.worldgen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.poscard8.wood_enjoyer.init.registry.ModTreeGen;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class LunarFoliagePlacer extends FoliagePlacer {

    public static final Codec<LunarFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> foliagePlacerParts(instance)
            .and(Codec.intRange(0, 16).fieldOf("height").forGetter(x -> x.height))
            .and(Codec.intRange(0, 128).fieldOf("attempts").forGetter(x -> x.attempts)).apply(instance, LunarFoliagePlacer::new));

    private final int height;
    private final int attempts;


    public LunarFoliagePlacer(IntProvider radius, IntProvider offset, int height, int attempts) {
        super(radius, offset);
        this.height = height;
        this.attempts = attempts;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModTreeGen.LUNAR_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader simulatedReader, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration treeConfig,
                                 int p_225617_, FoliageAttachment foliageAttachment, int chance0, int chance, int p_225621_) {

        BlockPos blockpos = foliageAttachment.pos();
        BlockPos.MutableBlockPos mutable = blockpos.mutable();

        for (int i = 0; i < this.attempts; ++i) {
            mutable.setWithOffset(blockpos, random.nextInt(chance) - random.nextInt(chance), random.nextInt(chance0) - random.nextInt(chance0), random.nextInt(chance) - random.nextInt(chance));
            if (getNearbyAirCount(treeConfig.foliageProvider, random, mutable) < 5) {
                tryPlaceLeaf(simulatedReader, foliageSetter, random, treeConfig, mutable);
            }
        }
    }


    @Override
    public int foliageHeight(RandomSource random, int p_225602_, TreeConfiguration treeConfig) {
        return height;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource p_225595_, int p_225596_, int p_225597_, int p_225598_, int p_225599_, boolean p_225600_) {
        return false;
    }

    protected static int getNearbyAirCount(BlockStateProvider provider, RandomSource random, BlockPos position) {

        int count = 0;

        for (int i = 1; i >= -1; --i) {
            for (int j = 1; j >= -1; --j) {
                for (int k = 1; k >= -1; --k) {
                    if (provider.getState(random, position.offset(i, j, k)).isAir()) {
                        ++count;
                    }
                }
            }
        }
        return count;
    }

}
