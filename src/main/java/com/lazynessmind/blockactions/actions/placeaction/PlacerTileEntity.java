package com.lazynessmind.blockactions.actions.placeaction;

import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.event.TileEntityRegister;
import com.lazynessmind.blockactions.utils.InvUtils;
import com.lazynessmind.blockactions.utils.InvUtils.InvObject;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PlacerTileEntity extends BlockActionTileEntity implements ITickableTileEntity {

    public Direction direction;

    public PlacerTileEntity() {
        super(TileEntityRegister.PLACER_TILE.get());
    }

    @Override
    public void tick() {
        if (this.world != null) {
            if (!this.world.isRemote) {
                if (this.getBlockState().has(PlacerBlock.FACING)) {
                    this.direction = this.getBlockState().get(PlacerBlock.FACING);
                }

                InvObject invItem = PlacerUtils.getBlockStateFromInvAbove(this.pos, this.world);
                BlockState fromInv = invItem.getState();

                if (fromInv != Blocks.AIR.getDefaultState() && this.world.getBlockState(pos.offset(this.getFacing())) == Blocks.AIR.getDefaultState()) {
                    this.decreaseWorkTime();
                }

                if (this.isEnergyMode()) {
                    this.energyManager.canInsert(true);
                    if (this.canWork()) {
                        this.doWork(this.world, this.pos, true);
                    }
                }
            }
        }
    }

    @Override
    public void doWork(@Nonnull World world, BlockPos pos, boolean energyMode) {
        InvObject invItem = PlacerUtils.getBlockStateFromInvAbove(this.pos, this.world);
        BlockState fromInv = invItem.getState();

        if (this.getFacing() != null) {
            if(this.isEnergyMode() && !this.energyManager.canRemove(this.getEnergyCost())) return;
            if (fromInv != Blocks.AIR.getDefaultState()) {
                BlockPos facingPos = this.pos.offset(this.getFacing());
                if (this.world.isAirBlock(facingPos)) {
                    this.world.setBlockState(facingPos, fromInv, 11);
                    this.world.playSound(null, pos, this.world.getBlockState(facingPos).getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1f, 1f);
                    this.setWorkTime(this.getCoolDown());
                    if (invItem.getState() != Blocks.AIR.getDefaultState()) {
                        InvUtils.removeFromInvAbove(this.pos, this.world, invItem.getIndex());
                    }
                    if (energyMode) this.energyManager.removeEnergy(this.getEnergyCost());
                }
            }
        }
    }

    public Direction getFacing() {
        return this.direction;
    }
}
