package net.danygames2014.balkonsweapons.api;

import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class DrinkUseAction extends EatUseAction{
    public DrinkUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {
        if(time <= stack.getMaxUseDuration() - 5 && time % 4 == 0 || finished) {
            SoundHelper.playSound(player, "balkonsweapons:random.drink", 0.5F, this.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
