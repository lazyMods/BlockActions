package pt.tabem.blockactions.datagen;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    static void onGatherData(GatherDataEvent event) {
        if (event.includeServer()) {
            event.getGenerator().addProvider(new RecipesGenerator(event.getGenerator()));
            event.getGenerator().addProvider(new LootTableGenerator(event.getGenerator()));
            event.getGenerator().addProvider(new TagGenerator(event.getGenerator(), event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
            event.getGenerator().addProvider(new LanguageGenerator(event.getGenerator()));
            event.getGenerator().addProvider(new ItemModelGenerator(event.getGenerator(), event.getExistingFileHelper()));
            event.getGenerator().addProvider(new BlockGenerator(event.getGenerator(), event.getExistingFileHelper()));
        }
    }
}
