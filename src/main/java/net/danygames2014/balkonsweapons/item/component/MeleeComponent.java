package net.danygames2014.balkonsweapons.item.component;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public class MeleeComponent extends AbstractWeaponComponent{

    public final MeleeSpecs meleeSpecs;
    public final ToolMaterial weaponMaterial;

    public MeleeComponent(MeleeSpecs meleeSpecs, ToolMaterial toolMaterial) {
        this.meleeSpecs = meleeSpecs;
        this.weaponMaterial = toolMaterial;
    }

    @Override
    protected void onSetItem() {
    }

    @Override
    public void setItemProperties() {
        item.setMaxDamage(weaponMaterial == null ? meleeSpecs.durabilityBase : (int) (meleeSpecs.durabilityBase + weaponMaterial.getDurability() + meleeSpecs.durabilityMult));
    }

    @Override
    public float getMaterialPartEntityDamage() {
        return weaponMaterial.getAttackDamage() * meleeSpecs.damageMult;
    }

    @Override
    public float getEntityDamage() {
        return meleeSpecs.damageBase + getMaterialPartEntityDamage();
    }

    @Override
    public float getBlockDamage(ItemStack stack, BlockState blockState) {
        if( canHarvestBlock(blockState)) {
            return meleeSpecs.blockDamage;
        }
        Material material = blockState.getMaterial();
        return (material != Material.PLANT && material != Material.LEAVES) ? 1.0f : meleeSpecs.blockDamage;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockState) {
        return blockState.getBlock() == Block.COBWEB;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, int x, int y, int z, LivingEntity livingEntity) {
        if (blockState.getBlock().getHardness() != 0.0f) {
//            WMItem.damageItem(itemstack, meleeSpecs.dmgFromBlock, entityliving);
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker) {
//        if (livingEntity.attackCooldown == livingEntity.maxHurtResistantTime) {
//            float kb = getKnockBack(itemstack, entityliving, attacker);
//            PhysHelper.knockBack(entityliving, attacker, kb);
//            if (meleeSpecs.attackDelay >= 3.0f) {
//                entityliving.hurtResistantTime += (int) getAttackDelay(itemstack, entityliving, attacker);
//            } else {
//                float f = (meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
//                entityliving.hurtResistantTime -= (int) (f / getAttackDelay(itemstack, entityliving, attacker));
//            }
//        }
//        WMItem.damageItem(itemstack, meleeSpecs.dmgFromEntity, attacker);
        return true;
    }

    @Override
    public float getAttackDelay(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker) {
        return meleeSpecs.attackDelay;
    }

    @Override
    public float getKnockback(ItemStack itemStack, LivingEntity livingEntity, LivingEntity attacker) {
        return meleeSpecs.getKnockBack(weaponMaterial);
    }

    @Override
    public boolean onEntityLeftClick(ItemStack itemStack, PlayerEntity player, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
//            PhysHelper.prepareKnockbackOnEntity(player, (EntityLivingBase) entity);
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(World world, PlayerEntity player, ItemStack itemStack) {
//        if (getItemUseAction(itemstack) != EnumAction.none)
//            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemStack;
    }

    @Override
    public void onUsingTick(ItemStack itemStack, LivingEntity livingEntity, int count) {

    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int i) {

    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int i, boolean flag) {

    }

    @Override
    public boolean shouldRotateAroundWhenRendering() {
        return false;
    }

    @Override
    public boolean shouldRenderCooldown() {
        return false;
    }

    @Override
    public float getCooldown() {
        return 0;
    }

    public enum MeleeSpecs {
        SPEAR(0, 1.0f, 3.0f, 1.0f, 1.0f, 0.2f, 1, 2, 2.7f),
        HALBERD(0, 1.0f, 4.0f, 1.0f, 1.5f, 0.6f, 1, 2, 3.2f),
        BATTLEAXE(0, 1.0f, 3.0f, 1.0f, 1.5f, 0.5f, 1, 2, 3.0f),
        WARHAMMER(0, 1.0f, 4.0f, 1.0f, 1.0f, 0.7f, 1, 2, 3.0f),
        KNIFE(0, 0.5f, 3.0f, 1.0f, 1.5f, 0.2f, 1, 2, 2.0f),
        KATANA(0, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1, 2, 0.2f),
        FIREROD(1, 0.0f, 1.0f, 0.0f, 1.0f, 0.4f, 2, 0, 0.0f),
        BOOMERANG(0, 0.5f, 2.0f, 1.0f, 1.0f, 0.4f, 1, 1, 2.0f),
        NONE(0, 1.0f, 1.0f, 0.0f, 1.0f, 0.4f, 0, 0, 0.0f);

        public final int durabilityBase;
        public final float durabilityMult;
        public final float damageBase;
        public final float damageMult;
        public final float blockDamage;
        public final float knockback;
        public final float attackDelay;
        public final int dmgFromEntity;
        public final int dmgFromBlock;

        MeleeSpecs(int durbase, float durmult, float dmgbase, float dmgmult, float blockdmg, float knockback, int dmgfromentity, int dmgfromblock, float attackdelay) {
            durabilityBase = durbase;
            durabilityMult = durmult;
            damageBase = dmgbase;
            damageMult = dmgmult;
            blockDamage = blockdmg;
            this.knockback = knockback;
            dmgFromEntity = dmgfromentity;
            dmgFromBlock = dmgfromblock;
            attackDelay = attackdelay;
        }

        public float getKnockBack(final ToolMaterial material) {
            return (material == ToolMaterial.GOLD) ? (knockback * 1.5f) : knockback;
        }
    }
}
