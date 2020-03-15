package com.lazynessmind.blockactions.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class Utils {

    public static ItemStack asStack(Item item) {
        return new ItemStack(item);
    }

    public static void spawnItem(BlockPos pos, World world, ItemStack stack) {
        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), stack));
    }

    public static BlockState getStateFromItem(ItemStack item) {
        return Block.getBlockFromItem(item.getItem()).getDefaultState();
    }

    public static String convertTickSec(int ticks) {
        float sec = (float) ticks / 20;
        return String.valueOf(sec).concat("sec");
    }

    public static void saveListToNbt(String key, CompoundNBT tag, NonNullList<ItemStack> stacks) {
        ListNBT items = new ListNBT();
        for (ItemStack stack : stacks) {
            CompoundNBT compoundnbt = new CompoundNBT();
            stack.write(compoundnbt);
            items.add(compoundnbt);
        }
        tag.put(key, items);
    }

    public static NonNullList<ItemStack> loadListNbt(String key, CompoundNBT nbt) {
        NonNullList<ItemStack> itemStacks = NonNullList.create();
        ListNBT items = nbt.getList(key, 10);
        for (int i = 0; i < items.size(); i++) {
            CompoundNBT itemStack = items.getCompound(i);
            itemStacks.add(ItemStack.read(itemStack));
        }
        return itemStacks;
    }

    public static <T extends Entity> List<T> getEntitiesInSpace(Class<T> entity, World world, BlockPos facingPos) {
        return world.getEntitiesWithinAABB(entity, new AxisAlignedBB(facingPos));
    }

    public static boolean isWater(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == Blocks.WATER || world.getBlockState(pos).has(BlockStateProperties.WATERLOGGED);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderItem(ItemStack stack, int xPos, int yPos){
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, xPos, yPos);
    }
}
