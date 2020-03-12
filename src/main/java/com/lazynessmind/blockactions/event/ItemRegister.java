package com.lazynessmind.blockactions.event;

import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {

    public static DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, BlockActions.MOD_ID);

    public static RegistryObject<SpeedUpgradeItem> SPEED_UPGRADE = ITEMS.register("speed_upgrade", SpeedUpgradeItem::new);
    public static RegistryObject<AttackUpgradeItem> ATTACK_UPGRADE = ITEMS.register("attack_upgrade", AttackUpgradeItem::new);
    public static RegistryObject<AttackAdultMobsUpgrade> ATTACK_ADULTS_UPGRADE = ITEMS.register("attack_adults_upgrade", AttackAdultMobsUpgrade::new);
    public static RegistryObject<EnergyModeUpgrade> ENERGY_MODE_UPGRADE = ITEMS.register("energy_mode_upgrade", EnergyModeUpgrade::new);
    public static RegistryObject<SilkTouchUpgrade> SILK_TOUCH_UPGRADE = ITEMS.register("silk_touch_upgrade", SilkTouchUpgrade::new);
}
