package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.worldgen.foliage.LunarFoliagePlacer;
import github.poscard8.wood_enjoyer.common.worldgen.trunk.GigaLunarTrunkPlacer;
import github.poscard8.wood_enjoyer.common.worldgen.trunk.LunarTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModTreeGen {


    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, WoodEnjoyer.ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, WoodEnjoyer.ID);

    public static final RegistryObject<TrunkPlacerType<LunarTrunkPlacer>> LUNAR_TRUNK_PLACER = TRUNK_PLACERS.register("lunar_trunk_placer",
            () -> new TrunkPlacerType<>(LunarTrunkPlacer.CODEC));

    public static final RegistryObject<TrunkPlacerType<GigaLunarTrunkPlacer>> GIGA_LUNAR_TRUNK_PLACER = TRUNK_PLACERS.register("giga_lunar_trunk_placer",
            () -> new TrunkPlacerType<>(GigaLunarTrunkPlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<LunarFoliagePlacer>> LUNAR_FOLIAGE_PLACER = FOLIAGE_PLACERS.register("lunar_foliage_placer",
            () -> new FoliagePlacerType<>(LunarFoliagePlacer.CODEC));

    public static void init(IEventBus bus) {
        TRUNK_PLACERS.register(bus);
        FOLIAGE_PLACERS.register(bus);
    }


}
