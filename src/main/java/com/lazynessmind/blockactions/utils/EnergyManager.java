package com.lazynessmind.blockactions.utils;

import com.lazynessmind.blockactions.utils.BlockActionEnergyStorage.EnergyInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnergyManager {

    private LazyOptional<BlockActionEnergyStorage> energyStorage;

    public EnergyManager(LazyOptional<BlockActionEnergyStorage> energyCap) {
        this.energyStorage = energyCap;
    }

    public EnergyInfo getEnergyInfo() {
        final EnergyInfo energyInfo = EnergyInfo.BLANK;
        this.energyStorage.ifPresent((energy -> {
            energyInfo.set(energy.getEnergyStored(), energy.getMaxEnergyStored());
        }));
        return energyInfo;
    }

    public void addEnergy(int amount) {
        this.energyStorage.ifPresent((energy) -> energy.receiveEnergy(amount, true));
    }

    public void removeEnergy(int amount) {
        this.energyStorage.ifPresent((energy) -> {
                energy.removeEnergy(amount);
        });
    }

    public boolean canRemove(int amount) {
        AtomicBoolean can = new AtomicBoolean(false);
        this.energyStorage.ifPresent((energyStorage) -> {
            if (energyStorage.canRemove(amount)) {
                can.set(true);
            }
        });
        return can.get();
    }

    public void saveCurrentEnergy(CompoundNBT nbt) {
        this.energyStorage.ifPresent((energy) -> energy.saveEnergy(nbt));
    }

    public void loadCurrentEnergy(CompoundNBT nbt) {
        this.energyStorage.ifPresent((energy) -> energy.loadEnergy(nbt));
    }

    public void canInsert(boolean value) {
        this.energyStorage.ifPresent((energyStorage) -> energyStorage.setCanInsert(value));
    }

    @Override
    public String toString() {
        return this.getEnergyInfo().getCurrent() + "/" + this.getEnergyInfo().getMax();
    }
}
