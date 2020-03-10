package com.lazynessmind.blockactions.event;

import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.actions.breakaction.BreakerBlock;
import com.lazynessmind.blockactions.actions.hitaction.HitBlock;
import com.lazynessmind.blockactions.actions.placeaction.PlacerBlock;
import com.lazynessmind.blockactions.actions.planteraction.PlanterBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegister {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, BlockActions.MOD_ID);

    public static RegistryObject<BreakerBlock> BREAKER = BLOCKS.register("breaker", BreakerBlock::new);
    public static RegistryObject<PlacerBlock> PLACER = BLOCKS.register("placer", PlacerBlock::new);
    public static RegistryObject<HitBlock> HIT = BLOCKS.register("hit", HitBlock::new);
    public static RegistryObject<PlanterBlock> PLANTER = BLOCKS.register("planter", PlanterBlock::new);
}
