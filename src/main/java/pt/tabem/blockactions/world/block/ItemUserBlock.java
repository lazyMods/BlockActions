package pt.tabem.blockactions.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import pt.tabem.blockactions.world.block.entity.BlockActionTileEntity;
import pt.tabem.blockactions.world.block.entity.ItemUserTileEntity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class ItemUserBlock extends BlockActionBase {

    @NotNull
    @Override
    public BlockActionTileEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemUserTileEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip.item_user.action"));
        tooltip.add(new TranslatableComponent("tooltip.item_user.info"));
        tooltip.add(new TranslatableComponent("tooltip.item_user.optional.one"));
        tooltip.add(new TranslatableComponent("tooltip.item_user.optional.two"));
        tooltip.add(new TranslatableComponent("tooltip.item_user.optional.three"));
        tooltip.add(new TranslatableComponent("tooltip.item_user.optional.four"));
    }
}