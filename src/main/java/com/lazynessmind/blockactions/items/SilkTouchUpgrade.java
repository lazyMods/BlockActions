package com.lazynessmind.blockactions.items;

import com.lazynessmind.blockactions.actions.breakaction.BreakerTileEntity;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SilkTouchUpgrade extends UpgradeItem {

    @Override
    public ApplyState applyUpgrade(BlockActionTileEntity tileEntityBase) {
        if (tileEntityBase instanceof BreakerTileEntity) {
            BreakerTileEntity breakerTileEntity = (BreakerTileEntity) tileEntityBase;
            if(!breakerTileEntity.hasSilkTouch){
                breakerTileEntity.hasSilkTouch = true;
                return new ApplyState(Utils.asStack(this), true, 0);
            }
        }
        return ApplyState.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.silktouchupgrade.canapply"));
        tooltip.add(new TranslationTextComponent("tooltip.silktouchupgrade.info"));
    }
}
