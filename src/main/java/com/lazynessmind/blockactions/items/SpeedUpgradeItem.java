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

public class SpeedUpgradeItem extends UpgradeItem {

    @Override
    public ApplyState applyUpgrade(BlockActionTileEntity tileEntityBase) {
        int newCoolDown = tileEntityBase.getCoolDown() / 2;
        if (newCoolDown > 0 && tileEntityBase.isEnergyMode()) {
            tileEntityBase.setCoolDown(tileEntityBase.getCoolDown() / 2);
            return new ApplyState(Utils.asStack(this), true, 200);
        }
        return ApplyState.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.speedupgrade.canapply"));
        tooltip.add(new TranslationTextComponent("tooltip.speedupgrade.info"));
    }
}
