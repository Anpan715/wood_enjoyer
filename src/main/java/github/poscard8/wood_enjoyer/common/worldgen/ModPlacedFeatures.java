package github.poscard8.wood_enjoyer.common.worldgen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> SPARSE_WALNUT = registerKey("sparse_walnut");
    public static final ResourceKey<PlacedFeature> WALNUT = registerKey("walnut");
    public static final ResourceKey<PlacedFeature> CHESTNUT = registerKey("chestnut");
    public static final ResourceKey<PlacedFeature> LUNAR = registerKey("lunar");
    public static final ResourceKey<PlacedFeature> GIGA_LUNAR = registerKey("giga_lunar");


    public static void init(BootstapContext<PlacedFeature> ctx) {


        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);

        ctx.register(SPARSE_WALNUT, new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeatures.WALNUT_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 1), ModBlocks.WALNUT_SAPLING.get())));

        ctx.register(WALNUT, new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeatures.WALNUT_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.25F, 3), ModBlocks.WALNUT_SAPLING.get())));

        ctx.register(CHESTNUT, new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeatures.CHESTNUT_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.25F, 3), ModBlocks.CHESTNUT_SAPLING.get())));

        ctx.register(LUNAR, new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeatures.LUNAR_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.0625F, 35), ModBlocks.LUNAR_SHRUB.get())));

        ctx.register(GIGA_LUNAR, new PlacedFeature(configuredFeatures.getOrThrow(ModConfiguredFeatures.GIGA_LUNAR_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.0625F, 28), ModBlocks.LUNAR_SHRUB.get())));


    }


    protected static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(WoodEnjoyer.ID, name));
    }

}
