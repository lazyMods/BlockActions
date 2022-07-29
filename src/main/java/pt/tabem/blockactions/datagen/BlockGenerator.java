package pt.tabem.blockactions.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pt.tabem.blockactions.BlockActions;
import pt.tabem.blockactions.ModBlocks;

import java.util.Objects;

public class BlockGenerator extends BlockStateProvider {

    public BlockGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BlockActions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.blockAction(ModBlocks.PLACER.get());
        this.blockAction(ModBlocks.BREAKER.get());
        this.blockAction(ModBlocks.ITEM_USE.get());
    }

    private void blockAction(Block block) {
        this.horizontalBlock(
                block,
                this.modLoc("block/side"),
                this.modLoc("block/" + Objects.requireNonNull(block.getRegistryName()).getPath()),
                this.modLoc("block/top"));
    }
}
