package com.lazynessmind.blockactions.actions.planteraction;

import com.lazynessmind.blockactions.utils.InvUtils;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class PlanterUtils {

    public static InvUtils.InvObject getSeedFromInv(BlockPos pos, World world) {
        if (InvUtils.hasInvAbove(pos, world)) {
            IInventory iInventory = InvUtils.getInvAbove(pos, world);
            if (iInventory != null) {
                for (int i = 0; i < iInventory.getSizeInventory(); ) {
                    if (Utils.getStateFromItem(iInventory.getStackInSlot(i)).getBlock() instanceof IPlantable) {
                        iInventory.markDirty();
                        return new InvUtils.InvObject(i, Utils.getStateFromItem(iInventory.getStackInSlot(i)));
                    } else {
                        i++;
                    }
                }
            }
        }
        return new InvUtils.InvObject(0, Blocks.AIR.getDefaultState());
    }

    public static class PlaceEvent {

        private boolean success;
        private BlockPos newPos;

        public PlaceEvent(boolean success, BlockPos newPos) {
            this.success = success;
            this.newPos = newPos;
        }

        public boolean isSuccess() {
            return success;
        }

        public BlockPos getNewPos() {
            return newPos;
        }
    }
}
