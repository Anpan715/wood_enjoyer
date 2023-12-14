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

        public static final TagKey<Item> WALNUT_LOGS = tag("walnut_logs");
        public static final TagKey<Item> CHESTNUT_LOGS = tag("chestnut_logs");
        public static final TagKey<Item> LUNAR_LOGS = tag("lunar_logs");
        public static final TagKey<Item> LUNAR_BLOCKS = tag("lunar_blocks");
        public static final TagKey<Item> CUT_PLANKS = tag("cut_planks");
        public static final TagKey<Item> FIREWOODS = tag("firewoods");
        public static final TagKey<Item> CHISELS = tag("chisels");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation(WoodEnjoyer.ID, name));
        }

    }


    public static class Blocks {

        public static final TagKey<Block> WALNUT_LOGS = tag("walnut_logs");
        public static final TagKey<Block> CHESTNUT_LOGS = tag("chestnut_logs");
        public static final TagKey<Block> LUNAR_LOGS = tag("lunar_logs");
        public static final TagKey<Block> LUNAR_BLOCKS = tag("lunar_blocks");
        public static final TagKey<Block> CUT_PLANKS = tag("cut_planks");
        public static final TagKey<Block> SCULPTURES = tag("sculptures");
        public static final TagKey<Block> FIREWOODS = tag("firewoods");
        public static final TagKey<Block> CARVEABLE = tag("carveable");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(WoodEnjoyer.ID, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> IS_MODDED = tag("is_modded");
        public static final TagKey<Biome> CAN_GENERATE_SPARSE_WALNUT_TREE = tag("can_generate_sparse_walnut_tree");
        public static final TagKey<Biome> CAN_GENERATE_LUNAR_TREE = tag("can_generate_lunar_tree");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(WoodEnjoyer.ID, name));
        }

    }

}
