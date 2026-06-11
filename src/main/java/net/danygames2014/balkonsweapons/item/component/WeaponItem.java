package net.danygames2014.balkonsweapons.item.component;

import net.danygames2014.balkonsweapons.item.MeleeSpecs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.Random;

public interface WeaponItem {
    Random getItemRandom();

    MeleeSpecs getMeleeSpecs();

    ToolMaterial getToolMaterial();

    float getEntityDamage();
    float getBlockDamage();
    float getMaterialDamage();

    void setItemProperties();
    float getKnockback(ItemStack stack, LivingEntity target, LivingEntity source);
    float getAttackDelay(ItemStack stack, LivingEntity target, LivingEntity source);


}
