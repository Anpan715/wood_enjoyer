package github.poscard8.wood_enjoyer.common.util;

import github.poscard8.wood_enjoyer.init.registry.ModSounds;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.List;

public enum ModdedWoodType {
    WALNUT(3.0F, MapColor.PODZOL, MapColor.COLOR_BROWN, SoundType.WOOD, true, false),
    CHESTNUT(4.0F, MapColor.RAW_IRON, MapColor.PODZOL, ModSounds.Types.CHESTNUT_WOOD, true, true),
    LUNAR(6.5F, MapColor.DIAMOND, MapColor.COLOR_GRAY, ModSounds.Types.LUNAR_WOOD, false, true);

    public final float hardness;
    public final boolean flammable;
    public final boolean needsCorrectTool;
    public final MapColor color;
    public final MapColor sideColor;
    public final SoundType sound;

    public final List<WoodType> RECORDS = List.of(
            WoodType.register(new WoodType("wood_enjoyer:walnut", BlockSetType.OAK)),
            WoodType.register(new WoodType("wood_enjoyer:chestnut", BlockSetType.JUNGLE, ModSounds.Types.CHESTNUT_WOOD, SoundType.CHERRY_WOOD_HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN)),
            WoodType.register(new WoodType("wood_enjoyer:lunar", BlockSetType.WARPED, ModSounds.Types.LUNAR_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN))
    );

    ModdedWoodType(float hardness, MapColor color, MapColor sideColor, SoundType sound, boolean flammable, boolean needsCorrectTool) {
        this.hardness = hardness;
        this.color = color;
        this.sideColor = sideColor;
        this.sound = sound;
        this.flammable = flammable;
        this.needsCorrectTool = needsCorrectTool;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public BlockBehaviour.Properties getProperties() {
        BlockBehaviour.Properties base = BlockBehaviour.Properties.of()
                .strength(this.hardness)
                .sound(this.sound)
                .instrument(NoteBlockInstrument.BASS)
                .mapColor((state) -> {
                    try {
                        return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? this.color : this.sideColor;
                    } catch (IllegalArgumentException exception) {
                        return this.color;
                    }
                });

        if (this.needsCorrectTool) {
            base.requiresCorrectToolForDrops();
        }
        if (this.flammable) {
            base.ignitedByLava();
        }

        return base;
    }

    public BlockBehaviour.Properties withNormalStrength() {
        return this.getProperties().strength(2);
    }

    public WoodType getRecord() {
        return RECORDS.get(this.ordinal());
    }

    public static ModdedWoodType byName(String name) {

        for (ModdedWoodType woodType : ModdedWoodType.values()) {
            if (woodType.toString().equals(name)) {
                return woodType;
            }
        }
        return null;
    }

}
