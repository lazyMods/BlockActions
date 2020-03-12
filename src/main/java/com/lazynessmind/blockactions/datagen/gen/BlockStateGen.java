package com.lazynessmind.blockactions.datagen.gen;

import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.event.BlockRegister;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

public class BlockStateGen extends BlockStateProvider {

    public String modid = BlockActions.MOD_ID;

    public BlockStateGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BlockActions.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(BlockRegister.BREAKER.get(), new ModelFile.UncheckedModelFile(new ResourceLocation(modid, "block/breaker")));
        horizontalBlock(BlockRegister.PLACER.get(), new ModelFile.UncheckedModelFile(new ResourceLocation(modid, "block/placer")));
        horizontalBlock(BlockRegister.HIT.get(), new ModelFile.UncheckedModelFile(new ResourceLocation(modid, "block/hit")));
        horizontalBlock(BlockRegister.PLANTER.get(), new ModelFile.UncheckedModelFile(new ResourceLocation(modid, "block/planter")));
    }
}
