package pt.tabem.blockactions;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nonnull;

@Mod(BlockActions.MOD_ID)
public class BlockActions {

    public static final String MOD_ID = "blockactions";

    public static final CreativeModeTab TAB = new CreativeModeTab("blockactions.tab") {
        @Override
        @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BREAKER.get());
        }
    };

    public BlockActions() {
        ModBlocks.init();
        ModBlockEntities.init();
        ModItems.init();
    }
}

