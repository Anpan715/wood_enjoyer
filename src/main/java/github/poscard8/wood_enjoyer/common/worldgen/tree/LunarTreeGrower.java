package github.poscard8.wood_enjoyer.common.worldgen.tree;

import github.poscard8.wood_enjoyer.common.worldgen.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

public class LunarTreeGrower extends AbstractMegaTreeGrower {

    public boolean growTree(ServerLevel serverLevel, ChunkGenerator chunkGen, BlockPos position, BlockState state, RandomSource random) {

        for (int i = 1; i >= -1; --i) {
            for (int k = 1; k >= -1; --k) {

                if (isThreeByThreeSapling(state, serverLevel, position, i, k)) {
                    return this.placeGiga(serverLevel, chunkGen, position, state, random, i, k);
                } else if (isTwoByTwoSapling(state, serverLevel, position, i, k)) {
                    return this.placeMega(serverLevel, chunkGen, position, state, random, i, k);
                }
            }
        }
        return false;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean flag) {
        return ModConfiguredFeatures.LUNAR_TREE;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
        return ModConfiguredFeatures.LUNAR_TREE;
    }

    @Nullable
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredGigaFeature() {
        return ModConfiguredFeatures.GIGA_LUNAR_TREE;
    }

    protected boolean placeGiga(ServerLevel serverLevel, ChunkGenerator chunkGen, BlockPos position, BlockState state, RandomSource random, int x, int z) {

        BlockState blockstate = Blocks.AIR.defaultBlockState();
        serverLevel.setBlock(position.offset(x - 1, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x - 1, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x - 1, 0, z + 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z + 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z + 1), blockstate, 4);

        ResourceKey<ConfiguredFeature<?, ?>> resourcekey = this.getConfiguredGigaFeature();
        if (resourcekey == null) return false;

        Holder<ConfiguredFeature<?, ?>> holder = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(resourcekey).orElse(null);
        var event = ForgeEventFactory.blockGrowFeature(serverLevel, random, position, holder);
        holder = event.getFeature();

        if (event.getResult() == Event.Result.DENY) return false;
        if (holder == null) return false;

        ConfiguredFeature<?, ?> configuredfeature = holder.value();

        serverLevel.setBlock(position.offset(x - 1, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x - 1, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x - 1, 0, z + 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x, 0, z + 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z - 1), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z), blockstate, 4);
        serverLevel.setBlock(position.offset(x + 1, 0, z + 1), blockstate, 4);

        return configuredfeature.place(serverLevel, chunkGen, random, position.offset(x, 0, z));

    }

    protected static boolean isThreeByThreeSapling(BlockState state, BlockGetter level, BlockPos position, int x, int z) {
        Block block = state.getBlock();
        return level.getBlockState(position.offset(x - 1, 0, z - 1)).is(block) && level.getBlockState(position.offset(x - 1, 0, z)).is(block) && level.getBlockState(position.offset(x - 1, 0, z + 1)).is(block)
                && level.getBlockState(position.offset(x, 0, z - 1)).is(block) && level.getBlockState(position.offset(x, 0, z)).is(block) && level.getBlockState(position.offset(x, 0, z + 1)).is(block)
                && level.getBlockState(position.offset(x + 1, 0, z - 1)).is(block) && level.getBlockState(position.offset(x + 1, 0, z)).is(block) && level.getBlockState(position.offset(x + 1, 0, z + 1)).is(block);
    }


}
