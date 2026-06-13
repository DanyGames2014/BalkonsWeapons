package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.ReloadHelper;
import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class RangedItem extends MeleeItem{
    protected static final int MAX_DELAY = 72000;
    public final RangedSpecs rangedSpecs;

    public static boolean isReloaded(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack).isReloaded();
    }

    public static boolean isReadyToFire(ItemStack itemstack) {
        return ReloadHelper.getReloadState(itemstack) == ReloadState.STATE_READY;
    }

    public static void setReloadState(ItemStack itemstack, ReloadState state) {
        ReloadHelper.setReloadState(itemstack, state);
    }

    public RangedItem(Identifier identifier, RangedSpecs rangedSpecs) {
        super(identifier, null, null);
        this.rangedSpecs = rangedSpecs;
        setItemProperties();
    }

    public RangedItem(Identifier identifier, MeleeSpecs meleeSpecs, ToolMaterial toolMaterial, RangedSpecs rangedSpecs) {
        super(identifier, meleeSpecs, toolMaterial);
        this.rangedSpecs = rangedSpecs;
        setItemProperties();
    }

    @Override
    public void setItemProperties() {
        setMaxDamage(this.rangedSpecs.durability);
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return 1;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        ReloadState state = ReloadHelper.getReloadState(stack);
        if (state == ReloadState.STATE_NONE) {
            return UseActions.BLOCK;
        }
        if (state == ReloadState.STATE_READY) {
            return UseActions.BOW;
        }
        return UseActions.NONE;
    }

    @Override
    public int getMaxUseDuration(ItemStack stack, World world, PlayerEntity player) {
        return MAX_DELAY;
    }

    @Override
    public boolean attemptHold(ItemStack stack, World world, PlayerEntity player) {
        if (!hasAmmo(stack, world, player)) {
            soundEmpty(stack, world, player);
            setReloadState(stack, ReloadState.STATE_NONE);
            return false;
        }
        if (isReadyToFire(stack)) {
            soundCharge(stack, world, player);
            player.setItemInUse(stack, getMaxUseDuration(stack, world, player));
            return true;
        }
        player.setItemInUse(stack, getMaxUseDuration(stack, world, player));
        return true;
    }

    @Override
    public void usingTick(ItemStack stack, World world, PlayerEntity player, int time) {
        if (ReloadHelper.getReloadState(stack) == ReloadState.STATE_NONE && getMaxUseDuration(stack, world, player) - player.getItemInUseDuration() >= getReloadDuration(stack)) {
            effectReloadDone(stack, player.world, player);
            setReloadState(stack, ReloadState.STATE_RELOADED);
        }
    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        if (!isReloaded(stack)) {
            return false;
        }
        if (isReadyToFire(stack)) {
            if (hasAmmoAndConsume(stack, world, player)) {
                fire(stack, world, player, player.getItemInUseDuration());
            }
            setReloadState(stack, ReloadState.STATE_NONE);
        } else {
            setReloadState(stack, ReloadState.STATE_READY);
        }
        return false;
    }

    public void soundEmpty(ItemStack itemstack, World world, PlayerEntity player) {
        SoundHelper.playSound(player, "random.click", 1.0f, 1.25f);
    }

    public void soundCharge(ItemStack itemstack, World world, PlayerEntity player) {
    }

    public void postShootingEffects(ItemStack itemstack, LivingEntity livingEntity, World world) {
        if (livingEntity instanceof PlayerEntity player) {
            effectPlayer(itemstack, player, world);
        }
        effectShoot(world, livingEntity.x, livingEntity.y, livingEntity.z, livingEntity.yaw, livingEntity.pitch);
    }

    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {

    }

    public void fire(ItemStack stack, World world, LivingEntity entity, int i) {

    }

    public void effectPlayer(ItemStack stack, PlayerEntity player, World world) {

    }

    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {

    }

    public int getReloadDuration(ItemStack itemstack) {
        return rangedSpecs.getReloadTime();
    }

    public List<Item> getAmmoItems() {
        return rangedSpecs.getAmmoItems();
    }

    protected ItemStack findAmmo(PlayerEntity player) {
        int slot = ItemUtil.findAnyItemSlot(player, getAmmoItems());
        if (slot < 0) return null;
        return player.inventory.main[slot];
    }

    protected boolean consumeAmmo(PlayerEntity player) {
        return ItemUtil.consumeAnyInventoryItem(player, getAmmoItems());
    }

    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, LivingEntity livingEntity) {
        if (!(livingEntity instanceof PlayerEntity player)) return true;
        return consumeAmmo(player); // original has creative and enchant checks here
    }

    public boolean hasAmmo(ItemStack itemstack, World world, PlayerEntity player) {
        boolean flag = findAmmo(player) != null;
        return flag; // original has creative and enchant checks here
    }

    // TODO: figure out how to use this on the bow UseAction
    public float getFOVMultiplier(int ticksinuse) {
        float f1 = ticksinuse / getMaxAimTimeTicks();
        if (f1 > 1.0f) {
            f1 = 1.0f;
        } else {
            f1 *= f1;
        }
        return 1.0f - f1 * getMaxZoom();
    }

    protected float getMaxAimTimeTicks() {
        return 20.0f;
    }

    protected float getMaxZoom() {
        return 0.15f;
    }
}
