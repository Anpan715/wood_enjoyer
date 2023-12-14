package github.poscard8.wood_enjoyer.common.item;

import github.poscard8.wood_enjoyer.common.config.WoodEnjoyerConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandleItem extends Item {

    public static final List<HandleItem> ALL = new ArrayList<>();
    public static final Item.Properties PROPERTIES = new Properties().stacksTo(16);

    private final MutableComponent tooltip;

    public HandleItem(Properties property, String text) {
        super(property);
        this.tooltip = Component.translatable(text).withStyle(ChatFormatting.DARK_GREEN);
        ALL.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        components.add(CommonComponents.EMPTY);
        components.add(Component.translatable("tooltip.wood_enjoyer.handle_applies_to").withStyle(ChatFormatting.GRAY));
        components.add(CommonComponents.space().append(Component.translatable("tooltip.wood_enjoyer.handle_applicable").withStyle(ChatFormatting.BLUE)));
    }

    public Component getTooltip() {
        return this.tooltip;
    }

    public int getIdentifier() {
        for (@Nullable HandleItem handle : ALL) {
            if (Objects.equals(handle, this)) {
                return ALL.indexOf(handle) + 1;
            }
        }
        return 0;
    }

    public static boolean isApplicable(ItemStack stack) {
        return stack.getItem() instanceof TieredItem || stack.getItem() instanceof BowItem || stack.getItem() instanceof FishingRodItem;
    }

    public static ItemStack apply(ItemStack stack, HandleItem handle) {
        ItemStack newStack = stack.copy();
        int damage = stack.getOrCreateTag().getInt("Handle") == 2 ? Math.round((float) (stack.getDamageValue() / WoodEnjoyerConfig.LUNAR_HANDLE_BUFF.get())) : stack.getDamageValue();
        newStack.getOrCreateTag().putInt("Handle", handle.getIdentifier());
        newStack.setDamageValue(damage);
        return newStack;
    }

    public static ItemStack clear(ItemStack stack) {
        ItemStack newStack = stack.copy();
        int damage = stack.getOrCreateTag().getInt("Handle") == 2 ? Math.round((float) (stack.getDamageValue() / WoodEnjoyerConfig.LUNAR_HANDLE_BUFF.get())) : stack.getDamageValue();
        newStack.getOrCreateTag().remove("Handle");
        newStack.setDamageValue(damage);
        return newStack;
    }

    public static int getStableBarWidth(ItemStack stack) {
        return Math.round(13.0F - (float) stack.getDamageValue() * 13.0F / (float) stack.getMaxDamage());
    }

    public static int getStableBarColor(ItemStack stack) {
        float stackMaxDamage = stack.getMaxDamage();
        float f = Math.max(0.0F, (stackMaxDamage - (float) stack.getDamageValue()) / stackMaxDamage);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

}
