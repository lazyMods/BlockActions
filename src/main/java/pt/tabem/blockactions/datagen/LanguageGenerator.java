package pt.tabem.blockactions.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import pt.tabem.blockactions.BlockActions;

public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(DataGenerator gen) {
        super(gen, BlockActions.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Tabs
        this.add("itemGroup.blockactions.tab", "Block Actions");
        //Names blocks
        this.add("block.blockactions.breaker", "§6Breaker");
        this.add("block.blockactions.placer", "§6Placer");
        this.add("block.blockactions.item_user", "§6Item User");
        //Names items
        this.add("item.blockactions.core", "§6Core");
        // Block tooltips
        this.add("tooltip.breaker.action", "§2§lAction: §rBreak blocks");
        this.add("tooltip.breaker.tip", "§2§lTip: §rThe breaker will automatically transfer the destroyed block if has an inventory above.");
        this.add("tooltip.placer.action", "§2§lAction: §rPlace blocks");
        this.add("tooltip.placer.requires", "§2§lRequires: §rContainer with block above the Placer");
        this.add("tooltip.item_user.action", "§2§lAction: §rTakes a given item and uses it on the facing block.");
        this.add("tooltip.item_user.info", "§2§lInfo: §r");
        this.add("tooltip.item_user.optional.one", "- Place a container with items to be used as an Item Clicker.");
        this.add("tooltip.item_user.optional.two", "- Place a container with blocks to be used as an Block Placer, or use the block on the block in front.");
        this.add("tooltip.item_user.optional.three", "- If the Item User doesn't have a container above it can be used as a left-clicker.");
        this.add("tooltip.item_user.optional.four", "- You can also use this as a item user and left-clicker, if the block in front supports both.");
    }
}
