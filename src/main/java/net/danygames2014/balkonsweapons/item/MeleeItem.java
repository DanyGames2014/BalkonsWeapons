package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.item.component.MeleeComponent;
import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.danygames2014.balkonsweapons.util.ItemUtil;
import net.danygames2014.balkonsweapons.util.PhysHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class MeleeItem extends TemplateItem implements WeaponItem, ItemWithHold {

    public MeleeSpecs meleeSpecs;
    public ToolMaterial toolMaterial;
    protected TagKey<Block> mineableTag;

    public MeleeItem(Identifier identifier, MeleeSpecs meleeSpecs, ToolMaterial toolMaterial) {
        super(identifier);
        this.meleeSpecs = meleeSpecs;
        this.toolMaterial = toolMaterial;
        if(meleeSpecs != null && !(this instanceof RangedItem)) {
            setItemProperties();
        }
        setMaxCount(1);
        mineableTag = TagKey.of(BlockRegistry.KEY, Identifier.of("minecraft:mineable/sword"));
    }

    @Override
    public boolean preHit(ItemStack stack, Entity otherEntity, PlayerEntity player) {
        if(otherEntity instanceof LivingEntity livingEntity) {
            PhysHelper.prepareKnockbackOnEntity(player, livingEntity);

        }
        return true;
    }

    // TODO: hack, need some other way to delay the player from attacking
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(meleeSpecs == null) {
            return false;
        }
        if(attacker.attackCooldown == 0) {
            float kb = getKnockback(stack, target, attacker);
            PhysHelper.knockback(target, attacker, kb);
            if (meleeSpecs.attackDelay >= 3.0f) {
                attacker.attackCooldown += (int) getAttackDelay(stack, target, attacker);
            } else {
                float f = (meleeSpecs.attackDelay < 1.0f) ? 1.2f : 2.0f;
                attacker.attackCooldown -= (int) (f / getAttackDelay(stack, target, attacker));
            }
        }
        if(attacker instanceof PlayerEntity player && !(this instanceof RangedItem)) {
            ItemUtil.damageItem(stack, meleeSpecs.dmgFromEntity, player);
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(PlayerEntity player, ItemStack itemStack, BlockView blockView, BlockPos blockPos, BlockState state) {
        return state.isIn(mineableTag);
    }

    @Override
    public float getMiningSpeedMultiplier(PlayerEntity player, ItemStack itemStack, BlockView blockView, BlockPos blockPos, BlockState state) {
        if(meleeSpecs == null) {
            return 1.0F;
        }
        if(isSuitableFor(player, itemStack, blockView, blockPos, state)) {
            return meleeSpecs.blockDamage * 10.0F;
        }
        return 1.0F;
    }


    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return (int) getEntityDamage();
    }

    @Override
    public Random getItemRandom() {
        return random;
    }

    @Override
    public MeleeSpecs getMeleeSpecs() {
        return meleeSpecs;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    @Override
    public float getEntityDamage() {
        if(meleeSpecs == null) {
            return 0.0F;
        }
        return meleeSpecs.damageBase + getMaterialDamage();
    }

    @Override
    public float getBlockDamage() {
        return 0;
    }

    @Override
    public float getMaterialDamage() {
        if(toolMaterial == null || meleeSpecs == null) {
            return 0.0F;
        }
        return toolMaterial.getAttackDamage() * meleeSpecs.damageMult;
    }

    @Override
    public void setItemProperties() {
        setMaxDamage(toolMaterial == null
                             ? meleeSpecs.durabilityBase
                             : (int) (meleeSpecs.durabilityBase
                                              + toolMaterial.getDurability() * meleeSpecs.durabilityMult));
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
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return UseActions.BLOCK;
    }

    @Override
    public float getKnockback(ItemStack stack, LivingEntity target, LivingEntity source) {
        if(meleeSpecs == null) {
            return 0.0F;
        }
        return meleeSpecs.getKnockBack(toolMaterial);
    }

    @Override
    public float getAttackDelay(ItemStack stack, LivingEntity target, LivingEntity source) {
        return meleeSpecs.attackDelay;
    }
}
