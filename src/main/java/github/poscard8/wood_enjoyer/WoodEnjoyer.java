package github.poscard8.wood_enjoyer;

import com.mojang.datafixers.util.Pair;
import github.poscard8.wood_enjoyer.client.ModModelLayers;
import github.poscard8.wood_enjoyer.client.model.ModdedBoatModel;
import github.poscard8.wood_enjoyer.client.model.ModdedChestBoatModel;
import github.poscard8.wood_enjoyer.client.renderer.ModdedBoatRenderer;
import github.poscard8.wood_enjoyer.client.renderer.StumpRenderer;
import github.poscard8.wood_enjoyer.common.config.WoodEnjoyerConfig;
import github.poscard8.wood_enjoyer.common.mixin.FireBlockInvoker;
import github.poscard8.wood_enjoyer.common.util.registry.BlockModelType;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.common.util.registry.ItemWrapper;
import github.poscard8.wood_enjoyer.init.datagen.*;
import github.poscard8.wood_enjoyer.init.registry.*;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mod(WoodEnjoyer.ID)
public class WoodEnjoyer {

    public static final String ID = "wood_enjoyer";
    private static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ID, "tab"));

    public WoodEnjoyer() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.init(bus);
        ModItems.init(bus);
        ModTreeGen.init(bus);

        ModEntities.ALL.register(bus);
        ModBlockEntities.ALL.register(bus);
        ModEnchantments.ALL.register(bus);
        ModSounds.ALL.register(bus);

        ModLoadingContext ctx = ModLoadingContext.get();
        ctx.registerConfig(ModConfig.Type.COMMON, WoodEnjoyerConfig.SPEC, "wood_enjoyer.toml");

    }

    @SuppressWarnings("ALL")
    @Mod.EventBusSubscriber(modid = ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static final class CommonEvents {

        @SubscribeEvent
        static void generateJsonFiles(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            ExistingFileHelper helper = event.getExistingFileHelper();
            PackOutput packOutput = generator.getPackOutput();

            generator.addProvider(true, new ModBlockStateProvider(packOutput, helper));
            generator.addProvider(true, new ModItemModelProvider(packOutput, helper));
            generator.addProvider(true, new ModLootTableProvider(packOutput));
            generator.addProvider(true, new ModRecipeProvider(packOutput));
            generator.addProvider(true, new ModWorldGenProvider(packOutput, event.getLookupProvider()));
        }

        @SubscribeEvent
        static void registerCreativeTab(RegisterEvent event) {
            event.register(Registries.CREATIVE_MODE_TAB, helper -> {
                helper.register(TAB_KEY, CreativeModeTab.builder()
                        .title(Component.translatable("tab.wood_enjoyer"))
                        .icon(ModBlocks.WALNUT_PLANKS.getItem()::getDefaultInstance)
                        .displayItems((params, output) -> {
                            ModBlocks.getCreativeTabBlocks().forEach(output::accept);
                            ModItems.getNonAnchoredWrappers().forEach(output::accept);
                        })
                        .build());
            });
        }

        @SubscribeEvent
        static void registerTabContent(BuildCreativeModeTabContentsEvent event) {

            if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

                for (BlockWrapper wrapper : ModBlocks.ALL) {

                    if (!wrapper.isModdedWood()) {
                        Item button = ForgeRegistries.ITEMS.getValue(new ResourceLocation(wrapper.getTypeName() + "_button"));
                        Item cutPlanks = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ID, "cut_" + wrapper.getTypeName() + "_planks"));

                        if (wrapper.getModelType() == BlockModelType.CUT_PLANKS) {
                            event.getEntries().putAfter(button.getDefaultInstance(), wrapper.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        } else if (wrapper.getModelType() == BlockModelType.FIREWOOD) {
                            event.getEntries().putAfter(cutPlanks.getDefaultInstance(), wrapper.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        }
                    }
                }

            } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {

                event.getEntries().putBefore(Items.RAIL.getDefaultInstance(), ModItems.WALNUT_BOAT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.RAIL.getDefaultInstance(), ModItems.WALNUT_CHEST_BOAT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.RAIL.getDefaultInstance(), ModItems.CHESTNUT_BOAT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.RAIL.getDefaultInstance(), ModItems.CHESTNUT_CHEST_BOAT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {

                event.getEntries().putBefore(Items.PUMPKIN_PIE.getDefaultInstance(), ModItems.WALNUT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.PUMPKIN_PIE.getDefaultInstance(), ModItems.CHESTNUT.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.PUMPKIN_PIE.getDefaultInstance(), ModItems.WALNUT_BOWL.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putBefore(Items.PUMPKIN_PIE.getDefaultInstance(), ModItems.CHESTNUT_BOWL.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                event.getEntries().putAfter(Items.PUMPKIN_PIE.getDefaultInstance(), ModItems.SWEET_PUMPKIN_PIE.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            } else if (event.getTabKey() == TAB_KEY) {

                List<ItemWrapper> anchored = ModItems.getAnchoredWrappers();

                for (ItemWrapper wrapper : anchored) {
                    event.getEntries().putAfter(wrapper.getAnchor().getStack(), wrapper.getStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }

        @SubscribeEvent
        static void registerFlammableBlocks(FMLCommonSetupEvent event) {

            FireBlock fireBlock = (FireBlock) Blocks.FIRE;

            Set<String> nonFlammable = Set.of("crimson", "warped", "lunar", "stump");
            Map<BlockModelType, Pair<Integer, Integer>> map = Map.of(
                    BlockModelType.LOG, Pair.of(5, 5),
                    BlockModelType.WOOD, Pair.of(5, 5),
                    BlockModelType.PLANKS, Pair.of(5, 20),
                    BlockModelType.STAIRS, Pair.of(5, 20),
                    BlockModelType.SLAB, Pair.of(5, 20),
                    BlockModelType.FENCE, Pair.of(5, 20),
                    BlockModelType.FENCE_GATE, Pair.of(5, 20),
                    BlockModelType.CUT_PLANKS, Pair.of(5, 20),
                    BlockModelType.FIREWOOD, Pair.of(10, 40),
                    BlockModelType.LEAVES, Pair.of(30, 60)
            );

            for (BlockWrapper wrapper : ModBlocks.ALL) {
                if (!nonFlammable.contains(wrapper.getTypeName()) && map.keySet().contains(wrapper.getModelType())) {

                    ((FireBlockInvoker) fireBlock).invokeSetFlammable(wrapper.get(),
                            map.get(wrapper.getModelType()).getFirst(),
                            map.get(wrapper.getModelType()).getSecond());
                }
            }

        }

    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("ALL")
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static final class ClientEvents {

        @SubscribeEvent
        static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.MODDED_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MODDED_HANGING_SIGN.get(), HangingSignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.STUMP.get(), StumpRenderer::new);
            event.registerEntityRenderer(ModEntities.MODDED_BOAT.get(), context -> new ModdedBoatRenderer(context, false));
            event.registerEntityRenderer(ModEntities.MODDED_CHEST_BOAT.get(), context -> new ModdedBoatRenderer(context, true));
        }

        @SubscribeEvent
        static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.MODDED_BOAT, ModdedBoatModel::createMainLayer);
            event.registerLayerDefinition(ModModelLayers.MODDED_CHEST_BOAT, ModdedChestBoatModel::createMainLayer);
        }

    }


}
