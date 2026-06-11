package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.entity.projectile.JavelinEntity;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class JavelinItem extends TemplateItem implements ItemWithHold {
    public JavelinItem(Identifier identifier) {
        super(identifier);
        setMaxCount(16);
    }


    @Override
    public int getMaxUseDuration(ItemStack stack, World world, PlayerEntity player) {
        return 72000;
    }

    @Override
    public void startUsing(ItemStack stack, World world, PlayerEntity player) {

    }

    @Override
    public void usingTick(ItemStack stack, World world, PlayerEntity player, int time) {

    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        int j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return false;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = !player.onGround && !player.isSubmergedInWater();
        if (!world.isRemote) {
            JavelinEntity javelinEntity = new JavelinEntity(world, player);
            javelinEntity.setAim(player, player.pitch, player.yaw, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            javelinEntity.setIsCritical(crit);
            world.spawnEntity(javelinEntity);
        }
        SoundHelper.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (true) { // creative check
            ItemUtil.decreaseStackCount(stack, 1, player);
        }
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return UseActions.BOW;
    }
}
