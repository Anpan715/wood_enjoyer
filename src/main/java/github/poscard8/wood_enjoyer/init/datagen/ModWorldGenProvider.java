package github.poscard8.wood_enjoyer.init.datagen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.worldgen.ModBiomeModifiers;
import github.poscard8.wood_enjoyer.common.worldgen.ModBiomes;
import github.poscard8.wood_enjoyer.common.worldgen.ModConfiguredFeatures;
import github.poscard8.wood_enjoyer.common.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    protected static final RegistrySetBuilder REGISTRY_SET = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::init)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::init)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::init)
            .add(ForgeRegistries.Keys.BIOMES, ModBiomes::init);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, REGISTRY_SET, Set.of(WoodEnjoyer.ID));
    }
}
