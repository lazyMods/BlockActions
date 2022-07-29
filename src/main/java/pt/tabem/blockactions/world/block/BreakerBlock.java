package pt.tabem.blockactions.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pt.tabem.blockactions.world.block.entity.BlockActionTileEntity;
import pt.tabem.blockactions.world.block.entity.BreakerTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class BreakerBlock extends BlockActionBase {

    @NotNull
    @Override
    public BlockActionTileEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BreakerTileEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip.breaker.action"));
        tooltip.add(new TranslatableComponent("tooltip.breaker.tip"));
    }
}