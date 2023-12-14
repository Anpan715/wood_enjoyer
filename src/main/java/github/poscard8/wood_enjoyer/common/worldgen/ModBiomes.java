package github.poscard8.wood_enjoyer.common.worldgen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModBiomes {

    public static ResourceKey<Biome> MIXED_FOREST = registerKey("mixed_forest");


    public static void init(BootstapContext<Biome> ctx) {
        ctx.register(MIXED_FOREST, mixedForest(ctx));
    }

    protected static ResourceKey<Biome> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(WoodEnjoyer.ID, name));
    }

    public static Biome mixedForest(BootstapContext<Biome> ctx) {

        HolderGetter<PlacedFeature> feature = ctx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carver = ctx.lookup(Registries.CONFIGURED_CARVER);

        Biome.BiomeBuilder base = new Biome.BiomeBuilder();
        base.temperature(1.3F).downfall(0.5F).hasPrecipitation(true);

        MobSpawnSettings.Builder mobSpawns = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 2, 3));

        BiomeSpecialEffects.Builder special = new BiomeSpecialEffects.Builder();
        special.skyColor(8103167).fogColor(12638463);
        special.waterColor(4159204).waterFogColor(329011);
        special.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        special.backgroundMusic(Musics.GAME);
        special.grassColorOverride(7581023);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(feature, carver);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(gen);
        BiomeDefaultFeatures.addDefaultCrystalFormations(gen);
        BiomeDefaultFeatures.addDefaultMonsterRoom(gen);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(gen);
        BiomeDefaultFeatures.addDefaultSprings(gen);
        BiomeDefaultFeatures.addSurfaceFreezing(gen);
        BiomeDefaultFeatures.addDefaultOres(gen);

        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.WALNUT);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.CHESTNUT);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
        gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);

        BiomeDefaultFeatures.addSavannaGrass(gen);
        BiomeDefaultFeatures.addGiantTaigaVegetation(gen);

        base.specialEffects(special.build());
        base.generationSettings(gen.build());
        base.mobSpawnSettings(mobSpawns.build());

        return base.build();
    }

}
