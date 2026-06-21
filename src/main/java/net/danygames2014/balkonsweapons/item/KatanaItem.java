package net.danygames2014.balkonsweapons.item;

import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.util.Identifier;

public class KatanaItem extends MeleeItem{
    public KatanaItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.KATANA, toolMaterial);
    }
}
