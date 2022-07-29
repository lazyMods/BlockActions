package pt.tabem.blockactions;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {

    private final static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlockActions.MOD_ID);

    public static RegistryObject<Item> CORE = ITEMS.register("core", () -> new Item(new Item.Properties().tab(BlockActions.TAB)));

    public static RegistryObject<BlockItem> BREAKER = registerBlockItem("breaker", ModBlocks.BREAKER);
    public static RegistryObject<BlockItem> PLACER = registerBlockItem("placer", ModBlocks.PLACER);
    public static RegistryObject<BlockItem> ITEM_USER = registerBlockItem("item_user", ModBlocks.ITEM_USE);

    private static RegistryObject<BlockItem> registerBlockItem(String regName, Supplier<? extends Block> block) {
        return ITEMS.register(regName, () -> new BlockItem(block.get(), new Item.Properties().tab(BlockActions.TAB)));
    }

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
