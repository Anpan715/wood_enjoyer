package github.poscard8.wood_enjoyer.client;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {

    public static final ModelLayerLocation MODDED_BOAT = register("modded_boat");
    public static final ModelLayerLocation MODDED_CHEST_BOAT = register("modded_chest_boat");

    protected static ModelLayerLocation register(String name) {
        return new ModelLayerLocation(new ResourceLocation(WoodEnjoyer.ID, name), "main");
    }

}
