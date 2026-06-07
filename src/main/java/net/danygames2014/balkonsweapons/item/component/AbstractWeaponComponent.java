package net.danygames2014.balkonsweapons.item.component;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public abstract class AbstractWeaponComponent {
    public Item item;
    public WeaponItem weapon;

    public AbstractWeaponComponent() {
        item = null;
        weapon = null;
    }

    public void setItem(WeaponItem weapon) {
        if(weapon instanceof Item item) {
            this.item = item;
        }
        this.weapon = weapon;
    }

    protected abstract void onSetItem();

    public abstract void setItemProperties();

    public abstract float getMaterialPartEntityDamage();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(ItemStack stack, BlockState blockState);

    public abstract boolean canHarvestBlock(BlockState blockState);

    public abstract boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, int x, int y, int z, PlayerEntity player);

    public abstract boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, int x, int y, int z, LivingEntity livingEntity);

    public abstract boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker);

    public abstract float getAttackDelay(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker);

    public abstract float getKnockback(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker);

    public abstract boolean onEntityLeftClick(ItemStack itemStack, PlayerEntity player, Entity entity);

    public abstract ItemStack onItemRightClick(World world, PlayerEntity player, ItemStack itemStack);

    public abstract void onUsingTick(ItemStack itemStack, LivingEntity livingEntity, int count);

    public abstract void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int i);

    public abstract void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean flag);

    public abstract boolean shouldRotateAroundWhenRendering();

    public abstract boolean shouldRenderCooldown();

    public abstract float getCooldown();
}
