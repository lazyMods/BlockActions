package com.lazynessmind.blockactions;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Configs {

    private static ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue DEFAULT_COOLDOWN;
    public static ForgeConfigSpec.IntValue MAX_UPGRADE_COUNT;

    public static ForgeConfigSpec.BooleanValue BREAK_TE;

    public static ForgeConfigSpec.DoubleValue HIT_DAMAGE;

    public static ForgeConfigSpec.IntValue ENERGY_PER_WORK;

    static {
        COMMON_BUILDER.comment("MOD: Block Actions");

        COMMON_BUILDER.push("Common configs");
        DEFAULT_COOLDOWN = COMMON_BUILDER.comment("Cool Down time in Ticks (20 ticks = 1 second").defineInRange("defaultCoolDown", 60, 20, Integer.MAX_VALUE);
        MAX_UPGRADE_COUNT = COMMON_BUILDER.comment("How much upgrades can the block hold").defineInRange("maxUpgrades", 5, 1, Integer.MAX_VALUE);
        ENERGY_PER_WORK = COMMON_BUILDER.comment("Amount of energy needed to work.").defineInRange("energy_per_work", 100, 100, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Breaker Configs");
        BREAK_TE = COMMON_BUILDER.comment("Destroy Tile Entities (Chests, Furnaces...)").define("breakTileEntities", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Hit Configs");
        HIT_DAMAGE = COMMON_BUILDER.comment("Amount of damage that can apply to the entity.").defineInRange("hitDamageValue", 1D, 1D, 20D);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void load(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }

}
