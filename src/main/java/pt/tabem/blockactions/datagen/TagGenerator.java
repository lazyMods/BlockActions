package pt.tabem.blockactions.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import pt.tabem.blockactions.BlockActions;
import pt.tabem.blockactions.ModBlockEntities;
import pt.tabem.blockactions.ModBlocks;

public class TagGenerator extends BlockTagsProvider {

    public TagGenerator(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, BlockActions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.ITEM_USE.get(),
                ModBlocks.PLACER.get(),
                ModBlocks.BREAKER.get()
        );
    }
}
