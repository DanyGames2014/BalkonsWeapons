package net.danygames2014.balkonsweapons.item;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BlowgunDartItem extends TemplateItem {
    public BlowgunDartItem(Identifier identifier) {
        super(identifier);
    }

    public static float[] getColorGl(ItemStack stack) {
        if(!stack.getStationNbt().contains("color")) {
            stack.getStationNbt().putInt("color", 0x3DC74A);
        }
        int color = stack.getStationNbt().getInt("color");

        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        return new float[] { r, g, b };
    }

    public static int getColor(ItemStack stack) {
        if(!stack.getStationNbt().contains("color")) {
            stack.getStationNbt().putInt("color", 0x3DC74A);
        }
        return stack.getStationNbt().getInt("color");
    }
}
