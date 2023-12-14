package github.poscard8.wood_enjoyer.common.util.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.init.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ItemWrapper implements Supplier<Item>, ItemLike {


    private final @Nullable BlockWrapper anchor;
    private final RegistryObject<Item> holder;
    private final ItemModelType modelType;

    public boolean hasItemModels = true;

    public ItemWrapper(String name, Supplier<Item> sup, ItemModelType modelType) {
        this(name, sup, modelType, null);
    }

    public ItemWrapper(String name, Supplier<Item> sup, ItemModelType modelType, BlockWrapper anchor) {
        this.holder = Registries.ITEM_REGISTRY.register(name, sup);
        this.modelType = modelType;
        this.anchor = anchor;

        if (anchor == null) {
            ModItems.ALL.add(this);
        } else {
            ModItems.ALL.add(0, this);
        }
    }

    @Override
    public Item get() {
        return holder.get();
    }

    public String getName() {
        return holder.getId().getPath();
    }

    public String getModId() {
        return holder.getId().getNamespace();
    }

    public ResourceLocation getResourceLocation() {
        return holder.getId();
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(this.getModId(), "item/" + this.getName());
    }

    public ItemModelType getModelType() {
        return this.modelType;
    }

    public ResourceLocation getParentModel() {
        String namespace;

        switch (this.getModelType()) {
            default -> namespace = ResourceLocation.DEFAULT_NAMESPACE;
            case GENERATED_SMALL, HANDHELD_SMALL -> namespace = WoodEnjoyer.ID;
        }
        return new ResourceLocation(namespace, "item/" + this.getModelType());
    }

    public BlockWrapper getAnchor() {
        return this.anchor;
    }

    public boolean hasAnchor() {
        return this.anchor != null;
    }

    public ItemWrapper noModels() {
        this.hasItemModels = false;
        return this;
    }

    public ItemStack getStack() {
        return this.get().getDefaultInstance();
    }

    public String toString() {
        return "ItemWrapper{" + holder + "}";
    }

    @Override
    public Item asItem() {
        return this.get();
    }

    public static class Registries {

        private static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WoodEnjoyer.ID);

        public static void init(IEventBus bus) {
            ITEM_REGISTRY.register(bus);
        }

    }
}
