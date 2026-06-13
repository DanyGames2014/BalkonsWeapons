package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.entity.projectile.KnifeEntity;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class KnifeItem extends MeleeItem{
    public static Map<ToolMaterial, KnifeItem> ALL_KNIFES = new HashMap<>();

    public KnifeItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.KNIFE, toolMaterial);
        ALL_KNIFES.put(toolMaterial, this);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (stack == null) {
            return stack;
        }
        if (false) { // !BalkonsWeaponMod.instance.modConfig.canThrowKnife
            return super.use(stack, world, user);
        }
        if (!world.isRemote) {
            KnifeEntity knifeEntity = new KnifeEntity(world, user, stack.copy());
            knifeEntity.setAim(user, user.pitch, user.yaw, 0.0f, 0.8f, 3.0f);
            world.spawnEntity(knifeEntity);
        }
        SoundHelper.playSound(user, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (true) { // creative check
            stack = stack.copy();
            stack.split(1);
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return true ? UseActions.NONE : super.getUseAction(stack, world, player); // BalkonsWeaponMod.instance.modConfig.canThrowKnife
    }
}
