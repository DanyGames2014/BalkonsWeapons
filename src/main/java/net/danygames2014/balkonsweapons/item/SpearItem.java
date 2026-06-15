package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.entity.projectile.SpearEntity;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.item.CustomReachProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class SpearItem extends MeleeItem implements CustomReachProvider {
    public SpearItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.SPEAR, toolMaterial);
        setMaxCount(1);
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return UseActions.NONE;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (stack == null) {
            return stack;
        }
        if (false) { // !BalkonsWeaponMod.instance.modConfig.canThrowSpear
            return super.use(stack, world, user);
        }
        if(!world.isRemote) {
            SpearEntity spearEntity = new SpearEntity(world, user, stack.copy());
            spearEntity.setAim(user, user.pitch, user.yaw, 0.0f, 0.8f, 3.0f);
            world.spawnEntity(spearEntity);
        }

        SoundHelper.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));

        if (true) { // creative check
            stack = stack.copy();
            stack.split(1);
        }

        return stack;
    }

    @Override
    public double getReach(ItemStack stack, PlayerEntity player, HitResultType type, double currentReach) {
        return 4;
    }
}
