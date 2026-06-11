package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.entity.projectile.BoomerangEntity;
import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BoomerangItem extends MeleeItem {
    public BoomerangItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.BOOMERANG, toolMaterial);
    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        if(stack == null) {
            return false;
        }

        int j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();

        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return false;
        }
        boolean crit = false;
        if (f > 1.5f) {
            f = 1.5f;
            crit = true;
        }
        f *= 1.5f;
        if (!world.isRemote) {
            BoomerangEntity boomerang = new BoomerangEntity(world, player, stack.copy());
            boomerang.setAim(player, player.pitch, player.yaw, 0.0f, f, 5.0f);
            boomerang.setIsCritical(crit);
            world.spawnEntity(boomerang);
        }
        SoundHelper.playSound(player, "random.bow", 0.6F,
                1.0F / (getItemRandom().nextFloat() * 0.4F + 1.0F));
        if(true) { // Creative check
            ItemUtil.decreaseStackCount(stack, 1, player);
        }

        return false;
    }
}
