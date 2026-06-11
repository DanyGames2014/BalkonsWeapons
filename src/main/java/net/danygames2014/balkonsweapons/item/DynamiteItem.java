package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.projectile.DynamiteEntity;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class DynamiteItem extends TemplateItem {
    public DynamiteItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (true) { // creative check
            --stack.count;
        }
        SoundHelper.playSound(user, "random.fuse", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            DynamiteEntity dynamiteEntity = new DynamiteEntity(world, user, 40 + random.nextInt(10));
            dynamiteEntity.setAim(user, user.pitch, user.yaw, 0.0f, 0.7f, 4.0f);
            world.spawnEntity(dynamiteEntity);
        }
        return stack;
    }
}
