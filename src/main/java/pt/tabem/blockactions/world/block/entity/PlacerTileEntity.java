package pt.tabem.blockactions.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import pt.tabem.blockactions.ModBlockEntities;
import pt.tabem.blockactions.util.InvUtils;
import pt.tabem.blockactions.util.Utils;

import javax.annotation.Nonnull;

public class PlacerTileEntity extends BlockActionTileEntity {

    public PlacerTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLACER_TILE.get(), pos, state);
    }

    @Override
    public boolean stopIf(@NotNull Level level) {

        boolean notFacingAir = !this.getFacingState().isAir();
        boolean hasntInvAbove = InvUtils.tryGetContainer(this.worldPosition.above(), level).isEmpty();
        boolean toPlaceIsAir =  this.getBlockStateFromInvAbove(this.worldPosition, level).state().isAir();

        return notFacingAir || hasntInvAbove || toPlaceIsAir;
    }

    @Override
    public void doWork(@Nonnull Level world) {
        var invObj = this.getBlockStateFromInvAbove(this.worldPosition, this.level);

        if (!invObj.state().isAir()) {
            var facingPos = this.worldPosition.relative(this.getFacing());
            if (world.getBlockState(facingPos).isAir()) {
                world.setBlock(facingPos, invObj.state(), 2);
                world.playSound(null, this.worldPosition, world.getBlockState(facingPos).getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1f, 1f);
                InvUtils.removeFromInvAbove(this.worldPosition, this.level, invObj.index());
            }
        }
    }

    private InvUtils.InvObject getBlockStateFromInvAbove(BlockPos pos, Level world) {
        if (InvUtils.hasInvAbove(pos, world)) {
            var iInventory = InvUtils.getInvAbove(pos, world);
            if (iInventory != null) {
                for (int i = 0; i < iInventory.getContainerSize(); ) {
                    if (iInventory.getItem(i).getItem() instanceof BlockItem) {
                        iInventory.setChanged(); //Fact: Probably this isn't needed.
                        return new InvUtils.InvObject(i, Utils.getStateFromItem(iInventory.getItem(i)));
                    } else {
                        i++;
                    }
                }
            }
        }
        return new InvUtils.InvObject(0, Blocks.AIR.defaultBlockState());
    }
}
