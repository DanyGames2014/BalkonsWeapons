package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.item.BlowgunDartItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

public class ColorProviderListener {
    @EventListener
    public void registerItemColorProviders(ItemColorsRegisterEvent event) {
        event.itemColors.register((stack, layer) -> {
            if(layer == 1) {
                return BlowgunDartItem.getColor(stack);
            }
            return 0xFFFFFF;
        }, BalkonsWeapons.blowgunDart);
    }
}
