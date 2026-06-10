package net.danygames2014.balkonsweapons.entity.projectile;

import net.minecraft.item.ItemStack;

public interface CustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    float[] getColorFromMaterialID(int materialId);
}
