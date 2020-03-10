package com.lazynessmind.blockactions.items;

import com.lazynessmind.blockactions.actions.hitaction.HitTileEntity;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AttackAdultMobsUpgrade extends UpgradeItem {

    @Override
    public ApplyState applyUpgrade(BlockActionTileEntity tileEntityBase) {
        if(tileEntityBase instanceof HitTileEntity){
            HitTileEntity hitTileEntity = (HitTileEntity)tileEntityBase;
            if(!hitTileEntity.attackOnlyAdults){
                hitTileEntity.attackOnlyAdults = true;
                return new ApplyState(Utils.asStack(this), true, 0);
            }
        }
        return ApplyState.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.attack_adults_upgrade.canapply"));
        tooltip.add(new TranslationTextComponent("tooltip.attack_adults_upgrade.info"));
    }
}
