package github.poscard8.wood_enjoyer.init.datagen;

import github.poscard8.wood_enjoyer.common.util.ItemUtils;
import github.poscard8.wood_enjoyer.common.util.ModTags;
import github.poscard8.wood_enjoyer.common.util.registry.BlockModelType;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;


public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ModBlocks.ALL.forEach(data -> readBlockWrappers(output, data));
        addManualRecipes(output);
    }

    protected void readBlockWrappers(RecipeOutput output, BlockWrapper wrapper) {
        if (wrapper.getIngredient() == null || !wrapper.hasRecipes) {
            return;
        }

        BlockItem self = wrapper.getItem();
        Item item = wrapper.getIngredient();

        switch (wrapper.getModelType().recipe) {
            default -> misc(output, self, item, wrapper.getModelType());
            case "" -> {
            }
            case "wood" -> woodFromLogs(output, self, item);
            case "cut_planks" -> cutPlanks(output, self, item);
            case "hanging_sign" -> hangingSign(output, self, item);

        }

    }

    protected void misc(RecipeOutput output, Item self, Item item, BlockModelType modelType) {
        Ingredient ingredient = Ingredient.of(item);

        switch (modelType.recipe) {
            default -> {
            }
            case "slab" ->
                    slabBuilder(RecipeCategory.BUILDING_BLOCKS, self, ingredient).group("wooden_slab").unlockedBy(getHasName(item), has(item)).save(output);
            case "stairs" ->
                    stairBuilder(self, ingredient).group("wooden_stairs").unlockedBy(getHasName(item), has(item)).save(output);
            case "fence" ->
                    fenceBuilder(self, ingredient).group("wooden_fence").unlockedBy(getHasName(item), has(item)).save(output);
            case "fence_gate" ->
                    fenceGateBuilder(self, ingredient).group("wooden_fence_gate").unlockedBy(getHasName(item), has(item)).save(output);
            case "door" ->
                    doorBuilder(self, ingredient).group("wooden_door").unlockedBy(getHasName(item), has(item)).save(output);
            case "trapdoor" ->
                    trapdoorBuilder(self, ingredient).group("wooden_trapdoor").unlockedBy(getHasName(item), has(item)).save(output);
            case "pressure_plate" ->
                    pressurePlateBuilder(RecipeCategory.BUILDING_BLOCKS, self, ingredient).group("wooden_pressure_plate").unlockedBy(getHasName(item), has(item)).save(output);
            case "button" ->
                    buttonBuilder(self, ingredient).group("wooden_button").unlockedBy(getHasName(item), has(item)).save(output);
            case "sign" ->
                    signBuilder(self, ingredient).group("wooden_sign").unlockedBy(getHasName(item), has(item)).save(output);
        }

    }

    protected void cutPlanks(RecipeOutput output, Item self, Item ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, self, 4)
                .define('#', ingredient)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .group("cut_planks")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(output);
    }

    protected void addManualRecipes(RecipeOutput output) {
        planksFromLogs(output, ItemUtils.fromLocation("walnut_planks"), ModTags.Items.WALNUT_LOGS, 4);
        planksFromLogs(output, ItemUtils.fromLocation("chestnut_planks"), ModTags.Items.CHESTNUT_LOGS, 5);
        planksFromLogs(output, ItemUtils.fromLocation("lunar_planks"), ModTags.Items.LUNAR_LOGS, 7);
    }


}
