package github.poscard8.wood_enjoyer.common.blockentity;

import github.poscard8.wood_enjoyer.common.util.BlockUtils;
import github.poscard8.wood_enjoyer.init.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Objects;

public class StumpBlockEntity extends BlockEntity {

    public static final List<ItemLike> LOGS = BlockUtils.LOGS;
    public static final List<ItemLike> FIREWOODS = BlockUtils.FIREWOODS;

    private int logType;
    private int logCount;
    private float rotation;

    public StumpBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntities.STUMP.get(), position, state);
        this.logType = 0;
        this.logCount = 0;
        this.rotation = 0;
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        logType = compoundTag.getInt("Log Type");
        logCount = compoundTag.getInt("Log Count");
        rotation = compoundTag.getFloat("Rotation");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.putInt("Log Type", logType);
        compoundTag.putInt("Log Count", logCount);
        compoundTag.putFloat("Rotation", rotation);
        super.saveAdditional(compoundTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("Log Type", logType);
        compoundTag.putInt("Log Count", logCount);
        compoundTag.putFloat("Rotation", rotation);
        return compoundTag;
    }

    public int getLogId() {
        return this.logType;
    }

    public Item getLog() {
        return LOGS.get(getLogId()).asItem();
    }

    public Item getFirewood() {
        return FIREWOODS.get(getLogId()).asItem();
    }

    public void setLog(int index) {
        setLog(LOGS.get(index).asItem());
    }

    public void setLog(Item item) {
        int index = -1;

        for (ItemLike itemLike : LOGS) {
            ++index;
            if (Objects.equals(itemLike.asItem(), item)) {
                this.logType = index;
            }
        }
        this.logCount = 1;
    }

    public int getLogCount() {
        return logCount;
    }

    public void setLogCount(int count) {
        this.logCount = count;
    }

    public void chop() {
        ++logCount;
    }

    public ItemStack getItem() {
        ItemStack stack;

        switch (logCount) {
            case 0, 1 -> stack = this.getLog().getDefaultInstance();
            default -> stack = this.getFirewood().getDefaultInstance();
        }
        return stack;
    }

    public ItemStack getLoot() {
        ItemStack stack;

        switch (logCount) {
            case 0 -> stack = ItemStack.EMPTY;
            default -> stack = this.getLog().getDefaultInstance();
            case 4 -> stack = new ItemStack(this.getFirewood(), logCount);
        }
        return stack;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(Player player) {
        double zDiff = player.getZ() - this.getBlockPos().getZ() - 0.5F;
        double xDiff = player.getX() - this.getBlockPos().getX() - 0.5F;

        this.rotation = -(float) Math.atan(zDiff / xDiff);
    }
}
