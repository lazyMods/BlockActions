package pt.tabem.blockactions.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class Utils {

    public static ItemStack asStack(Item item) {
        return new ItemStack(item);
    }

    public static void spawnItem(BlockPos pos, Level level, ItemStack stack) {
        level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
    }

    public static BlockState getStateFromItem(ItemStack item) {
        return Block.byItem(item.getItem()).defaultBlockState();
    }

    public static String convertTickSec(int ticks) {
        float sec = (float) ticks / 20;
        return String.valueOf(sec).concat("sec");
    }

    public static void saveListToNbt(String key, CompoundTag tag, NonNullList<ItemStack> stacks) {
        var items = new ListTag();
        for (var stack : stacks) {
            var compound = new CompoundTag();
            stack.save(compound);
            items.add(compound);
        }
        tag.put(key, items);
    }

    public static NonNullList<ItemStack> loadListNbt(String key, CompoundTag nbt) {
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        var items = nbt.getList(key, 10);
        for (int i = 0; i < items.size(); i++) {
            var itemStack = items.getCompound(i);
            itemStacks.add(ItemStack.of(itemStack));
        }
        return itemStacks;
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void renderItem(ItemStack stack, int xPos, int yPos) {
        Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, xPos, yPos);
    }
}
