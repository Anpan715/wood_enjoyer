package github.poscard8.wood_enjoyer.common.util;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ItemUtils {

    public static Item fromLocation(String path) {
        return fromLocation(new ResourceLocation(WoodEnjoyer.ID, path));
    }

    public static Item fromLocation(ResourceLocation location) {
        return ForgeRegistries.ITEMS.getValue(location);
    }


}
