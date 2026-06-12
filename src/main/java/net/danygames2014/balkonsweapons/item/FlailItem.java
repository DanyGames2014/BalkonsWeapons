package net.danygames2014.balkonsweapons.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class FlailItem extends TemplateItem {

    public FlailItem(Identifier identifier) {
        super(identifier);
    }

    public float getFlailDamage() {
        return 1.0f;
    }
}
