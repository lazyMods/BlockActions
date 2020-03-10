package com.lazynessmind.blockactions.base;

import com.lazynessmind.blockactions.Configs;
import com.lazynessmind.blockactions.api.IInfo;
import com.lazynessmind.blockactions.utils.BlockActionEnergyStorage;
import com.lazynessmind.blockactions.utils.EnergyManager;
import com.lazynessmind.blockactions.utils.Utils;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;

public abstract class BlockActionTileEntity extends TileEntity implements ITickableTileEntity, IInfo {

    private static final String TAG_WORKTIME = "WorkTime";
    public int workTime = -1;

    private static final String TAG_COOLDOWN = "CoolDown";
    private int coolDown = Configs.DEFAULT_COOLDOWN.get();

    private static final String TAG_ENERGY_PER_WORK = "EnergyPerWork";
    private int energyCost = Configs.ENERGY_PER_WORK.get();

    private static final String TAG_UPGRADE_COUNT = "CurrentUpgradeCount";
    public int currentUpgrades = 0;

    private static final String TAG_ENERGY_MODE = "IsEnergyMode";
    private boolean energyMode;

    private static final String TAG_UPGRADES = "Upgrades";
    private NonNullList<ItemStack> upgradeItems = NonNullList.create();

    private PlayerEntity fakePlayer;
    public final LazyOptional<BlockActionEnergyStorage> ENERGY = LazyOptional.of(BlockActionEnergyStorage::new);
    public EnergyManager energyManager = new EnergyManager(ENERGY);

    public BlockActionTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
        this.energyManager.canInsert(this.isEnergyMode());
    }

    public abstract void doWork(@Nonnull World world, BlockPos pos, boolean energyMode);

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.workTime = compound.getInt(TAG_WORKTIME);
        this.coolDown = compound.getInt(TAG_COOLDOWN);
        this.energyCost = compound.getInt(TAG_ENERGY_PER_WORK);
        this.currentUpgrades = compound.getInt(TAG_UPGRADE_COUNT);
        this.energyMode = compound.getBoolean(TAG_ENERGY_MODE);
        this.energyManager.loadCurrentEnergy(compound);
        this.upgradeItems = Utils.loadListNbt(TAG_UPGRADES, compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt.putInt(TAG_WORKTIME, this.workTime);
        nbt.putInt(TAG_COOLDOWN, this.coolDown);
        nbt.putInt(TAG_ENERGY_PER_WORK, this.energyCost);
        nbt.putInt(TAG_UPGRADE_COUNT, this.currentUpgrades);
        nbt.putBoolean(TAG_ENERGY_MODE, this.energyMode);
        this.energyManager.saveCurrentEnergy(nbt);
        Utils.saveListToNbt(TAG_UPGRADES, nbt, this.upgradeItems);
        return nbt;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityEnergy.ENERGY) {
            return ENERGY.cast();
        }
        return super.getCapability(cap);
    }

    public boolean canWork() {
        return this.workTime <= 0;
    }

    public int getWorkTime() {
        return this.workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
        this.markDirty();
    }

    public void decreaseWorkTime() {
        this.workTime--;
        this.markDirty();
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
        this.markDirty();
    }

    public int getCoolDown() {
        return this.coolDown;
    }

    public int getCurrentUpgrades() {
        return this.currentUpgrades;
    }

    public void setEnergyMode(boolean energyMode) {
        this.energyMode = energyMode;
    }

    public boolean isEnergyMode() {
        return this.energyMode;
    }

    public int getEnergyCost() {
        return this.energyCost;
    }

    public void increaseEnergyCost(int value) {
        this.energyCost += value;
    }

    public NonNullList<ItemStack> getUpgradeItems() {
        return this.upgradeItems;
    }

    public void setFakePlayer(World world, String playerName) {
        this.fakePlayer = new PlayerEntity(world, new GameProfile(null, playerName)) {
            @Override
            public boolean isSpectator() {
                return true;
            }

            @Override
            public boolean isCreative() {
                return false;
            }
        };
    }

    public PlayerEntity getFakePlayer() {
        return this.fakePlayer;
    }

    @Override
    public CompoundNBT getInfo() {
        CompoundNBT info = new CompoundNBT();

        info.putString("currentUpgrades", new TranslationTextComponent("infooverlay.upgrades").appendText(this.getCurrentUpgrades() + "/" + Configs.MAX_UPGRADE_COUNT.get()).getFormattedText());

        if (this.isEnergyMode()) {
            info.putString("energyCost", new TranslationTextComponent("infooverlay.energycost").appendText(String.valueOf(this.getEnergyCost())).getFormattedText());
            info.putString("energy", new TranslationTextComponent("infooverlay.energy").appendText(this.energyManager.toString()).getFormattedText());
            info.putString("cooldown", new TranslationTextComponent("infooverlay.cooldown").appendText(Utils.convertTickSec(this.getCoolDown())).getFormattedText());
            info.putString("workTime", new TranslationTextComponent("infooverlay.worktime").appendText("" + Math.max(this.getWorkTime(), 0)).getFormattedText());
        }
        return info;
    }
}
