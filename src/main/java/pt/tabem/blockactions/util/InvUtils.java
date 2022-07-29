package pt.tabem.blockactions.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class InvUtils {

    public static Optional<Container> tryGetContainer(BlockPos pos, @Nonnull Level level) {
        var atPos = level.getBlockState(pos).hasBlockEntity()
                && level.getBlockEntity(pos) instanceof Container container ? container
                : null;
        return Optional.ofNullable(atPos);
    }

    public static boolean hasInv(BlockPos pos, Level world) {
        if (world.getBlockState(pos).hasBlockEntity()) {
            return world.getBlockEntity(pos) instanceof Container;
        }
        return false;
    }

    public static boolean hasInvAbove(BlockPos pos, Level world) {
        if (world.getBlockState(pos.above()).hasBlockEntity()) {
            return world.getBlockEntity(pos.above()) instanceof Container;
        }
        return false;
    }

    public static Container getInvAbove(BlockPos pos, Level world) {
        return hasInvAbove(pos, world) ? (Container) world.getBlockEntity(pos.above()) : null;
    }

    public static void removeFromInvAbove(BlockPos pos, Level world, int index) {
        if (hasInvAbove(pos, world)) {
            var iInventory = getInvAbove(pos, world);
            if (iInventory != null) {
                iInventory.removeItem(index, 1);
                iInventory.setChanged();
            }
        }
    }

    public static boolean addToInvAbove(BlockPos pos, Level world, ItemStack stack) {
        if (hasInvAbove(pos, world)) {
            var iInventory = getInvAbove(pos, world);
            if (iInventory != null) {
                for (int i = 0; i < iInventory.getContainerSize(); ) {
                    ItemStack slot = iInventory.getItem(i);
                    if (slot.getItem() == stack.getItem() && ItemStack.tagMatches(slot, stack)) {
                        int count = slot.getCount() + 1;
                        if (count <= iInventory.getMaxStackSize()) {
                            slot.setCount(count);
                            iInventory.setChanged();
                            return true;
                        } else {
                            i++;
                        }
                    } else if (slot == ItemStack.EMPTY) {
                        iInventory.setItem(i, stack);
                        iInventory.setChanged();
                        return true;
                    } else {
                        i++;
                    }
                }
            }
        }
        return false;
    }

    public static Optional<ItemStack> getNotEmpty(Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (!container.getItem(i).isEmpty()) return Optional.of(container.getItem(i));
        }
        return Optional.empty();
    }

    public record InvObject(int index, BlockState state) {

    }
}
