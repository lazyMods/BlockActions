package pt.tabem.blockactions.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import pt.tabem.blockactions.ModItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

public class RecipesGenerator extends RecipeProvider {

    public RecipesGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.blockActions(consumer);
        shapedRecipe(consumer, ModItems.CORE.get(),
                "CRC.RGR.CRC",
                new UnlockedBy("coal", Items.COAL),
                key('C', Items.COAL), key('R', Items.REDSTONE), key('G', Items.GOLD_BLOCK)
        );
    }

    private void blockActions(Consumer<FinishedRecipe> consumer) {
        var basaltTrigger = new UnlockedBy("basalt", Blocks.BASALT);
        var main = new Key('B', Blocks.COBBLED_DEEPSLATE);
        var core = new Key('C', ModItems.CORE.get());
        shapedRecipe(consumer, ModItems.BREAKER.get(), "BBB.BCB.BPB", basaltTrigger, main, core, key('P', Items.GOLDEN_PICKAXE));
        shapedRecipe(consumer, ModItems.PLACER.get(), "BBB.BCB.BDB", basaltTrigger, main, core, key('D', Blocks.DROPPER));
        shapedRecipe(consumer, ModItems.ITEM_USER.get(), "BDB.BCB.BFB", basaltTrigger, main, core, key('D', Items.DIAMOND), key('F', Blocks.DROPPER));
    }

    private void shapedRecipe(Consumer<FinishedRecipe> consumer, ItemLike itemLike, String pattern, UnlockedBy unlockedBy, Key... keys) {
        var patterns = pattern.split("\\.");
        var recipeBuilder = ShapedRecipeBuilder.shaped(itemLike);
        for (var s : patterns) recipeBuilder.pattern(s);
        for (var key : keys) recipeBuilder.define(key.c, key.like);
        recipeBuilder.unlockedBy(unlockedBy.name, TriggerInstance.hasItems(unlockedBy.like)).save(consumer);
    }

    private Key key(char c, ItemLike like) {
        return new Key(c, like);
    }

    private record Key(char c, ItemLike like) {
    }

    private record UnlockedBy(String name, ItemLike like) {
    }
}