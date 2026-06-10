package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.item.component.MeleeComponent;
import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class MeleeItem extends TemplateSwordItem implements WeaponItem {
    public final MeleeComponent meleeComponent;

    public MeleeItem(Identifier identifier, MeleeComponent meleeComponent) {
        super(identifier, meleeComponent.weaponMaterial == null ? ToolMaterial.WOOD : meleeComponent.weaponMaterial);
        (this.meleeComponent = meleeComponent).setItem(this);
        meleeComponent.setItemProperties();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return meleeComponent.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean postMine(ItemStack stack, int blockId, int x, int y, int z, LivingEntity miner) {
        return meleeComponent.onBlockDestroyed(stack, miner.world, miner.world.getBlockState(x, y, z), x, y, z, miner);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        return meleeComponent.onItemRightClick(world, user, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        meleeComponent.onUpdate(stack, world, entity, slot, selected);
    }

    @Override
    public Random getItemRandom() {
        return random;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return null;
    }
}
