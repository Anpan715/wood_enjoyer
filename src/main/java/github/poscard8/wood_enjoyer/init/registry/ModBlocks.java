package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.common.block.*;
import github.poscard8.wood_enjoyer.common.util.ModUtils;
import github.poscard8.wood_enjoyer.common.util.ModdedWoodType;
import github.poscard8.wood_enjoyer.common.util.registry.BlockModelType;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.common.worldgen.tree.ChestnutTreeGrower;
import github.poscard8.wood_enjoyer.common.worldgen.tree.LunarTreeGrower;
import github.poscard8.wood_enjoyer.common.worldgen.tree.WalnutTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public abstract class ModBlocks {

    private static final DeferredRegister<Block> PLACEHOLDER = DeferredRegister.create(ForgeRegistries.BLOCKS, "");
    public static final List<BlockWrapper> ALL = new ArrayList<>();

    public static final BlockWrapper WALNUT_LOG = log(ModdedWoodType.WALNUT);
    public static final BlockWrapper WALNUT_WOOD = wood(ModdedWoodType.WALNUT);
    public static final BlockWrapper STRIPPED_WALNUT_LOG = strippedLog(ModdedWoodType.WALNUT);
    public static final BlockWrapper STRIPPED_WALNUT_WOOD = strippedWood(ModdedWoodType.WALNUT);
    public static final BlockWrapper WALNUT_PLANKS = planks(ModdedWoodType.WALNUT).noBlockStates();
    public static final BlockWrapper WALNUT_STAIRS = stairs(ModdedWoodType.WALNUT).noBlockStates().noItemModels();
    public static final BlockWrapper WALNUT_SLAB = slab(ModdedWoodType.WALNUT).noBlockStates();
    public static final BlockWrapper WALNUT_FENCE = fence(ModdedWoodType.WALNUT).noBlockStates().noItemModels();
    public static final BlockWrapper WALNUT_FENCE_GATE = fenceGate(ModdedWoodType.WALNUT).noBlockStates();
    public static final BlockWrapper WALNUT_DOOR = door(ModdedWoodType.WALNUT);
    public static final BlockWrapper WALNUT_TRAPDOOR = trapdoor(ModdedWoodType.WALNUT);
    public static final BlockWrapper WALNUT_PRESSURE_PLATE = pressurePlate(ModdedWoodType.WALNUT).noBlockStates();
    public static final BlockWrapper WALNUT_BUTTON = button(ModdedWoodType.WALNUT).noBlockStates().noItemModels();
    public static final BlockWrapper WALNUT_SIGN = sign(ModdedWoodType.WALNUT);
    public static final BlockWrapper WALNUT_HANGING_SIGN = hangingSign(ModdedWoodType.WALNUT);

    public static final BlockWrapper CHESTNUT_LOG = log(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_WOOD = wood(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper STRIPPED_CHESTNUT_LOG = strippedLog(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper STRIPPED_CHESTNUT_WOOD = strippedWood(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_PLANKS = planks(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_STAIRS = stairs(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_SLAB = slab(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_FENCE = fence(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_FENCE_GATE = fenceGate(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_DOOR = door(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_TRAPDOOR = trapdoor(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_PRESSURE_PLATE = pressurePlate(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_BUTTON = button(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_SIGN = sign(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CHESTNUT_HANGING_SIGN = hangingSign(ModdedWoodType.CHESTNUT);

    public static final BlockWrapper LUNAR_LOG = log(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_WOOD = wood(ModdedWoodType.LUNAR);
    public static final BlockWrapper STRIPPED_LUNAR_LOG = strippedLog(ModdedWoodType.LUNAR);
    public static final BlockWrapper STRIPPED_LUNAR_WOOD = strippedWood(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_PLANKS = planks(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_STAIRS = stairs(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_SLAB = slab(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_FENCE = fence(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_FENCE_GATE = fenceGate(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_DOOR = door(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_TRAPDOOR = trapdoor(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_PRESSURE_PLATE = pressurePlate(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_BUTTON = button(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_SIGN = sign(ModdedWoodType.LUNAR);
    public static final BlockWrapper LUNAR_HANGING_SIGN = hangingSign(ModdedWoodType.LUNAR);

    public static final BlockWrapper CUT_OAK_PLANKS = cutPlanks(Blocks.OAK_PLANKS);
    public static final BlockWrapper CUT_SPRUCE_PLANKS = cutPlanks(Blocks.SPRUCE_PLANKS);
    public static final BlockWrapper CUT_BIRCH_PLANKS = cutPlanks(Blocks.BIRCH_PLANKS);
    public static final BlockWrapper CUT_JUNGLE_PLANKS = cutPlanks(Blocks.JUNGLE_PLANKS);
    public static final BlockWrapper CUT_ACACIA_PLANKS = cutPlanks(Blocks.ACACIA_PLANKS);
    public static final BlockWrapper CUT_DARK_OAK_PLANKS = cutPlanks(Blocks.DARK_OAK_PLANKS);
    public static final BlockWrapper CUT_MANGROVE_PLANKS = cutPlanks(Blocks.MANGROVE_PLANKS);
    public static final BlockWrapper CUT_CHERRY_PLANKS = cutPlanks(Blocks.CHERRY_PLANKS);
    public static final BlockWrapper CUT_BAMBOO_PLANKS = cutPlanks(Blocks.BAMBOO_PLANKS);
    public static final BlockWrapper CUT_CRIMSON_PLANKS = cutPlanks(Blocks.CRIMSON_PLANKS);
    public static final BlockWrapper CUT_WARPED_PLANKS = cutPlanks(Blocks.WARPED_PLANKS);
    public static final BlockWrapper CUT_WALNUT_PLANKS = cutPlanks(ModdedWoodType.WALNUT).noBlockStates();
    public static final BlockWrapper CUT_CHESTNUT_PLANKS = cutPlanks(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper CUT_LUNAR_PLANKS = cutPlanks(ModdedWoodType.LUNAR);

    public static final BlockWrapper OAK_SCULPTURE = sculpture(CUT_OAK_PLANKS);
    public static final BlockWrapper SPRUCE_SCULPTURE = sculpture(CUT_SPRUCE_PLANKS);
    public static final BlockWrapper BIRCH_SCULPTURE = sculpture(CUT_BIRCH_PLANKS);
    public static final BlockWrapper JUNGLE_SCULPTURE = sculpture(CUT_JUNGLE_PLANKS);
    public static final BlockWrapper ACACIA_SCULPTURE = sculpture(CUT_ACACIA_PLANKS);
    public static final BlockWrapper DARK_OAK_SCULPTURE = sculpture(CUT_DARK_OAK_PLANKS);
    public static final BlockWrapper MANGROVE_SCULPTURE = sculpture(CUT_MANGROVE_PLANKS);
    public static final BlockWrapper CHERRY_SCULPTURE = sculpture(CUT_CHERRY_PLANKS);
    public static final BlockWrapper BAMBOO_SCULPTURE = sculpture(CUT_BAMBOO_PLANKS);
    public static final BlockWrapper CRIMSON_SCULPTURE = sculpture(CUT_CRIMSON_PLANKS);
    public static final BlockWrapper WARPED_SCULPTURE = sculpture(CUT_WARPED_PLANKS);
    public static final BlockWrapper WALNUT_SCULPTURE = sculpture(CUT_WALNUT_PLANKS);
    public static final BlockWrapper CHESTNUT_SCULPTURE = sculpture(CUT_CHESTNUT_PLANKS);
    public static final BlockWrapper LUNAR_SCULPTURE = sculpture(CUT_LUNAR_PLANKS);

    public static final BlockWrapper OAK_FIREWOOD = firewood(Blocks.OAK_PLANKS);
    public static final BlockWrapper SPRUCE_FIREWOOD = firewood(Blocks.SPRUCE_PLANKS);
    public static final BlockWrapper BIRCH_FIREWOOD = firewood(Blocks.BIRCH_PLANKS);
    public static final BlockWrapper JUNGLE_FIREWOOD = firewood(Blocks.JUNGLE_PLANKS);
    public static final BlockWrapper ACACIA_FIREWOOD = firewood(Blocks.ACACIA_PLANKS);
    public static final BlockWrapper DARK_OAK_FIREWOOD = firewood(Blocks.DARK_OAK_PLANKS);
    public static final BlockWrapper MANGROVE_FIREWOOD = firewood(Blocks.MANGROVE_PLANKS);
    public static final BlockWrapper CHERRY_FIREWOOD = firewood(Blocks.CHERRY_PLANKS);
    public static final BlockWrapper BAMBOO_FIREWOOD = firewood(Blocks.BAMBOO_PLANKS);
    public static final BlockWrapper CRIMSON_FIREWOOD = firewood(Blocks.CRIMSON_PLANKS);
    public static final BlockWrapper WARPED_FIREWOOD = firewood(Blocks.WARPED_PLANKS);
    public static final BlockWrapper WALNUT_FIREWOOD = firewood(ModdedWoodType.WALNUT);
    public static final BlockWrapper CHESTNUT_FIREWOOD = firewood(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper LUNAR_FIREWOOD = firewood(ModdedWoodType.LUNAR);

    public static final BlockWrapper STUMP = new BlockWrapper("stump", () -> new StumpBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(24.0F).requiresCorrectToolForDrops()), BlockModelType.MISC).noBlockStates().noRecipes();

    public static final BlockWrapper WALNUT_LEAVES = leaves(ModdedWoodType.WALNUT);
    public static final BlockWrapper CHESTNUT_LEAVES = leaves(ModdedWoodType.CHESTNUT);
    public static final BlockWrapper LUNAR_ROOTS = new BlockWrapper("lunar_roots", () -> new LunarRootsBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).strength(1, 0.4F).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollission()), BlockModelType.MISC).noBlockStates().noItemModels().noLootTables();
    public static final BlockWrapper WALNUT_SAPLING = sapling(ModdedWoodType.WALNUT, new WalnutTreeGrower());
    public static final BlockWrapper CHESTNUT_SAPLING = sapling(ModdedWoodType.CHESTNUT, new ChestnutTreeGrower());
    public static final BlockWrapper LUNAR_SHRUB = new BlockWrapper("lunar_shrub", () -> new LunarShrubBlock(new LunarTreeGrower(), BlockBehaviour.Properties.copy(LUNAR_ROOTS.get()).instabreak()), BlockModelType.MISC).noBlockStates().noItemModels();

    protected static BlockWrapper log(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new ModdedLogBlock(woodType), BlockModelType.LOG);
    }

    protected static BlockWrapper strippedLog(ModdedWoodType woodType) {
        return new BlockWrapper("stripped_" + woodType, () -> new ModdedLogBlock(woodType), BlockModelType.LOG);
    }

    protected static BlockWrapper wood(ModdedWoodType woodType) {
        return new BlockWrapper(woodType.toString(), () -> new RotatedPillarBlock(woodType.getProperties()), BlockModelType.WOOD);
    }

    protected static BlockWrapper strippedWood(ModdedWoodType woodType) {
        return new BlockWrapper("stripped_" + woodType, () -> new RotatedPillarBlock(woodType.getProperties()), BlockModelType.WOOD);
    }

    protected static BlockWrapper planks(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new Block(woodType.withNormalStrength()), BlockModelType.PLANKS);
    }

    protected static BlockWrapper stairs(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new StairBlock(Blocks.OAK_PLANKS::defaultBlockState, woodType.withNormalStrength()), BlockModelType.STAIRS);
    }

    protected static BlockWrapper slab(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new SlabBlock(woodType.withNormalStrength()), BlockModelType.SLAB);
    }

    protected static BlockWrapper fence(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new FenceBlock(woodType.withNormalStrength()), BlockModelType.FENCE);
    }

    protected static BlockWrapper fenceGate(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new FenceGateBlock(woodType.withNormalStrength(), woodType.getRecord()), BlockModelType.FENCE_GATE);
    }

    protected static BlockWrapper door(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new DoorBlock(woodType.getProperties().strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY), BlockSetType.OAK), BlockModelType.DOOR);
    }

    protected static BlockWrapper trapdoor(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new TrapDoorBlock(woodType.getProperties().strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY).isValidSpawn((state, level, pos, type) -> false), BlockSetType.OAK), BlockModelType.TRAPDOOR);
    }

    protected static BlockWrapper pressurePlate(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, woodType.withNormalStrength(), BlockSetType.OAK), BlockModelType.PRESSURE_PLATE);
    }

    protected static BlockWrapper button(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new ButtonBlock(woodType.withNormalStrength(), BlockSetType.OAK, 30, false), BlockModelType.BUTTON);
    }

    protected static BlockWrapper sign(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new ModdedSignBlock.Standing(woodType.withNormalStrength(), woodType), BlockModelType.SIGN);
    }

    protected static BlockWrapper hangingSign(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new ModdedSignBlock.Hanging(woodType.withNormalStrength(), woodType), BlockModelType.HANGING_SIGN);
    }

    protected static BlockWrapper cutPlanks(Block planks) {
        return new BlockWrapper(ModUtils.shortenName(planks.getDescriptionId(), 7).substring(16), () -> new CutPlankBlock(BlockBehaviour.Properties.copy(planks)), BlockModelType.CUT_PLANKS).setIngredient((BlockItem) planks.asItem());
    }

    protected static BlockWrapper cutPlanks(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new CutPlankBlock(woodType.withNormalStrength()), BlockModelType.CUT_PLANKS);
    }

    protected static BlockWrapper firewood(Block planks) {
        return new BlockWrapper(ModUtils.shortenName(planks.getDescriptionId(), 7).substring(16), () -> new FirewoodBlock(planks), BlockModelType.FIREWOOD).setIngredient((BlockItem) planks.asItem());
    }

    protected static BlockWrapper firewood(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new FirewoodBlock(woodType.getProperties(), false), BlockModelType.FIREWOOD);
    }

    protected static BlockWrapper sculpture(BlockWrapper cutPlanks) {
        return new BlockWrapper(cutPlanks.getName().substring(4, cutPlanks.getName().length() - 7), () -> new WoodSculptureBlock(BlockBehaviour.Properties.copy(cutPlanks.get()).strength(1)), BlockModelType.SCULPTURE).noItemModels();
    }

    protected static BlockWrapper leaves(ModdedWoodType woodType) {
        return new BlockWrapper(woodType, () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), BlockModelType.LEAVES).noLootTables().noRecipes();
    }

    protected static BlockWrapper sapling(ModdedWoodType woodType, AbstractTreeGrower treeType) {
        return new BlockWrapper(woodType, () -> new SaplingBlock(treeType, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), BlockModelType.SAPLING);
    }

    public static void init(IEventBus bus) {
        PLACEHOLDER.register(bus);
        BlockWrapper.Registries.init(bus);
    }

    public static List<BlockItem> getCreativeTabBlocks() {
        return ALL.stream().filter(wrapper -> wrapper.getModelType() != BlockModelType.SCULPTURE).map(BlockWrapper::getItem).collect(Collectors.toList());
    }

}
