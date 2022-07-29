package pt.tabem.blockactions.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.StringUtils;
import pt.tabem.blockactions.BlockActions;
import pt.tabem.blockactions.ModBlocks;
import pt.tabem.blockactions.ModItems;

public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BlockActions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        parentBlock(ModBlocks.BREAKER.get());
        parentBlock(ModBlocks.PLACER.get());
        parentBlock(ModBlocks.ITEM_USE.get());
        simpleItem(ModItems.CORE.get());
    }

    private void simpleItem(Item item) {
        final String path = item.getRegistryName().getPath();
        this.singleTexture(path, mcLoc(StringUtils.joinWith("/", ITEM_FOLDER, "generated")), "layer0", modLoc(StringUtils.joinWith("/", ITEM_FOLDER, path)));
    }

    public void parentBlock(Block block) {
        final String path = block.getRegistryName().getPath();
        this.getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc(StringUtils.joinWith("/", BLOCK_FOLDER, path))));
    }
}
