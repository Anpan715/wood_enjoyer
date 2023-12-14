package github.poscard8.wood_enjoyer.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WoodEnjoyerConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue WARPED_HANDLE_BUFF;
    public static final ForgeConfigSpec.DoubleValue LUNAR_HANDLE_BUFF;
    public static final ForgeConfigSpec.BooleanValue CHISEL_DISPENSE_BEHAVIOR;

    static {

        BUILDER.push("Configs for Wood Enjoyer");


        WARPED_HANDLE_BUFF = BUILDER
                .comment("Enchantment table buffs for tools with warped handle")
                .comment("With 15 bookshelves, tool with warped handle will have the max enchantment cost 30 + x")
                .comment("Higher costs lead to better enchantments")
                .defineInRange("warpedHandleBuff", 3, 0, 20);

        LUNAR_HANDLE_BUFF = BUILDER
                .comment("Durability buff that comes with lunar handle")
                .comment("If you want the modifier to add %50 durability, enter 1.5")
                .defineInRange("lunarHandleBuff", 1.175, 1, 5);

        CHISEL_DISPENSE_BEHAVIOR = BUILDER
                .comment("Chisel items put in dispenser being able to carve the blocks next to the dispenser")
                .define("chiselDispenseBehavior", true);

        BUILDER.pop();
        SPEC = BUILDER.build();

    }

}
