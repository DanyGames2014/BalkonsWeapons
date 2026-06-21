package net.danygames2014.balkonsweapons.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;

public class BattleaxeItem extends MeleeItem{
    public BattleaxeItem(Identifier identifier, ToolMaterial toolMaterial) {
        super(identifier, MeleeSpecs.BATTLEAXE, toolMaterial);
        mineableTag = TagKey.of(BlockRegistry.KEY, Identifier.of("minecraft:mineable/axe"));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        double mx = target.velocityX;
        double my = target.velocityY;
        double mz = target.velocityZ;
//        int prevhurtres = otherEntity.hurtResistantTime;
//        int prevhurt = otherEntity instanceof EntityLivingBase ? ((EntityLivingBase) otherEntity).hurtTime : 0;
        target.damage(attacker, (int)getIgnoreArmorAmount(toolMaterial));
        target.velocityX = mx;
        target.velocityY = my;
        target.velocityZ = mz;
//        otherEntity.hurtResistantTime = prevhurtres;
//        if (otherEntity instanceof EntityLivingBase)
//            ((EntityLivingBase) otherEntity).hurtTime = prevhurt;
        return super.postHit(stack, target, attacker);
    }

    public float getIgnoreArmorAmount(ToolMaterial material) {
        return 1.0f;
    }
}
