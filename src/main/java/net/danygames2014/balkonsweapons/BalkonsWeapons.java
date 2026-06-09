package net.danygames2014.balkonsweapons;

import net.danygames2014.balkonsweapons.api.*;
import net.danygames2014.balkonsweapons.item.TestItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
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
    public static Item poisonousDart;
    public static Item musketWoodenBayonet;
    public static Item musketStoneBayonet;
    public static Item musketIronBayonet;
    public static Item musketGoldenBayonet;
    public static Item musketDiamondBayonet;
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

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        // will move to a more proper location
        UseActions.NONE = new NoneUseAction(NAMESPACE.id("none"));
        UseActions.BOW = new BowUseAction(NAMESPACE.id("bow"));
        UseActions.BLOCK = new BlockUseAction(NAMESPACE.id("block"));

        testItem = new TestItem(NAMESPACE.id("test")).setTranslationKey(NAMESPACE, "test_item");
    }
}
