package net.danygames2014.balkonsweapons.entity.projectile;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {
    private static final float[] NO_MATERIAL_COLOR = new float[]{1.0f, 1.0f, 1.0f};
    private static final Map<Integer, CustomProjectileMaterials> CUSTOM_MATERIALS =
            new HashMap<>(ToolMaterial.values().length);

    public static void registerCustomProjectileMaterial(CustomProjectileMaterials customprojectilematerial) {
        int[] allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (int i : allMaterialIDs) {
            CUSTOM_MATERIALS.put(i, customprojectilematerial);
        }
    }

    public static int getMaterialID(ItemStack itemstack) {
        for (CustomProjectileMaterials mat : CUSTOM_MATERIALS.values()) {
            int i = mat.getMaterialID(itemstack);
            if (i >= ToolMaterial.values().length) {
                return i;
            }
        }
        return -1;
    }

    public static float[] getColorFromMaterialID(int id) {
        CustomProjectileMaterials mat = CUSTOM_MATERIALS.get(id);
        if (mat != null) {
            return mat.getColorFromMaterialID(id);
        }
        return MaterialRegistry.NO_MATERIAL_COLOR;
    }
}
