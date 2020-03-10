package com.lazynessmind.blockactions.actions.breakaction;

import com.lazynessmind.blockactions.base.BlockActionBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public class BreakerBlock extends BlockActionBase {

    public BreakerBlock() {
        super("breaker");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltips, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltips, flagIn);
        tooltips.add(new TranslationTextComponent("tooltip.breaker.action"));
        tooltips.add(new TranslationTextComponent("tooltip.breaker.tip"));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BreakerTileEntity();
    }
}