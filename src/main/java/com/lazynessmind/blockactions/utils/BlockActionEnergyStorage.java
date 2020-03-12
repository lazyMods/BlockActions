package com.lazynessmind.blockactions.utils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.IEnergyStorage;

public class BlockActionEnergyStorage implements IEnergyStorage {

    private static final String TAG_CURRENT_ENERGY = "CurrentEnergy";
    private int currentEnergy = 0;
    private boolean canInsert = false;

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) return 0;
        int res = Math.min((maxReceive + this.getEnergyStored()), this.getMaxEnergyStored());
        if (!simulate) this.setCurrentEnergy(res);
        return res;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return this.currentEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return 10000;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return this.canInsert;
    }

    public void setCanInsert(boolean canInsert) {
        this.canInsert = canInsert;
    }

    public void removeEnergy(int amount) {
        if (canRemove(amount)) {
            int res = Math.max(this.getEnergyStored() - amount, 0);
            this.setCurrentEnergy(res);
        }
    }

    public void setCurrentEnergy(int value) {
        this.currentEnergy = value;
    }

    public void saveEnergy(CompoundNBT nbt) {
        nbt.putInt(TAG_CURRENT_ENERGY, this.getEnergyStored());
    }

    public void loadEnergy(CompoundNBT nbt) {
        if (nbt.contains(TAG_CURRENT_ENERGY)) {
            this.currentEnergy = nbt.getInt(TAG_CURRENT_ENERGY);
        }
    }

    public boolean canRemove(int amount) {
        return (this.getEnergyStored() - amount) >= 0;
    }

    public static class EnergyInfo {
        private int current;
        private int max;

        public static final EnergyInfo BLANK = new EnergyInfo(0, 0);

        public EnergyInfo(int current, int max) {
            this.current = current;
            this.max = max;
        }

        public int getCurrent() {
            return this.current;
        }

        public int getMax() {
            return this.max;
        }

        public void set(int current, int max) {
            this.current = current;
            this.max = max;
        }
    }
}
