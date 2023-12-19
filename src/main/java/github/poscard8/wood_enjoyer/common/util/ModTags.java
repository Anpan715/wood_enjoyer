package github.poscard8.wood_enjoyer.common.util;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ALL")
public abstract class ModTags {

    public static class Items {

        public static final TagKey<Item>
                WALNUT_LOGS = tag("walnut_logs"),
                CHESTNUT_LOGS = tag("chestnut_logs"),
                LUNAR_LOGS = tag("lunar_logs"),
                LUNAR_BLOCKS = tag("lunar_blocks"),
                CUT_PLANKS = tag("cut_planks"),
                FIREWOODS = tag("firewoods"),
                CHISELS = tag("chisels");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation(WoodEnjoyer.ID, name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block>
                WALNUT_LOGS = tag("walnut_logs"),
                CHESTNUT_LOGS = tag("chestnut_logs"),
                LUNAR_LOGS = tag("lunar_logs"),
                LUNAR_BLOCKS = tag("lunar_blocks"),
                CUT_PLANKS = tag("cut_planks"),
                SCULPTURES = tag("sculptures"),
                FIREWOODS = tag("firewoods"),
                CARVEABLE = tag("carveable");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(WoodEnjoyer.ID, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome>
                WOOD_ENJOYER_BIOME = tag("wood_enjoyer_biome"),
                CAN_GENERATE_SPARSE_WALNUT_TREE = tag("can_generate_sparse_walnut_tree"),
                CAN_GENERATE_LUNAR_TREE = tag("can_generate_lunar_tree");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(WoodEnjoyer.ID, name));
        }
    }

}
