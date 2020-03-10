package com.lazynessmind.blockactions.proxy;

import com.lazynessmind.blockactions.event.BlockRegister;
import com.lazynessmind.blockactions.event.ItemRegister;
import com.lazynessmind.blockactions.event.TileEntityRegister;
import com.lazynessmind.blockactions.net.NetHandler;
import com.lazynessmind.blockactions.utils.RedstoneWorkRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {

    public void init() {
        this.addToBus();
    }

    public void addToBus() {
        TileEntityRegister.TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockRegister.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemRegister.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
    }

    public void setupCommon(FMLCommonSetupEvent event) {
        NetHandler.registerCommon();
        RedstoneWorkRegistry.initWorks();
    }
}
