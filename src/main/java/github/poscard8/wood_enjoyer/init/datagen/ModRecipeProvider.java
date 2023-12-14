package github.poscard8.wood_enjoyer.init.datagen;

import github.poscard8.wood_enjoyer.common.util.ModTags;
import github.poscard8.wood_enjoyer.common.util.ModUtils;
import github.poscard8.wood_enjoyer.common.util.registry.BlockModelType;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ModBlocks.ALL.forEach(data -> readBlockWrappers(consumer, data));
        addManualRecipes(consumer);
    }

    protected void readBlockWrappers(Consumer<FinishedRecipe> consumer, BlockWrapper wrapper) {
        if (wrapper.getIngredient() == null || !wrapper.hasRecipes) {
            return;
        }

        BlockItem self = wrapper.getItem();
        Item item = wrapper.getIngredient();

        switch (wrapper.getModelType().recipe) {
            default -> misc(consumer, self, item, wrapper.getModelType());
            case "" -> {
            }
            case "wood" -> woodFromLogs(consumer, self, item);
            case "cut_planks" -> cutPlanks(consumer, self, item);
            case "hanging_sign" -> hangingSign(consumer, self, item);

        }

    }

    protected void misc(Consumer<FinishedRecipe> consumer, Item self, Item item, BlockModelType modelType) {
        Ingredient ingredient = Ingredient.of(item);

        switch (modelType.recipe) {
            default -> {
            }
            case "slab" ->
                    slabBuilder(RecipeCategory.BUILDING_BLOCKS, self, ingredient).group("wooden_slab").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "stairs" ->
                    stairBuilder(self, ingredient).group("wooden_stairs").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "fence" ->
                    fenceBuilder(self, ingredient).group("wooden_fence").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "fence_gate" ->
                    fenceGateBuilder(self, ingredient).group("wooden_fence_gate").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "door" ->
                    doorBuilder(self, ingredient).group("wooden_door").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "trapdoor" ->
                    trapdoorBuilder(self, ingredient).group("wooden_trapdoor").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "pressure_plate" ->
                    pressurePlateBuilder(RecipeCategory.BUILDING_BLOCKS, self, ingredient).group("wooden_pressure_plate").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "button" ->
                    buttonBuilder(self, ingredient).group("wooden_button").unlockedBy(getHasName(item), has(item)).save(consumer);
            case "sign" ->
                    signBuilder(self, ingredient).group("wooden_sign").unlockedBy(getHasName(item), has(item)).save(consumer);
        }

    }

    protected void cutPlanks(Consumer<FinishedRecipe> consumer, Item self, Item ingredient) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, self, 4)
                .define('#', ingredient)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .group("cut_planks")
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(consumer);
    }

    protected void addManualRecipes(Consumer<FinishedRecipe> consumer) {
        planksFromLogs(consumer, ModUtils.itemFromLocation("walnut_planks"), ModTags.Items.WALNUT_LOGS, 4);
        planksFromLogs(consumer, ModUtils.itemFromLocation("chestnut_planks"), ModTags.Items.CHESTNUT_LOGS, 5);
        planksFromLogs(consumer, ModUtils.itemFromLocation("lunar_planks"), ModTags.Items.LUNAR_LOGS, 7);
    }


}
