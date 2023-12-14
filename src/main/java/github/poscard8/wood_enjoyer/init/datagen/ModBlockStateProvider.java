package github.poscard8.wood_enjoyer.init.datagen;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import github.poscard8.wood_enjoyer.common.block.FirewoodBlock;
import github.poscard8.wood_enjoyer.common.block.ModdedLogBlock;
import github.poscard8.wood_enjoyer.common.block.WoodSculptureBlock;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.common.util.registry.TextureType;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WoodEnjoyer.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.ALL.forEach(this::readBlockWrappers);
    }

    protected void readBlockWrappers(BlockWrapper wrapper) {
        if (!wrapper.hasBlockStates) {
            return;
        }

        switch (wrapper.getModelType()) {
            default -> {
            }
            case LOG ->
                    axisBlock((ModdedLogBlock) wrapper.get(), wrapper.getTextureLocation(TextureType.SIDE), wrapper.getTextureLocation(TextureType.TOP));
            case WOOD ->
                    axisBlock((RotatedPillarBlock) wrapper.get(), wrapper.getTextureLocation(TextureType.SIDE), wrapper.getTextureLocation(TextureType.SIDE));
            case FIREWOOD -> firewoodBlock(wrapper);
            case PLANKS, CUT_PLANKS -> simpleBlock(wrapper.get(), cubeAll(wrapper.get()));
            case SCULPTURE -> sculptureBlock(wrapper);
            case STAIRS -> stairsBlock((StairBlock) wrapper.get(), wrapper.getTextureLocation());
            case SLAB ->
                    slabBlock((SlabBlock) wrapper.get(), wrapper.getTextureLocation(), wrapper.getTextureLocation());
            case FENCE -> fenceBlock((FenceBlock) wrapper.get(), wrapper.getTextureLocation());
            case FENCE_GATE -> fenceGateBlock((FenceGateBlock) wrapper.get(), wrapper.getTextureLocation());
            case DOOR ->
                    doorBlockWithRenderType((DoorBlock) wrapper.get(), wrapper.getTextureLocation(TextureType.BOTTOM), wrapper.getTextureLocation(TextureType.TOP), "cutout");
            case TRAPDOOR ->
                    trapdoorBlockWithRenderType((TrapDoorBlock) wrapper.get(), wrapper.getTextureLocation(), true, "cutout");
            case PRESSURE_PLATE -> pressurePlateBlock((PressurePlateBlock) wrapper.get(), wrapper.getTextureLocation());
            case BUTTON -> buttonBlock((ButtonBlock) wrapper.get(), wrapper.getTextureLocation());
            case SIGN ->
                    signBlock((StandingSignBlock) wrapper.get(), (WallSignBlock) wrapper.getOther(), wrapper.getTextureLocation());
            case HANGING_SIGN -> hangingSignBlock(wrapper);
            case LEAVES ->
                    simpleBlock(wrapper.get(), models().withExistingParent(wrapper.getName(), new ResourceLocation("block/leaves")).texture("all", wrapper.getTextureLocation()));
        }
    }

    protected void hangingSignBlock(BlockWrapper wrapper) {
        simpleBlock(wrapper.get(), models().getBuilder(wrapper.getName()).texture("particle", wrapper.getTextureLocation()));
        simpleBlock(wrapper.getOther(), models().getBuilder(wrapper.getTypeName() + "_wall_hanging_sign").texture("particle", wrapper.getTextureLocation()));
    }

    protected void firewoodBlock(BlockWrapper wrapper) {
        ResourceLocation log = ((FirewoodBlock) wrapper.get()).getLogLocation();
        boolean flag = log.getNamespace().equals("minecraft");

        ResourceLocation inner = Objects.equals(wrapper.getTypeName(), "bamboo") ? new ResourceLocation("block/bamboo_block") : wrapper.getTextureLocation(TextureType.INNER);
        ResourceLocation outer = flag ? new ResourceLocation("block/" + log.getPath()) : wrapper.getTextureLocation(TextureType.SIDE);
        ResourceLocation top = flag ? new ResourceLocation("block/" + log.getPath() + "_top") : wrapper.getTextureLocation(TextureType.TOP);

        BlockModelBuilder count_1 = models().withExistingParent(wrapper.getName() + "_1", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_1")).texture("outer", outer).texture("inner", inner).texture("top", top);
        BlockModelBuilder count_2 = models().withExistingParent(wrapper.getName() + "_2", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_2")).texture("outer", outer).texture("inner", inner).texture("top", top);
        BlockModelBuilder count_3 = models().withExistingParent(wrapper.getName() + "_3", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_3")).texture("outer", outer).texture("inner", inner).texture("top", top);
        BlockModelBuilder count_4 = models().withExistingParent(wrapper.getName() + "_4", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_4")).texture("outer", outer).texture("inner", inner).texture("top", top);
        BlockModelBuilder count_1_h = models().withExistingParent(wrapper.getName() + "_horizontal_1", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_horizontal_1")).texture("outer", outer).texture("inner", inner).texture("top", top);
        BlockModelBuilder count_2_h = models().withExistingParent(wrapper.getName() + "_horizontal_2", new ResourceLocation(WoodEnjoyer.ID, "block/firewood_horizontal_2")).texture("outer", outer).texture("inner", inner).texture("top", top);

        getVariantBuilder(wrapper.get())
                .partialState().with(FirewoodBlock.COUNT, 1).with(FirewoodBlock.AXIS, Direction.Axis.X).addModels(ConfiguredModel.builder().modelFile(count_1_h).rotationX(90).rotationY(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 1).with(FirewoodBlock.AXIS, Direction.Axis.Y).addModels(ConfiguredModel.builder().modelFile(count_1).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 1).with(FirewoodBlock.AXIS, Direction.Axis.Z).addModels(ConfiguredModel.builder().modelFile(count_1_h).rotationX(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 2).with(FirewoodBlock.AXIS, Direction.Axis.X).addModels(ConfiguredModel.builder().modelFile(count_2_h).rotationX(90).rotationY(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 2).with(FirewoodBlock.AXIS, Direction.Axis.Y).addModels(ConfiguredModel.builder().modelFile(count_2).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 2).with(FirewoodBlock.AXIS, Direction.Axis.Z).addModels(ConfiguredModel.builder().modelFile(count_2_h).rotationX(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 3).with(FirewoodBlock.AXIS, Direction.Axis.X).addModels(ConfiguredModel.builder().modelFile(count_3).rotationX(90).rotationY(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 3).with(FirewoodBlock.AXIS, Direction.Axis.Y).addModels(ConfiguredModel.builder().modelFile(count_3).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 3).with(FirewoodBlock.AXIS, Direction.Axis.Z).addModels(ConfiguredModel.builder().modelFile(count_3).rotationX(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 4).with(FirewoodBlock.AXIS, Direction.Axis.X).addModels(ConfiguredModel.builder().modelFile(count_4).rotationX(90).rotationY(90).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 4).with(FirewoodBlock.AXIS, Direction.Axis.Y).addModels(ConfiguredModel.builder().modelFile(count_4).buildLast())
                .partialState().with(FirewoodBlock.COUNT, 4).with(FirewoodBlock.AXIS, Direction.Axis.Z).addModels(ConfiguredModel.builder().modelFile(count_4).rotationX(90).buildLast());

    }

    protected void sculptureBlock(BlockWrapper wrapper) {
        VariantBlockStateBuilder builder = getVariantBuilder(wrapper.get());

        for (WoodSculptureBlock.Model model : WoodSculptureBlock.Model.values()) {
            addSculptureModel(wrapper, builder, model);
        }
    }

    private void addSculptureModel(BlockWrapper wrapper, VariantBlockStateBuilder builder, WoodSculptureBlock.Model model) {
        ModelFile modelFile = models().withExistingParent(wrapper.getName() + "_" + model, "wood_enjoyer:block/" + model + "_sculpture").texture("all", wrapper.getTextureLocation());

        builder
                .partialState().with(WoodSculptureBlock.MODEL, model).with(WoodSculptureBlock.FACING, Direction.NORTH).addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(0).buildLast())
                .partialState().with(WoodSculptureBlock.MODEL, model).with(WoodSculptureBlock.FACING, Direction.EAST).addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(90).buildLast())
                .partialState().with(WoodSculptureBlock.MODEL, model).with(WoodSculptureBlock.FACING, Direction.SOUTH).addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(180).buildLast())
                .partialState().with(WoodSculptureBlock.MODEL, model).with(WoodSculptureBlock.FACING, Direction.WEST).addModels(ConfiguredModel.builder().modelFile(modelFile).rotationY(270).buildLast());
    }

}
