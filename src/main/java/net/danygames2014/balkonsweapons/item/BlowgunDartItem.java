package net.danygames2014.balkonsweapons.item;

import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.effect.EntityEffectType;
import net.modificationstation.stationapi.api.effect.EntityEffectTypeRegistry;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlowgunDartItem extends TemplateItem implements CustomTooltipProvider {
    private final List<ItemStack> ALL_DARTS = new ArrayList<>();

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

    public void addDartType(Identifier effect, int duration, int color) {
        ItemStack type = new ItemStack(this);
        type.getStationNbt().putString("effect", effect.toString());
        type.getStationNbt().putInt("duration", duration);
        type.getStationNbt().putInt("color", color);
        ALL_DARTS.add(type);
    }

    @SubItemProvider
    public List<ItemStack> getSubItems() {
        return ALL_DARTS;
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(s);

        if(itemStack != null  && itemStack.getStationNbt().contains("effect") && itemStack.getStationNbt().contains("duration")) {
            Identifier effectId = Identifier.of(itemStack.getStationNbt().getString("effect"));
            int duration = itemStack.getStationNbt().getInt("duration");
            tooltip.add("§c" + TranslationStorage.getInstance().get("gui.stationapi.effect." + effectId.namespace + "." + effectId.path + ".name") + " (" + formatEffectTime(duration) + ")");
        }

        return tooltip.toArray(new String[0]);
    }

    private static String formatEffectTime(int totalTicks) {
        int totalSeconds = totalTicks / 20;

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
}
