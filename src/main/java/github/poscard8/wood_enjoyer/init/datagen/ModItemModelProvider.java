package github.poscard8.wood_enjoyer.init.datagen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.common.util.registry.ItemWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WoodEnjoyer.ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModBlocks.ALL.forEach(this::readBlockWrappers);
        ModItems.ALL.forEach(this::readItemWrappers);
    }

    protected void readBlockWrappers(BlockWrapper wrapper) {
        if (!wrapper.hasItemModels) {
            return;
        }

        switch (wrapper.getModelType().itemModel) {
            default -> withExistingParent(wrapper.getName(), wrapper.getBlockModelLocation());
            case "fence" -> fenceInventory(wrapper.getName(), wrapper.getTextureLocation());
            case "button" -> buttonInventory(wrapper.getName(), wrapper.getTextureLocation());
            case "trapdoor" -> trapdoorBottom(wrapper.getName(), wrapper.getTextureLocation());
            case "firewood" ->
                    withExistingParent(wrapper.getName(), new ResourceLocation(WoodEnjoyer.ID, "block/" + wrapper.getName() + "_1"));
            case "generated" -> basicItem(wrapper.getItem());
        }
    }

    protected void readItemWrappers(ItemWrapper wrapper) {
        if (!wrapper.hasItemModels) {
            return;
        }

        withExistingParent(wrapper.getName(), wrapper.getParentModel()).texture("layer0", wrapper.getTextureLocation());
    }


}
