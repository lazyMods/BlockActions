package com.lazynessmind.blockactions.utils;

import com.lazynessmind.blockactions.api.IRedstoneWork;
import com.lazynessmind.blockactions.base.BlockActionBase;
import com.lazynessmind.blockactions.event.BlockRegister;

import java.util.HashMap;

public class RedstoneWorkRegistry {

    public static HashMap<BlockActionBase, IRedstoneWork> REDSTONE_WORKS = new HashMap<>();

    public static void initWorks() {
        BlockRegister.BLOCKS.getEntries().forEach((blockRegistryObject) -> {
            if (blockRegistryObject.get() instanceof BlockActionBase) {
                REDSTONE_WORKS.put((BlockActionBase)blockRegistryObject.get(), ((te, state, world, pos) -> te.doWork(world, pos, false)));
            }
        });
    }
}
