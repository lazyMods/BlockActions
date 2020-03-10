package com.lazynessmind.test;

import com.lazynessmind.blockactions.base.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockEnergy extends ModBlock {

    public BlockEnergy() {
        super("blockenergy", Block.Properties.from(Blocks.IRON_BLOCK), ItemGroup.REDSTONE);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityEnergy();
    }
}


