package github.poscard8.wood_enjoyer.common.worldgen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.worldgen.foliage.LunarFoliagePlacer;
import github.poscard8.wood_enjoyer.common.worldgen.trunk.GigaLunarTrunkPlacer;
import github.poscard8.wood_enjoyer.common.worldgen.trunk.LunarTrunkPlacer;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;

public abstract class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> WALNUT_TREE = registerKey("walnut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHESTNUT_TREE = registerKey("chestnut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_TREE = registerKey("lunar_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIGA_LUNAR_TREE = registerKey("giga_lunar_tree");

    public static void init(BootstapContext<ConfiguredFeature<?, ?>> ctx) {

        ctx.register(WALNUT_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.WALNUT_LOG.get()),
                new CherryTrunkPlacer(8, 2, 1, ConstantInt.of(3), ConstantInt.of(2), UniformInt.of(-6, -5), UniformInt.of(-4, -3)),
                BlockStateProvider.simple(ModBlocks.WALNUT_LEAVES.get()),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(3), 175),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build()));

        ctx.register(CHESTNUT_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CHESTNUT_LOG.get()),
                new CherryTrunkPlacer(7, 1, 1, ConstantInt.of(3), UniformInt.of(2, 4), UniformInt.of(-5, -4), ConstantInt.of(-3)),
                BlockStateProvider.simple(ModBlocks.CHESTNUT_LEAVES.get()),
                new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(3), 190),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build()));

        ctx.register(LUNAR_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LUNAR_LOG.get()),
                new LunarTrunkPlacer(27, 4, 5),
                BlockStateProvider.simple(ModBlocks.LUNAR_WOOD.get()),
                new LunarFoliagePlacer(ConstantInt.of(4), ConstantInt.of(1), 3, 70),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build()));

        ctx.register(GIGA_LUNAR_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LUNAR_LOG.get()),
                new GigaLunarTrunkPlacer(22, 14, 11),
                BlockStateProvider.simple(ModBlocks.LUNAR_WOOD.get()),
                new LunarFoliagePlacer(ConstantInt.of(5), ConstantInt.of(1), 3, 90),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build()));

    }

    protected static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WoodEnjoyer.ID, name));
    }

}
