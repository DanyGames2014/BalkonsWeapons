package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.ReloadHelper;
import net.danygames2014.balkonsweapons.entity.projectile.MusketBulletEntity;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MusketItem extends RangedItem implements CustomTooltipProvider {

    private final int bayonetDurability;
    public MusketItem(Identifier identifier) {
        super(identifier, RangedSpecs.MUSKET);
        this.bayonetDurability = 0;
    }

    public MusketItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.KNIFE, toolMaterial, RangedSpecs.MUSKET);
        this.bayonetDurability = toolMaterial == null
                                         ? meleeSpecs.durabilityBase
                                         : (int) (meleeSpecs.durabilityBase
                                                          + toolMaterial.getDurability() * meleeSpecs.durabilityMult);
    }

    @Override
    public void effectReloadDone(ItemStack stack, World world, LivingEntity player) {
//        entityliving.swingItem();
        SoundHelper.playSound(world, player.x, player.y, player.z, "random.click", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void fire(ItemStack stack, World world, LivingEntity entity, int i) {
        int j = MAX_DELAY;
        if(entity instanceof PlayerEntity player) {
            j = getMaxUseDuration(stack, world, player) - player.getItemInUseDuration();
        }
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            MusketBulletEntity musketBulletEntity = new MusketBulletEntity(world, entity);
            musketBulletEntity.setAim(entity, entity.pitch, entity.yaw, 0.0f, 5.0f, 1.0f / f);
            world.spawnEntity(musketBulletEntity);
        }
        int deltaDamage = 1;
        boolean flag = stack.getDamage() + deltaDamage >= stack.getMaxDamage();
        if (flag && meleeSpecs != null) {
            int bayonetDamage = stack.getStationNbt().contains("bayonetDamage") ? stack.getStationNbt().getShort("bayonetDamage") : 0;
            ItemStack newStack = new ItemStack(KnifeItem.ALL_KNIFES.get(toolMaterial), 1, bayonetDamage);
            if (entity instanceof PlayerEntity player) {
                ItemUtil.damageItem(stack, deltaDamage, player);
                player.inventory.addStack(newStack);
            }
        } else {
            if (entity instanceof PlayerEntity player) {
                ItemUtil.damageItem(stack, deltaDamage, player);
            }
            setReloadState(stack, ReloadState.STATE_NONE);
        }
        postShootingEffects(stack, entity, world);
    }

    @Override
    public void effectPlayer(ItemStack stack, PlayerEntity player, World world) {
        float f = player.isSneaking() ? -0.05f : -0.1f;
        double d = -MathHelper.sin(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        double d2 = MathHelper.cos(player.yaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        player.pitch -= (player.isSneaking() ? 7.5f : 15.0f);
        player.addVelocity(d, 0.0, d2);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw, float pitch) {
        SoundHelper.playSound(world, x, y, z, "random.explode", 3F, 1F / (random.nextFloat() * 0.4F + 0.7F));
        SoundHelper.playSound(world, x, y, z, "ambient.weather.thunder", 3F, 1F / (random.nextFloat() * 0.4F + 0.4F));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            ParticleHelper.addParticle(world, "smoke", x + particleX, y + particleY, z + particleZ, 0.0, 0.0,
                    0.0);
        }
        ParticleHelper.addParticle(world, "flame", x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public float getMaxZoom() {
        return 0.15F;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(super.postHit(stack, target, attacker)) {
            if(attacker instanceof PlayerEntity player && true) {  //creative check
                bayonetDamage(stack, attacker, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        if(meleeSpecs == null) {
            return 1;
        }
        return (int) getEntityDamage();
    }

    public void bayonetDamage(ItemStack itemstack, LivingEntity entityliving, int damage) {
        int bayonetdamage = itemstack.getStationNbt().getShort("bayonetDamage") + damage;
        if (bayonetdamage > bayonetDurability) {
//            entityliving.renderBrokenItemStack(itemstack);
//            if (entityliving instanceof EntityPlayer)
//                ((EntityPlayer) entityliving).triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this)]);
            bayonetdamage = 0;
            ItemStack itemstack2 = new ItemStack(BalkonsWeapons.musket, 1);
            itemstack2.setDamage(itemstack.getDamage());
            if(entityliving instanceof PlayerEntity player) {
                player.inventory.main[player.inventory.selectedSlot] = itemstack2;
            }
            if (itemstack.getStationNbt().contains("rld")) {
                ReloadHelper.setReloadState(itemstack2, ReloadHelper.getReloadState(itemstack));
            }
        }
        itemstack.getStationNbt().putShort("bayonetDamage", (short) bayonetdamage);
    }

    @Override
    public @NotNull String[] getTooltip(ItemStack itemStack, String s) {
        List<String> tooltip = new ArrayList<>();
        tooltip.add(s);
        int bayonetDamage = itemStack.getStationNbt().getShort("bayonetDamage");
        if(bayonetDamage > 0) {
            int remainingDurability = bayonetDurability - bayonetDamage;
            tooltip.add(String.format("Bayonet durability: %d/%d", remainingDurability, bayonetDurability));
        }
        return tooltip.toArray(new String[0]);
    }
}
