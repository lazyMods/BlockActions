package com.lazynessmind.blockactions.api;

import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRedstoneWork {

    void doWork(BlockActionTileEntity tileEntityBase, BlockState state, World world, BlockPos pos);
}
