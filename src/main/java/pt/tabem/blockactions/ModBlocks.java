package pt.tabem.blockactions;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pt.tabem.blockactions.world.block.BreakerBlock;
import pt.tabem.blockactions.world.block.ItemUserBlock;
import pt.tabem.blockactions.world.block.PlacerBlock;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlockActions.MOD_ID);

    public static RegistryObject<BreakerBlock> BREAKER = BLOCKS.register("breaker", BreakerBlock::new);
    public static RegistryObject<PlacerBlock> PLACER = BLOCKS.register("placer", PlacerBlock::new);
    public static RegistryObject<ItemUserBlock> ITEM_USE = BLOCKS.register("item_user", ItemUserBlock::new);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
