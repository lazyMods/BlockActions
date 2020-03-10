package com.lazynessmind.test;

import com.lazynessmind.blockactions.event.TileEntityRegister;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergy extends TileEntity implements ITickableTileEntity, IEnergyStorage {

    public TileEntityEnergy() {
        super(TileEntityRegister.ENERGY_TILE.get());
    }

    @Override
    public void tick() {
        if(world != null){
            if(!world.isRemote){
                for(Direction dir : Direction.values()){
                    TileEntity tileAt = world.getTileEntity(pos.offset(dir));
                    if(tileAt != null){
                        tileAt.getCapability(CapabilityEnergy.ENERGY).ifPresent((energy)->{
                            energy.receiveEnergy(10, false);
                            extractEnergy(10, false);
                        });
                    }
                }
            }
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return this.getMaxEnergyStored() - maxExtract;
    }

    @Override
    public int getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
