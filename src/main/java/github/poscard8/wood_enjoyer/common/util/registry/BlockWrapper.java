package github.poscard8.wood_enjoyer.common.util.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.block.ModdedSignBlock;
import github.poscard8.wood_enjoyer.common.util.ItemUtils;
import github.poscard8.wood_enjoyer.common.util.ModdedWoodType;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/*
Exists to make block registration and JSON file generation easier.
Registers blocks and block items at once.
Still uses deferred register and registry objects, but they are hidden/private.
Most methods utilize BlockModelType class, which is customized for my needs.
*/

public class BlockWrapper implements Supplier<Block>, ItemLike {

    private final RegistryObject<Block> blockHolder;
    private final RegistryObject<Block> otherHolder;
    private final RegistryObject<BlockItem> itemHolder;
    private final BlockModelType blockModelType;
    private final String typeName;
    private BlockItem ingredient = null;

    public boolean hasBlockStates = true;
    public boolean hasItemModels = true;
    public boolean hasLootTables = true;
    public boolean hasRecipes = true;

    public BlockWrapper(ModdedWoodType woodType, Supplier<? extends Block> sup, BlockModelType blockModelType) {
        this(woodType.toString(), sup, blockModelType);
    }

    public BlockWrapper(String typeName, Supplier<? extends Block> sup, BlockModelType blockModelType) {

        String blockName = this.getBlockName(typeName, blockModelType);

        this.typeName = typeName;
        this.blockModelType = blockModelType;
        this.blockHolder = Registries.BLOCK_REGISTRY.register(blockName, sup);
        this.otherHolder = this.registerOther(typeName);
        this.itemHolder = this.registerItem(blockName);
        ModBlocks.ALL.add(this);
    }

    private String getBlockName(String typeName, BlockModelType blockModelType) {
        switch (blockModelType) {
            default -> {
                return typeName + "_" + blockModelType;
            }
            case CUT_PLANKS -> {
                return "cut_" + typeName + "_planks";
            }
            case MISC -> {
                return typeName;
            }
        }
    }

    @Nullable
    private RegistryObject<Block> registerOther(String typeName) {
        switch (this.getModelType()) {
            default -> {
                return null;
            }
            case SIGN -> {
                return Registries.BLOCK_REGISTRY.register(typeName + "_wall_" + blockModelType, () -> new ModdedSignBlock.Wall(BlockBehaviour.Properties.copy(this.blockHolder.get()), ModdedWoodType.byName(typeName)));
            }
            case HANGING_SIGN -> {
                return Registries.BLOCK_REGISTRY.register(typeName + "_wall_" + blockModelType, () -> new ModdedSignBlock.WallHanging(BlockBehaviour.Properties.copy(this.blockHolder.get()), ModdedWoodType.byName(typeName)));
            }
        }
    }

    @Nullable
    private RegistryObject<BlockItem> registerItem(String name) {
        switch (this.getModelType()) {
            default -> {
                return Registries.ITEM_REGISTRY.register(name, () -> new BlockItem(this.get(), new Item.Properties()));
            }
            case SIGN -> {
                return Registries.ITEM_REGISTRY.register(name, () -> new SignItem(new Item.Properties().stacksTo(16), this.get(), this.getOther()));
            }
            case HANGING_SIGN -> {
                return Registries.ITEM_REGISTRY.register(name, () -> new HangingSignItem(this.get(), this.getOther(), new Item.Properties().stacksTo(16)));
            }
            case SCULPTURE -> {
                return null;
            }
        }
    }

    public Block get() {
        return blockHolder.get();
    }

    public Block getOther() {
        return this.otherHolder == null ? Blocks.AIR : this.otherHolder.get();
    }

    public BlockItem getItem() {
        return this.getModelType() == BlockModelType.SCULPTURE ? this.getIngredient() : itemHolder.get();
    }

    public String getName() {
        return blockHolder.getId().getPath();
    }

    public String getModId() {
        return blockHolder.getId().getNamespace();
    }

    public ResourceLocation getResourceLocation() {
        return blockHolder.getId();
    }

    public String getTypeName() {
        return this.typeName;
    }

    public BlockModelType getModelType() {
        return this.blockModelType;
    }

    public @Nullable BlockItem getIngredient() {
        if (this.ingredient != null) {
            return this.ingredient;
        }

        String name;

        switch (this.getModelType()) {
            default -> name = typeName + "_planks";
            case WOOD, PLANKS -> name = typeName + "_log";
            case SCULPTURE -> name = "cut_" + typeName + "_planks";
            case HANGING_SIGN -> name = "stripped_" + typeName + "_log";
            case LOG, FIREWOOD, LEAVES -> {
                return null;
            }
        }
        return (BlockItem) ItemUtils.fromLocation(name);
    }


    public ResourceLocation getTextureLocation() {
        return this.getTextureLocation(TextureType.DEFAULT);
    }

    public ResourceLocation getTextureLocation(TextureType textureType) {
        String textureName;

        switch (this.getModelType()) {
            default -> textureName = this.typeName + "_planks";
            case LOG, DOOR -> textureName = this.getName() + textureType;
            case WOOD, FIREWOOD -> textureName = this.typeName + "_log" + textureType;
            case PLANKS, CUT_PLANKS, TRAPDOOR, LEAVES -> textureName = this.getName();
            case SCULPTURE -> textureName = "cut_" + this.typeName + "_planks";
            case HANGING_SIGN -> textureName = "stripped_" + this.typeName + "_log_side";
        }
        return new ResourceLocation(WoodEnjoyer.ID, "block/" + textureName);
    }

    public ResourceLocation getBlockModelLocation() {
        return new ResourceLocation(WoodEnjoyer.ID, "block/" + this.getName());
    }

    public BlockWrapper setIngredient(BlockItem ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public BlockWrapper noBlockStates() {
        this.hasBlockStates = false;
        return this;
    }

    public BlockWrapper noItemModels() {
        this.hasItemModels = false;
        return this;
    }

    public BlockWrapper noLootTables() {
        this.hasLootTables = false;
        return this;
    }

    public BlockWrapper noRecipes() {
        this.hasRecipes = false;
        return this;
    }

    public boolean isModdedWood() {
        return Objects.equals(typeName, "walnut") || Objects.equals(typeName, "chestnut") || Objects.equals(typeName, "lunar");
    }

    public ItemStack getStack() {
        return this.getItem().getDefaultInstance();
    }

    public String toString() {
        return "BlockWrapper{" + blockHolder + "}";
    }

    public @NotNull Item asItem() {
        return this.getItem();
    }

    public static class Registries {

        private static final DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, WoodEnjoyer.ID);
        private static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WoodEnjoyer.ID);

        public static void init(IEventBus bus) {
            BLOCK_REGISTRY.register(bus);
            ITEM_REGISTRY.register(bus);
        }

    }
}
