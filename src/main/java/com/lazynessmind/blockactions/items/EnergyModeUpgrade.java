package com.lazynessmind.blockactions.items;

import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EnergyModeUpgrade extends UpgradeItem {

    @Override
    public ApplyState applyUpgrade(BlockActionTileEntity tileEntityBase) {
        if(!tileEntityBase.isEnergyMode()){
            tileEntityBase.setEnergyMode(true);
            return new ApplyState(Utils.asStack(this), true, 0);
        }
        return ApplyState.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.energymodeupgrade.canapply"));
        tooltip.add(new TranslationTextComponent("tooltip.energymodeupgrade.info"));
    }
}
