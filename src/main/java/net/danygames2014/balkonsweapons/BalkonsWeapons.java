package net.danygames2014.balkonsweapons;

import net.danygames2014.balkonsweapons.api.*;
import net.danygames2014.balkonsweapons.item.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class BalkonsWeapons {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;
    
    public static Item woodenSpear;
    public static Item stoneSpear;
    public static Item ironSpear;
    public static Item goldenSpear;
    public static Item diamondSpear;
    public static Item woodenHalberd;
    public static Item stoneHalberd;
    public static Item ironHalberd;
    public static Item goldenHalberd;
    public static Item diamondHalberd;
    public static Item woodenBattleaxe;
    public static Item stoneBattleaxe;
    public static Item ironBattleaxe;
    public static Item goldenBattleaxe;
    public static Item diamondBattleaxe;
    public static Item woodenKnife;
    public static Item stoneKnife;
    public static Item ironKnife;
    public static Item goldenKnife;
    public static Item diamondKnife;
    public static Item woodenWarhammer;
    public static Item stoneWarhammer;
    public static Item ironWarhammer;
    public static Item goldenWarhammer;
    public static Item diamondWarhammer;
    public static Item woodenFlail;
    public static Item stoneFlail;
    public static Item ironFlail;
    public static Item goldenFlail;
    public static Item diamondFlail;
    public static Item woodenKatana;
    public static Item stoneKatana;
    public static Item ironKatana;
    public static Item goldenKatana;
    public static Item diamondKatana;
    public static Item woodenBoomerang;
    public static Item stoneBoomerang;
    public static Item ironBoomerang;
    public static Item goldenBoomerang;
    public static Item diamondBoomerang;
    public static Item fireRod;
    public static Item javelin;
    public static Item heavyCrossbow;
    public static Item crossbowBolt;
    public static Item blowgun;
    public static Item blowgunDart;
    public static Item woodenBayonetMusket;
    public static Item stoneBayonetMusket;
    public static Item ironBayonetMusket;
    public static Item goldenBayonetMusket;
    public static Item diamondBayonetMusket;
    public static Item musket;
    public static Item musketBarrel;
    public static Item musketRound;
    public static Item blunderbuss;
    public static Item blunderbussBarrel;
    public static Item blunderbussShot;
    public static Item handMortar;
    public static Item mortarBarrel;
    public static Item mortarShell;
    public static Item gunStock;
    public static Item flintlockPistol;
    public static Item cannon;
    public static Item cannonBall;
    public static Item dynamite;
    public static Item trainingDummy;

    public static Item testItem;
    public static Item testItem2;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // will move to a more proper location
        UseActions.NONE = new NoneUseAction(NAMESPACE.id("none"));
        UseActions.BOW = new BowUseAction(NAMESPACE.id("bow"));
        UseActions.BLOCK = new BlockUseAction(NAMESPACE.id("block"));
        UseActions.EAT = new EatUseAction(NAMESPACE.id("eat"));
        UseActions.DRINK = new DrinkUseAction(NAMESPACE.id("drink"));

        testItem = new TestItem(NAMESPACE.id("test")).setTranslationKey(NAMESPACE, "test_item");

        woodenSpear = new SpearItem(NAMESPACE.id("wooden_spear"), ToolMaterial.WOOD);
        stoneSpear = new SpearItem(NAMESPACE.id("stone_spear"), ToolMaterial.STONE);
        ironSpear = new SpearItem(NAMESPACE.id("iron_spear"), ToolMaterial.IRON);
        goldenSpear = new SpearItem(NAMESPACE.id("golden_spear"), ToolMaterial.GOLD);
        diamondSpear = new SpearItem(NAMESPACE.id("diamond_spear"), ToolMaterial.DIAMOND);

        woodenBoomerang = new BoomerangItem(NAMESPACE.id("wooden_boomerang"), ToolMaterial.WOOD);
        stoneBoomerang = new BoomerangItem(NAMESPACE.id("stone_boomerang"), ToolMaterial.STONE);
        ironBoomerang = new BoomerangItem(NAMESPACE.id("iron_boomerang"), ToolMaterial.IRON);
        goldenBoomerang = new BoomerangItem(NAMESPACE.id("golden_boomerang"), ToolMaterial.GOLD);
        diamondBoomerang = new BoomerangItem(NAMESPACE.id("diamond_boomerang"), ToolMaterial.DIAMOND);

        woodenKnife = new KnifeItem(NAMESPACE.id("wooden_knife"), ToolMaterial.WOOD);
        stoneKnife = new KnifeItem(NAMESPACE.id("stone_knife"), ToolMaterial.STONE);
        ironKnife = new KnifeItem(NAMESPACE.id("iron_knife"), ToolMaterial.IRON);
        goldenKnife = new KnifeItem(NAMESPACE.id("golden_knife"), ToolMaterial.GOLD);
        diamondKnife = new KnifeItem(NAMESPACE.id("diamond_knife"), ToolMaterial.DIAMOND);

        woodenKatana = new KatanaItem(NAMESPACE.id("wooden_katana"), ToolMaterial.WOOD);
        stoneKatana = new KatanaItem(NAMESPACE.id("stone_katana"), ToolMaterial.STONE);
        ironKatana = new KatanaItem(NAMESPACE.id("iron_katana"), ToolMaterial.IRON);
        goldenKatana = new KatanaItem(NAMESPACE.id("golden_katana"), ToolMaterial.GOLD);
        diamondKatana = new KatanaItem(NAMESPACE.id("diamond_katana"), ToolMaterial.DIAMOND);

        fireRod = new FireRodItem(NAMESPACE.id("fire_rod"));
        dynamite = new DynamiteItem(NAMESPACE.id("dynamite"));
        javelin = new JavelinItem(NAMESPACE.id("javelin"));
        trainingDummy = new DummyItem(NAMESPACE.id("training_dummy"));

        cannon = new CannonItem(NAMESPACE.id("cannon"));

        blunderbuss = new BlunderbussItem(NAMESPACE.id("blunderbuss"));
        handMortar = new MortarItem(NAMESPACE.id("hand_mortar"));
        flintlockPistol = new FlintlockItem(NAMESPACE.id("flintlock_pistol"));
        heavyCrossbow = new CrossbowItem(NAMESPACE.id("heavy_crossbow"));
        blowgun = new BlowgunItem(NAMESPACE.id("blowgun"));

        musket = new MusketItem(NAMESPACE.id("musket"));
        woodenBayonetMusket = new MusketItem(NAMESPACE.id("wooden_bayonet_musket"), ToolMaterial.WOOD);
        stoneBayonetMusket = new MusketItem(NAMESPACE.id("stone_bayonet_musket"), ToolMaterial.STONE);
        ironBayonetMusket = new MusketItem(NAMESPACE.id("iron_bayonet_musket"), ToolMaterial.IRON);
        goldenBayonetMusket = new MusketItem(NAMESPACE.id("golden_bayonet_musket"), ToolMaterial.GOLD);
        diamondBayonetMusket = new MusketItem(NAMESPACE.id("diamond_bayonet_musket"), ToolMaterial.DIAMOND);

        blunderbussShot = new TemplateItem(NAMESPACE.id("blunderbuss_shot"));
        mortarShell = new TemplateItem(NAMESPACE.id("mortar_shell"));
        cannonBall = new TemplateItem(NAMESPACE.id("cannon_ball"));
        musketRound = new TemplateItem(NAMESPACE.id("musket_round"));
        crossbowBolt = new TemplateItem(NAMESPACE.id("crossbow_bolt"));
        blowgunDart = new BlowgunDartItem(NAMESPACE.id("blowgun_dart"));

        ((BlowgunDartItem)blowgunDart).addDartType(NAMESPACE.id("slowness"), 360, 0xA6FFE3);

        testItem2 = new TestItem(NAMESPACE.id("test2")).setTranslationKey(NAMESPACE, "test_item2");
    }
}
