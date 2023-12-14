package github.poscard8.wood_enjoyer.common.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("ALL")
@Mixin(ItemCombinerMenu.class)
public interface ItemCombinerMenuInvoker {

    @Accessor
    Container getInputSlots();

    @Accessor
    ResultContainer getResultSlots();

}
