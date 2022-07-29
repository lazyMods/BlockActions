package pt.tabem.blockactions.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import pt.tabem.blockactions.ModBlockEntities;
import pt.tabem.blockactions.util.InvUtils;

import javax.annotation.Nonnull;

public class BreakerTileEntity extends BlockActionTileEntity {

    public BreakerTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREAKER_TILE.get(), pos, state);
    }

    @Override
    public boolean stopIf(@NotNull Level level) {
        return this.getFacingState().isAir();
    }

    @Override
    public void doWork(@Nonnull Level world) {
        var facingPos = this.worldPosition.relative(direction);
        boolean isAir = this.getFacingState().isAir();
        boolean isLiquid = this.getFacingState().getBlock() instanceof LiquidBlock;
        boolean canBreak = this.getFacingState().getDestroySpeed(world, facingPos) >= 0;
        boolean isTileEntity = this.getFacingState().hasBlockEntity();
        boolean hasInv = InvUtils.hasInv(facingPos, world);

        if (isTileEntity) return;
        if (!hasInv && !isAir && !isLiquid && canBreak) {
            this.breakBlock(world, facingPos);
        }
    }

    private void breakBlock(@Nonnull Level world, BlockPos facingPos) {
        var toDrop = world.getBlockState(facingPos);
        var drops = Block.getDrops(toDrop, (ServerLevel) world, facingPos, world.getBlockEntity(facingPos));
        boolean canInsertItem = false;
        for (ItemStack stack : drops)
            canInsertItem = InvUtils.addToInvAbove(this.worldPosition, world, stack);
        world.destroyBlock(facingPos, !canInsertItem);
    }
}
