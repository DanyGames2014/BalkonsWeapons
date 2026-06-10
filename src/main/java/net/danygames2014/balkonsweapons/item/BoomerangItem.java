package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BoomerangItem extends TemplateItem implements ItemWithHold {
    public BoomerangItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public int getMaxUseDuration(ItemStack stack, World world, PlayerEntity player) {
        return 0;
    }

    @Override
    public void startUsing(ItemStack stack, World world, PlayerEntity player) {

    }

    @Override
    public void usingTick(ItemStack stack, World world, PlayerEntity player, int time) {

    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return null;
    }
}
