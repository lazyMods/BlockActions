package pt.tabem.blockactions.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.tabem.blockactions.ModBlocks;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Modified version of: https://github.com/McJty/YouTubeModding14/blob/06097eee7db535d55c6cbe0bb4a523b07335fa33/src/main/java/com/mcjty/mytutorial/datagen/BaseLootTableProvider.java#L23
public class LootTableGenerator extends LootTableProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> blockLootTables = new HashMap<>();
    private final DataGenerator generator;

    public LootTableGenerator(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    private void createTables() {
        this.lootTableWithOneOfItself("placer", ModBlocks.PLACER.get());
        this.lootTableWithOneOfItself("breaker", ModBlocks.BREAKER.get());
        this.lootTableWithOneOfItself("item_user", ModBlocks.ITEM_USE.get());
    }

    public void lootTableWithOneOfItself(String regName, Block block) {
        this.blockLootTables.put(block, LootTable.lootTable().withPool(new LootPool.Builder().name(regName).add(LootItem.lootTableItem(block))));
    }

    @Override
    public void run(@Nonnull HashCache cache) {
        this.createTables();

        var tables = new HashMap<ResourceLocation, LootTable>();
        blockLootTables.forEach((block, builder) -> tables.put(block.getLootTable(), builder.setParamSet(LootContextParamSets.BLOCK).build()));

        this.writeTables(cache, tables);
    }

    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        var outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            var path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
}