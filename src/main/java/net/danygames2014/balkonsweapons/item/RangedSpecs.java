package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.config.Config;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RangedSpecs {
    BLOWGUN("blowgun", 250, "dart"),
    CROSSBOW("crossbow", 250, "bolt"),
    MUSKET("musket", 80, "bullet"),
    BLUNDERBUSS("blunderbuss", 80, "blunderbuss_shot"),
    FLINTLOCK("flintlock", 8, "bullet"),
    MORTAR("mortar", 40, "mortar_shell");

    private int reloadTime;
    private List<Item> ammoItems;
    private final String[] ammoItemTags;
    public final String reloadTimeTag;
    public final int durability;

    RangedSpecs(String reloadtimetag, int durability, String... ammoitemtags) {
        ammoItemTags = ammoitemtags;
        reloadTimeTag = reloadtimetag;
        this.durability = durability;
        ammoItems = null;
        reloadTime = -1;
    }

    public int getReloadTime() {
        if (reloadTime < 0) {
            reloadTime = Config.RELOAD_CONFIG.getReloadTime(BalkonsWeapons.NAMESPACE.id(reloadTimeTag));
        }
        return reloadTime;
    }

    public List<Item> getAmmoItems() {
        if (ammoItems == null) {
            ammoItems = Arrays.stream(ammoItemTags)
                                .map(path -> BalkonsWeapons.NAMESPACE.id(path))
                                .map(ItemRegistry.INSTANCE::get)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
            BalkonsWeapons.LOGGER.debug("Found items {} for {} @{}", ammoItems, Arrays.toString(ammoItemTags), this);
        }

        return ammoItems;
    }
}
