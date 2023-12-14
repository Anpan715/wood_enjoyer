package github.poscard8.wood_enjoyer.common.worldgen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.util.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> SPARSE_WALNUT = registerKey("sparse_walnut");
    public static final ResourceKey<BiomeModifier> LUNAR = registerKey("lunar");
    public static final ResourceKey<BiomeModifier> GIGA_LUNAR = registerKey("giga_lunar");


    public static void init(BootstapContext<BiomeModifier> ctx) {

        HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = ctx.lookup(ForgeRegistries.Keys.BIOMES);

        ctx.register(SPARSE_WALNUT, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.CAN_GENERATE_SPARSE_WALNUT_TREE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SPARSE_WALNUT)),
                GenerationStep.Decoration.FLUID_SPRINGS
        ));

        ctx.register(LUNAR, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.CAN_GENERATE_LUNAR_TREE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LUNAR)),
                GenerationStep.Decoration.FLUID_SPRINGS
        ));

        ctx.register(GIGA_LUNAR, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.CAN_GENERATE_LUNAR_TREE),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GIGA_LUNAR)),
                GenerationStep.Decoration.FLUID_SPRINGS
        ));


    }

    protected static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(WoodEnjoyer.ID, name));
    }

}
