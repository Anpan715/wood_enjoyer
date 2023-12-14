package github.poscard8.wood_enjoyer.common.util.registry;

public enum BlockModelType {
    LOG("", "block", "log", 1.5F),
    WOOD("wood", 1.5F),
    FIREWOOD("firewood", "firewood", "firewood", 10),
    PLANKS("planks", 1.5F),
    CUT_PLANKS("cut_planks", 1.5F),
    SCULPTURE("", "", "sculpture", 1.5F),
    STAIRS("stairs", 1.5F),
    SLAB("slab", "block", "slab", 0.75F),
    FENCE("fence", "fence", "dropSelf", 1.5F),
    FENCE_GATE("fence_gate", 1.5F),
    DOOR("door", "generated", "door", 1),
    TRAPDOOR("trapdoor", "trapdoor", "dropSelf", 1.5F),
    PRESSURE_PLATE("pressure_plate", 1.5F),
    BUTTON("button", "button", "dropSelf", 0.5F),
    SIGN("sign", "generated", "dropSelf", 1),
    HANGING_SIGN("hanging_sign", "generated", "dropSelf", 1),
    LEAVES("", "block", "leaves", 0),
    SAPLING("", "generated", "dropSelf", 0.5F),
    MISC("", 0);

    public final String recipe;
    public final String itemModel;
    public final String loot;
    public final float burnTime;

    BlockModelType(String type, float burnTime) {
        this(type, "block", "dropSelf", burnTime);
    }

    BlockModelType(String recipe, String itemModel, String loot, float burnTime) {
        this.recipe = recipe;
        this.itemModel = itemModel;
        this.loot = loot;
        this.burnTime = burnTime;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
