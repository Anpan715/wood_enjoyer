package github.poscard8.wood_enjoyer.common.util.registry;

public enum TextureType {
    DEFAULT(""),
    SIDE("_side"),
    BOTTOM("_bottom"),
    TOP("_top"),
    INNER("_inner"),
    OUTER("_outer");

    TextureType(String name) {
        this.name = name;
    }

    private final String name;

    public String toString() {
        return this.name;
    }
}
