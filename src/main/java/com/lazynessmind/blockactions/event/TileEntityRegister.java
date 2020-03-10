package com.lazynessmind.blockactions.event;

import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.actions.breakaction.BreakerTileEntity;
import com.lazynessmind.blockactions.actions.hitaction.HitTileEntity;
import com.lazynessmind.blockactions.actions.placeaction.PlacerTileEntity;
import com.lazynessmind.blockactions.actions.planteraction.PlanterTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {

    public static final DeferredRegister<TileEntityType<?>> TILES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, BlockActions.MOD_ID);

    public static final RegistryObject<TileEntityType<PlacerTileEntity>> PLACER_TILE = TILES.register("placer", () ->
            TileEntityType.Builder.create(PlacerTileEntity::new, BlockRegister.PLACER.get()).build(null));

    public static final RegistryObject<TileEntityType<BreakerTileEntity>> BREAKER_TILE = TILES.register("breaker", () ->
            TileEntityType.Builder.create(BreakerTileEntity::new, BlockRegister.BREAKER.get()).build(null));

    public static final RegistryObject<TileEntityType<HitTileEntity>> HIT_TILE = TILES.register("hit", () ->
            TileEntityType.Builder.create(HitTileEntity::new, BlockRegister.HIT.get()).build(null));

    public static final RegistryObject<TileEntityType<PlanterTileEntity>> PLANTER_TILE = TILES.register("planter", () ->
            TileEntityType.Builder.create(PlanterTileEntity::new, BlockRegister.PLANTER.get()).build(null));
}
