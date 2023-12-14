package github.poscard8.wood_enjoyer.common.entity;

import github.poscard8.wood_enjoyer.common.util.registry.BlockWrapper;
import github.poscard8.wood_enjoyer.init.registry.ModBlocks;
import github.poscard8.wood_enjoyer.init.registry.ModEntities;
import github.poscard8.wood_enjoyer.init.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;

public class ModdedBoat extends Boat {

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModdedBoat.class, EntityDataSerializers.INT);

    public ModdedBoat(EntityType<? extends ModdedBoat> type, Level level) {
        super(type, level);
    }

    public ModdedBoat(Level level, double x, double y, double z) {
        this(ModEntities.MODDED_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setModdedVariant(Type.byId(compoundTag.getInt("ModdedVariant")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ModdedVariant", this.getModdedVariant().ordinal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE, 0);
    }

    public void setModdedVariant(Type type) {
        this.entityData.set(DATA_ID_TYPE, type.ordinal());
    }

    public ModdedBoat.Type getModdedVariant() {
        return ModdedBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
    }

    @Override
    public Item getDropItem() {
        return this.getModdedVariant() == Type.WALNUT ? ModItems.WALNUT_BOAT.get() : ModItems.CHESTNUT_BOAT.get();
    }

    public enum Type implements StringRepresentable {
        WALNUT(ModBlocks.WALNUT_PLANKS),
        CHESTNUT(ModBlocks.CHESTNUT_PLANKS);

        private final BlockWrapper planks;

        Type(BlockWrapper wrapper) {
            this.planks = wrapper;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        @Override
        public String getSerializedName() {
            return this.toString();
        }

        public Block getPlanks() {
            return planks.get();
        }

        public static Type byId(int id) {
            return Arrays.stream(Type.values()).filter(type -> type.ordinal() == id).toList().get(0);
        }
    }
}
