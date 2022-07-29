package pt.tabem.blockactions.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pt.tabem.blockactions.ModBlockEntities;
import pt.tabem.blockactions.world.block.entity.BlockActionTileEntity;
import pt.tabem.blockactions.world.block.entity.PlacerTileEntity;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class PlacerBlock extends BlockActionBase {

    @NotNull
    @Override
    public BlockActionTileEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlacerTileEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("tooltip.placer.action"));
        tooltip.add(new TranslatableComponent("tooltip.placer.requires"));
    }
}
