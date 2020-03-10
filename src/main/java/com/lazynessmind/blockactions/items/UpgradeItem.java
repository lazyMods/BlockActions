package com.lazynessmind.blockactions.items;

import com.lazynessmind.blockactions.Configs;
import com.lazynessmind.blockactions.base.BlockActionBase;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.base.ModItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class UpgradeItem extends ModItem {

    public UpgradeItem() {
        super(ItemGroup.REDSTONE);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();

        if (!world.isRemote) {
            if (world.getBlockState(pos).getBlock() instanceof BlockActionBase) {
                if (world.getBlockState(pos).hasTileEntity()) {
                    if (world.getTileEntity(pos) instanceof BlockActionTileEntity) {
                        BlockActionTileEntity tileEntityBase = (BlockActionTileEntity) world.getTileEntity(pos);
                        if (tileEntityBase != null) {
                            if (tileEntityBase.getCurrentUpgrades() < Configs.MAX_UPGRADE_COUNT.get()) {
                                ApplyState applyState = this.applyUpgrade(tileEntityBase);
                                if (applyState.getResult()) {
                                    tileEntityBase.currentUpgrades++;
                                    tileEntityBase.getUpgradeItems().add(applyState.getAppliedUpg());
                                    context.getItem().shrink(1);
                                    tileEntityBase.increaseEnergyCost(applyState.getUpgradeCost());
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onItemUse(context);
    }

    public abstract ApplyState applyUpgrade(BlockActionTileEntity tileEntityBase);

    public static class ApplyState {

        private ItemStack appliedUpg;
        private boolean result;
        private int upgradeCost;

        public static final ApplyState FAIL = new ApplyState(null, false, 0);

        public ApplyState(@Nullable ItemStack appliedUpg, boolean result, int upgradeCost) {
            this.appliedUpg = appliedUpg;
            this.result = result;
            this.upgradeCost = upgradeCost;
        }

        public ItemStack getAppliedUpg() {
            return this.appliedUpg;
        }

        public boolean getResult() {
            return this.result;
        }

        public int getUpgradeCost() {
            return upgradeCost;
        }
    }
}
