package github.poscard8.wood_enjoyer.init.registry;

import github.poscard8.wood_enjoyer.WoodEnjoyer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModSounds {

    public static final DeferredRegister<SoundEvent> ALL = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WoodEnjoyer.ID);

    public static final RegistryObject<SoundEvent> LUNAR_WOOD_BREAK = register("block.lunar_wood.break");
    public static final RegistryObject<SoundEvent> LUNAR_WOOD_FALL = register("block.lunar_wood.fall");
    public static final RegistryObject<SoundEvent> LUNAR_WOOD_HIT = register("block.lunar_wood.hit");
    public static final RegistryObject<SoundEvent> LUNAR_WOOD_PLACE = register("block.lunar_wood.place");
    public static final RegistryObject<SoundEvent> LUNAR_WOOD_STEP = register("block.lunar_wood.step");

    public static final RegistryObject<SoundEvent> WOOD_CARVE = register("misc.wood_carve");
    public static final RegistryObject<SoundEvent> WOOD_CHOP = register("misc.wood_chop");

    protected static RegistryObject<SoundEvent> register(String name) {
        return ALL.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WoodEnjoyer.ID, name)));
    }

    public static final class Types {

        public static final SoundType CHESTNUT_WOOD = new ForgeSoundType(1.25F, 1.4F, () -> SoundEvents.WOOD_BREAK, () -> SoundEvents.WOOD_STEP, () -> SoundEvents.WOOD_PLACE, () -> SoundEvents.WOOD_HIT, () -> SoundEvents.WOOD_FALL);
        public static final SoundType LUNAR_WOOD = new ForgeSoundType(1.25F, 1, LUNAR_WOOD_BREAK, LUNAR_WOOD_STEP, LUNAR_WOOD_PLACE, LUNAR_WOOD_HIT, LUNAR_WOOD_FALL);

    }


}
