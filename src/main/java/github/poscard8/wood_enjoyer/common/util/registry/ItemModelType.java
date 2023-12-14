package github.poscard8.wood_enjoyer.common.util.registry;

public enum ItemModelType {
    GENERATED,
    GENERATED_SMALL,
    HANDHELD,
    HANDHELD_SMALL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
