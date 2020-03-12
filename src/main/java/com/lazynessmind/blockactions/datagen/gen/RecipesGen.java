package com.lazynessmind.blockactions.datagen.gen;

import com.lazynessmind.blockactions.event.BlockRegister;
import com.lazynessmind.blockactions.event.ItemRegister;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class RecipesGen extends RecipeProvider {

    public RecipesGen(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.blockActions(consumer);
        this.upgrades(consumer);
    }

    private void blockActions(Consumer<IFinishedRecipe> consumer){
        ShapedRecipeBuilder.shapedRecipe(BlockRegister.BREAKER.get())
                .patternLine("CCC")
                .patternLine("CRC")
                .patternLine("CPC")
                .key('C', Blocks.COBBLESTONE)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('P', Blocks.PISTON)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlockRegister.PLACER.get())
                .patternLine("CCC")
                .patternLine("CRC")
                .patternLine("CDC")
                .key('C', Blocks.COBBLESTONE)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('D', Blocks.DROPPER)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlockRegister.HIT.get())
                .patternLine("CCC")
                .patternLine("CRC")
                .patternLine("CSC")
                .key('C', Blocks.COBBLESTONE)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('S', Items.DIAMOND_SWORD)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlockRegister.PLANTER.get())
                .patternLine("CCC")
                .patternLine("CRC")
                .patternLine("CHC")
                .key('C', Blocks.COBBLESTONE)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('H', Items.DIAMOND_HOE)
                .addCriterion("cobblestone", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);
    }

    private void upgrades(Consumer<IFinishedRecipe> consumer){
        ShapedRecipeBuilder.shapedRecipe(ItemRegister.ATTACK_ADULTS_UPGRADE.get())
                .patternLine("OEO")
                .patternLine("ERE")
                .patternLine("OEO")
                .key('O', Blocks.OBSIDIAN)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('E', Items.EGG)
                .addCriterion("egg", InventoryChangeTrigger.Instance.forItems(Items.EGG))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegister.ATTACK_UPGRADE.get())
                .patternLine("OQO")
                .patternLine("QRQ")
                .patternLine("OQO")
                .key('O', Blocks.OBSIDIAN)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('Q', Items.QUARTZ)
                .addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Blocks.OBSIDIAN))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegister.SPEED_UPGRADE.get())
                .patternLine("ONO")
                .patternLine("NRN")
                .patternLine("ONO")
                .key('O', Blocks.OBSIDIAN)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('N', Items.NETHER_WART)
                .addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Blocks.OBSIDIAN))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegister.ENERGY_MODE_UPGRADE.get())
                .patternLine("OEO")
                .patternLine("ERE")
                .patternLine("OEO")
                .key('O', Blocks.OBSIDIAN)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('E', Items.ENDER_PEARL)
                .addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Blocks.OBSIDIAN))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegister.SILK_TOUCH_UPGRADE.get())
                .patternLine("OEO")
                .patternLine("ERE")
                .patternLine("OEO")
                .key('O', Blocks.OBSIDIAN)
                .key('R', Blocks.REDSTONE_BLOCK)
                .key('E', Items.EMERALD)
                .addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Blocks.OBSIDIAN))
                .build(consumer);
    }
}