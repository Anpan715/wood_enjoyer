package github.poscard8.wood_enjoyer.common.util;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.common.util.registry.ItemWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Objects;

public abstract class ModUtils {

    public static Item itemFromLocation(String path) {
        return itemFromLocation(new ResourceLocation(WoodEnjoyer.ID, path));
    }

    public static Item itemFromLocation(ResourceLocation location) {
        for (BlockWrapper wrapper : ModBlocks.ALL) {
            if (Objects.equals(wrapper.getResourceLocation(), location)) {
                return wrapper.getItem();
            }
        }

        for (ItemWrapper wrapper : ModItems.ALL) {
            if (Objects.equals(wrapper.getResourceLocation(), location)) {
                return wrapper.get();
            }
        }

        return null;
    }


    public static String shortenName(String first, int chars) {
        return first.substring(0, first.length() - chars);
    }


}
