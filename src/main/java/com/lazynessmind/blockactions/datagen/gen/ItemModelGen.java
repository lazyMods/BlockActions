package com.lazynessmind.blockactions.datagen.gen;

import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.event.BlockRegister;
import com.lazynessmind.blockactions.event.ItemRegister;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import org.apache.commons.lang3.StringUtils;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BlockActions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        parentBlock(BlockRegister.BREAKER.get());
        parentBlock(BlockRegister.PLACER.get());
        parentBlock(BlockRegister.HIT.get());
        parentBlock(BlockRegister.PLANTER.get());
        simpleItem(ItemRegister.SPEED_UPGRADE.get());
        simpleItem(ItemRegister.ATTACK_UPGRADE.get());
        simpleItem(ItemRegister.ATTACK_ADULTS_UPGRADE.get());
        simpleItem(ItemRegister.ENERGY_MODE_UPGRADE.get());
        simpleItem(ItemRegister.SILK_TOUCH_UPGRADE.get());
    }

    private void simpleItem(Item item) {
        final String path = item.getRegistryName().getPath();
        this.singleTexture(path, mcLoc(StringUtils.joinWith("/", ITEM_FOLDER, "generated")), "layer0", modLoc(StringUtils.joinWith("/", ITEM_FOLDER, path)));
    }

    public void parentBlock(Block block) {
        final String path = block.getRegistryName().getPath();
        this.getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc(StringUtils.joinWith("/", BLOCK_FOLDER, path))));
    }

    @Override
    public String getName() {
        return "BlockAction: Item Model Generator";
    }
}
