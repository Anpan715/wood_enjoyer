package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.common.entity.ModdedBoat;
import github.poscard8.wood_enjoyer.common.item.ChiselItem;
import github.poscard8.wood_enjoyer.common.item.HandleItem;
import github.poscard8.wood_enjoyer.common.item.ModBoatItem;
import github.poscard8.wood_enjoyer.common.util.registry.ItemModelType;
import github.poscard8.wood_enjoyer.common.util.registry.ItemWrapper;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class ModItems {

    private static final DeferredRegister<Item> PLACEHOLDER = DeferredRegister.create(ForgeRegistries.ITEMS, "");
    public static final List<ItemWrapper> ALL = new ArrayList<>();

    public static final ItemWrapper
            COPPER_CHISEL = new ItemWrapper("copper_chisel", () -> new ChiselItem(new Item.Properties().stacksTo(1).durability(48)), ItemModelType.HANDHELD_SMALL, ModBlocks.STUMP),
            NETHERITE_CHISEL = new ItemWrapper("netherite_chisel", () -> new ChiselItem(new Item.Properties().stacksTo(1).durability(1016)), ItemModelType.HANDHELD_SMALL, ModBlocks.STUMP),
            WARPED_HANDLE = new ItemWrapper("warped_handle", () -> new HandleItem(HandleItem.PROPERTIES, "tooltip.wood_enjoyer.warped_handle"), ItemModelType.HANDHELD_SMALL, ModBlocks.STUMP),
            LUNAR_HANDLE = new ItemWrapper("lunar_handle", () -> new HandleItem(HandleItem.PROPERTIES, "tooltip.wood_enjoyer.lunar_handle"), ItemModelType.HANDHELD_SMALL, ModBlocks.STUMP),

            WALNUT_BOAT = boat(ModdedBoat.Type.WALNUT, false),
            WALNUT_CHEST_BOAT = boat(ModdedBoat.Type.WALNUT, true),
            CHESTNUT_BOAT = boat(ModdedBoat.Type.CHESTNUT, false),
            CHESTNUT_CHEST_BOAT = boat(ModdedBoat.Type.CHESTNUT, true),

            WALNUT = new ItemWrapper("walnut", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.6F).fast().build())), ItemModelType.GENERATED_SMALL),
            CHESTNUT = new ItemWrapper("chestnut", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.8F).fast().build())), ItemModelType.GENERATED_SMALL),
            WALNUT_BOWL = new ItemWrapper("walnut_bowl", () -> new BowlFoodItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.6F).build()).stacksTo(1)), ItemModelType.GENERATED),
            CHESTNUT_BOWL = new ItemWrapper("chestnut_bowl", () -> new BowlFoodItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationMod(0.8F).build()).stacksTo(1)), ItemModelType.GENERATED),
            SWEET_PUMPKIN_PIE = new ItemWrapper("sweet_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(11).saturationMod(0.3F).fast().build())), ItemModelType.GENERATED);

    protected static ItemWrapper boat(ModdedBoat.Type type, boolean hasChest) {
        String suffix = hasChest ? "_chest_boat" : "_boat";
        return new ItemWrapper(type + suffix, () -> new ModBoatItem(hasChest, type, new Item.Properties().stacksTo(1)), ItemModelType.GENERATED, ModBlocks.STUMP);
    }

    public static void init(IEventBus bus) {
        PLACEHOLDER.register(bus);
        ItemWrapper.Registries.init(bus);
    }

    public static List<ItemWrapper> getNonAnchoredWrappers() {
        return ALL.stream().filter(wrapper -> !wrapper.hasAnchor()).toList();
    }

    public static List<ItemWrapper> getAnchoredWrappers() {
        return ALL.stream().filter(ItemWrapper::hasAnchor).toList();
    }

}
