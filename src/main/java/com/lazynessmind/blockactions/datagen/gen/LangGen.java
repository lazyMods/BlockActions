package com.lazynessmind.blockactions.datagen.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lazynessmind.blockactions.BlockActions;
import com.lazynessmind.blockactions.utils.Log;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.translate.JavaUnicodeEscaper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class LangGen extends LanguageProvider {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> data = new TreeMap<>();

    public String locale;
    public DataGenerator gen;

    public LangGen(DataGenerator gen, String locale) {
        super(gen, BlockActions.MOD_ID, locale);
        this.locale = locale;
        this.gen = gen;
    }

    @Override
    protected void addTranslations() {
        if (locale.equals("pt_br")) {
            portuguese();
        } else if (locale.equals("en_us")) {
            english();
        }
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        addTranslations();
        if (!data.isEmpty())
            savethis(cache, data, this.gen.getOutputFolder().resolve("assets/" + BlockActions.MOD_ID + "/lang/" + locale + ".json"));
    }

    private void savethis(DirectoryCache cache, Object object, Path target) throws IOException {
        String data = GSON.toJson(object).replace("\u00C2", ""); //Fix the \u00C2 before §
        data = JavaUnicodeEscaper.outsideOf(0, 0x7f).translate(data); // Escape unicode after the fact so that it's not double escaped by GSON
        String hash = IDataProvider.HASH_FUNCTION.hashUnencodedChars(data).toString();
        if (!Objects.equals(cache.getPreviousHash(target), hash) || !Files.exists(target)) {
            Files.createDirectories(target.getParent());

            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(target)) {
                bufferedwriter.write(new String(data.getBytes(StandardCharsets.UTF_8)));
            }
        }

        cache.recordHash(target, hash);
    }

    private void portuguese() {
        //Names Blocks
        addTrans("block.blockactions.breaker", "§6Quebrador");
        addTrans("block.blockactions.placer", "§6Colocador");
        addTrans("block.blockactions.hit", "§6Atacante");
        addTrans("block.blockactions.planter", "§6Plantador");
        //Names Items
        addTrans("item.blockactions.speed_upgrade", "§6Upgrade de Velocidade");
        addTrans("item.blockactions.attack_upgrade", "§6Upgrade de Dano");
        addTrans("item.blockactions.attack_adults_upgrade", "§6Upgrade de Modo: Atacar apenas animais adultos");
        addTrans("item.blockactions.energy_mode_upgrade", "§6Upgrade de Modo: Energia");
        addTrans("item.blockactions.silk_touch_upgrade", "§6Upgrade de Modo: Toque de Seda");
        //Tooltips Blocks
        addTrans("tooltip.breaker.action", "§2§lFunção: §rQuebrar blocos");
        addTrans("tooltip.breaker.tip", "§2§lDica: §rO bloco destruído será transferido para um inventário acima se disponível.");
        addTrans("tooltip.placer.action", "§2§lFunção: §rColocar blocos");
        addTrans("tooltip.hit.action", "§2§lFunção: §rAtacar entidades");
        addTrans("tooltip.hit.tip", "§2§lDica: §rColoca um Item Frame na frente com uma arma para aumentar o dano.");
        addTrans("tooltip.planter.action", "§2§lFunção: §rPlantar sementes");
        //Tooltips upgrades
        addTrans("tooltip.speedupgrade.canapply", "§2§lPode aplicar em: §rTodos.");
        addTrans("tooltip.speedupgrade.info", "§2§lInfo: §rQuando aplicado reduz o tempo de cooldown em metade. (Apenas no modo de energia).");
        addTrans("tooltip.attackupgrade.canapply", "§2§lPode aplicar em: §rAtacador.");
        addTrans("tooltip.attackupgrade.info", "§2§lInfo: §rQuando aplicado aumenta o dano base em 1.");
        addTrans("tooltip.attack_adults_upgrade.canapply", "§2§lPode aplicar em: §rAtacador.");
        addTrans("tooltip.attack_adults_upgrade.info", "§2§lInfo: §rQuando aplicado apenas ataca animais adultos.");
        addTrans("tooltip.energymodeupgrade.canapply", "§2§lPode aplicar em: §rTodos.");
        addTrans("tooltip.energymodeupgrade.info", "§2§lInfo: §rQuando aplicado muda para o modo Energia.");
        addTrans("tooltip.silktouchupgrade.canapply", "§2§lPode aplicar em: §rQuebrador.");
        addTrans("tooltip.silktouchupgrade.info", "§2§lInfo: §rAplica o encantamento Toque de Seda no bloco.");
        //Info Overlay
        addTrans("infooverlay.energy", "§3§lEnergia: §r");
        addTrans("infooverlay.energycost", "§3§lCusto de Energia: §r");
        addTrans("infooverlay.working", "§3§lFuncionando: §r");
        addTrans("infooverlay.cooldown", "§3§lCooldown: §r");
        addTrans("infooverlay.upgrades", "§3§lUpgrades: §r");
        addTrans("infooverlay.worktime", "§3§lTempo de trabalho: §r");
        addTrans("infooverlay.silktouch", "§3§lModo Toque de Seda: §r");
        addTrans("infooverlay.sneak", "Agachar para ver info!");
        addTrans("infooverlay.placer.needchest", "Precisas de um inventário com blocos por cima.");
        addTrans("infooverlay.breaker.canbreakinv", "Não pode quebrar blocos com um inventário");
        addTrans("infooverlay.breaker.canbreakte", "Não pode quebrar Tile Entities");
        addTrans("infooverlay.hit.damage", "§3§lDano: §r");
        addTrans("infooverlay.hit.onlyadults", "§3§lAtacando apenas adultos.");
        addTrans("infooverlay.hit.morethanone", "§eAviso: Mais de um Item Frame não vai aumentar o dano.");
        addTrans("infooverlay.planter.needchest", "Precisa de um inventário com sementes por cima.");
        addTrans("infooverlay.planter.needwater", "Coloca água por baixo para funcionar.");
    }

    private void english() {
        //Names blocks
        addTrans("block.blockactions.breaker", "§6Breaker");
        addTrans("block.blockactions.placer", "§6Placer");
        addTrans("block.blockactions.hit", "§6Hit");
        addTrans("block.blockactions.planter", "§6Planter");
        //Names items
        addTrans("item.blockactions.speed_upgrade", "§6Speed Upgrade");
        addTrans("item.blockactions.attack_upgrade", "§6Attack Upgrade");
        addTrans("item.blockactions.attack_adults_upgrade", "§6Mode Upgrade: §rAttack only Adult Mobs");
        addTrans("item.blockactions.energy_mode_upgrade", "§6Mode Upgrade: §rEnergy");
        addTrans("item.blockactions.silk_touch_upgrade", "§6Mode Upgrade: §rSilk Touch");
        // Block tooltips
        addTrans("tooltip.breaker.action", "§2§lAction: §rBreak blocks");
        addTrans("tooltip.breaker.tip", "§2§lTip: §rThe breaker will automatically transfer the destroyed block if has an inventory above.");
        addTrans("tooltip.placer.action", "§2§lAction: §rPlace blocks");
        addTrans("tooltip.hit.action", "§2§lAction: §rAttack entities");
        addTrans("tooltip.hit.tip.one", "§2§lTip #1: §rPut an Item Frame with a weapon to increase the damage.");
        addTrans("tooltip.hit.tip.two", "§2§lTip #2: §rEnchant the weapon with Fire Aspect to get cooked food.");
        addTrans("tooltip.planter.action", "§2§lAction: §rPlant seeds on farmland");
        //Upgrade tooltips
        addTrans("tooltip.speedupgrade.canapply", "§2§lCan apply on: §rAll.");
        addTrans("tooltip.speedupgrade.info", "§2§lInfo: §rOn applied cuts the current cooldown on half. (Only on energy mode)");
        addTrans("tooltip.attackupgrade.canapply", "§2§lCan apply on: §rHit.");
        addTrans("tooltip.attackupgrade.info", "§2§lInfo: §rOn applied increases the base attack by 1.");
        addTrans("tooltip.attack_adults_upgrade.canapply", "§2§lCan apply on: §rHit.");
        addTrans("tooltip.attack_adults_upgrade.info", "§2§lInfo: §rOn applied only attacks adult mobs.");
        addTrans("tooltip.energymodeupgrade.canapply", "§2§lCan apply on: §rAll.");
        addTrans("tooltip.energymodeupgrade.info", "§2§lInfo: §rOn applied changes the mode from Redstone to Energy.");
        addTrans("tooltip.silktouchupgrade.canapply", "§2§lCan apply on: §rBreaker.");
        addTrans("tooltip.silktouchupgrade.info", "§2§lInfo: §rApply Silk Touch enchantment.");
        //info overlay
        addTrans("infooverlay.energy", "§3§lEnergy: §r");
        addTrans("infooverlay.energycost", "§3§lEnergy Cost: §r");
        addTrans("infooverlay.cooldown", "§3§lCooldown: §r");
        addTrans("infooverlay.upgrades", "§3§lUpgrades: §r");
        addTrans("infooverlay.worktime", "§3§lWork time: §r");
        addTrans("infooverlay.silktouch", "§3§lSilk Touch Mode: §r");
        addTrans("infooverlay.sneak", "Sneak to see the info!");
        addTrans("infooverlay.placer.needchest", "The placer needs an inventory above with blocks.");
        addTrans("infooverlay.breaker.canbreakinv", "Can't break blocks with inventories");
        addTrans("infooverlay.breaker.canbreakte", "Can't break blocks with tile entities");
        addTrans("infooverlay.working", "§3§lWorking: §r");
        addTrans("infooverlay.hit.damage", "§3§lDamage: §r");
        addTrans("infooverlay.hit.onlyadults", "§3§lAttacking only adults!");
        addTrans("infooverlay.hit.morethanone", "§eWarn: More than one Item Frame will not affect the attack.");
        addTrans("infooverlay.planter.needchest", "The planter needs an inventory above with seeds");
        addTrans("infooverlay.planter.needwater", "Place water below in order to work.");
    }

    public void addTrans(String key, String trans) {
        if (data.put(key, trans) != null)
            throw new IllegalStateException("Duplicate translation key " + key);
    }
}
