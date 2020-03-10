package com.lazynessmind.blockactions.datagen.gen;

import com.lazynessmind.blockactions.BlockActions;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class BlockModelGen extends BlockModelProvider {

    public BlockModelGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BlockActions.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        orientable("breaker", new ResourceLocation(modid, "block/side"), new ResourceLocation(modid, "block/breaker"), new ResourceLocation(modid, "block/side"));
        orientable("placer", new ResourceLocation(modid, "block/side"), new ResourceLocation(modid, "block/placer"), new ResourceLocation(modid, "block/side"));
        orientable("hit", new ResourceLocation(modid, "block/side"), new ResourceLocation(modid, "block/hit"), new ResourceLocation(modid, "block/side"));
        orientable("planter", new ResourceLocation(modid, "block/side"), new ResourceLocation(modid, "block/planter"), new ResourceLocation(modid, "block/side"));
    }

    @Override
    public String getName() {
        return "BlockAction: Block Model Generator";
    }
}
