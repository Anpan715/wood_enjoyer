package github.poscard8.wood_enjoyer.init.datagen.sub;

import com.mojang.datafixers.util.Pair;
import github.poscard8.wood_enjoyer.common.block.FirewoodBlock;
import github.poscard8.wood_enjoyer.common.block.ModdedLogBlock;
import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModEnchantments;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootSubProvider extends BlockLootSubProvider {

    protected static final LootItemCondition.Builder HAS_MILLING = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(ModEnchantments.MILLING.get(), MinMaxBounds.Ints.atLeast(1))));
    protected static final LootItemCondition.Builder HAS_FORTUNE = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.BLOCK_FORTUNE, MinMaxBounds.Ints.atLeast(1))));

    public ModBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    protected void generate() {
        ModBlocks.ALL.forEach(this::readBlockWrappers);
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.ALL.stream().filter(wrapper -> wrapper.hasLootTables).map(BlockWrapper::get).collect(Collectors.toSet());
    }

    protected void readBlockWrappers(BlockWrapper wrapper) {
        if (!wrapper.hasLootTables) {
            return;
        }

        switch (wrapper.getModelType().loot) {
            default -> dropSelf(wrapper.get());
            case "log" -> moddedLog(wrapper);
            case "firewood" -> firewood(wrapper);
            case "sculpture" -> this.add(wrapper.get(), createSingleItemTable(wrapper.getItem()));
            case "slab" -> this.add(wrapper.get(), createSlabItemTable(wrapper.get()));
            case "door" -> this.add(wrapper.get(), createDoorTable(wrapper.get()));
            case "leaves" -> {
            }
        }
    }

    protected void firewood(BlockWrapper wrapper) {
        this.add(wrapper.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(wrapper, LootItem.lootTableItem(wrapper)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(wrapper.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FirewoodBlock.COUNT, 2))))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(wrapper.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FirewoodBlock.COUNT, 3))))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(wrapper.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FirewoodBlock.COUNT, 4))))))));
    }

    protected void moddedLog(BlockWrapper wrapper) {
        this.add(wrapper.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(wrapper, LootItem.lootTableItem(getPlanks(wrapper))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(getPlankCount(wrapper) - 2)).when(HAS_FORTUNE))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(getPlankCount(wrapper))).when(HAS_FORTUNE.invert()))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(wrapper.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModdedLogBlock.NATURAL, true))))).when(HAS_MILLING))
                .add(this.applyExplosionDecay(wrapper, LootItem.lootTableItem(wrapper)
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(wrapper.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModdedLogBlock.NATURAL, true))))).when(HAS_MILLING.invert()))));
    }

    protected static Item getPlanks(BlockWrapper logWrapper) {

        Map<Pair<BlockWrapper, BlockWrapper>, BlockWrapper> map = Map.of(
                Pair.of(ModBlocks.WALNUT_LOG, ModBlocks.STRIPPED_WALNUT_LOG), ModBlocks.WALNUT_PLANKS,
                Pair.of(ModBlocks.CHESTNUT_LOG, ModBlocks.STRIPPED_CHESTNUT_LOG), ModBlocks.CHESTNUT_PLANKS,
                Pair.of(ModBlocks.LUNAR_LOG, ModBlocks.STRIPPED_LUNAR_LOG), ModBlocks.LUNAR_PLANKS
        );

        for (Pair<BlockWrapper, BlockWrapper> pair : map.keySet()) {
            if (pair.getFirst() == logWrapper || pair.getSecond() == logWrapper) {
                return map.get(pair).asItem();
            }
        }
        return Items.AIR;
    }

    protected static int getPlankCount(BlockWrapper logWrapper) {

        Map<Pair<BlockWrapper, BlockWrapper>, Integer> map = Map.of(
                Pair.of(ModBlocks.WALNUT_LOG, ModBlocks.STRIPPED_WALNUT_LOG), 6,
                Pair.of(ModBlocks.CHESTNUT_LOG, ModBlocks.STRIPPED_CHESTNUT_LOG), 7,
                Pair.of(ModBlocks.LUNAR_LOG, ModBlocks.STRIPPED_LUNAR_LOG), 9
        );
        for (Pair<BlockWrapper, BlockWrapper> pair : map.keySet()) {
            if (pair.getFirst() == logWrapper || pair.getSecond() == logWrapper) {
                return map.get(pair);
            }
        }
        return 0;
    }

}
