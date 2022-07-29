package pt.tabem.blockactions;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pt.tabem.blockactions.world.block.entity.BreakerTileEntity;
import pt.tabem.blockactions.world.block.entity.PlacerTileEntity;
import pt.tabem.blockactions.world.block.entity.ItemUserTileEntity;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BlockActions.MOD_ID);

    public static RegistryObject<BlockEntityType<PlacerTileEntity>> PLACER_TILE = TILES.register("placer", () -> BlockEntityType.Builder.of(PlacerTileEntity::new, ModBlocks.PLACER.get()).build(null));
    public static RegistryObject<BlockEntityType<BreakerTileEntity>> BREAKER_TILE = TILES.register("breaker", () -> BlockEntityType.Builder.of(BreakerTileEntity::new, ModBlocks.BREAKER.get()).build(null));
    public static RegistryObject<BlockEntityType<ItemUserTileEntity>> PLAYER_SIMULATOR = TILES.register("item_user", () -> BlockEntityType.Builder.of(ItemUserTileEntity::new, ModBlocks.ITEM_USE.get()).build(null));

    public static void init() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
