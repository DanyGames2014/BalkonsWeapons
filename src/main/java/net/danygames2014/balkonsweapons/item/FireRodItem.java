package net.danygames2014.balkonsweapons.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class FireRodItem extends TemplateItem {
    private static final Random RANDOM = new Random();

    public FireRodItem(Identifier identifier) {
        super(identifier);
        this.setMaxDamage(1);
        this.setMaxCount(1);
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        attackedEntity.fireTicks = 260;
        return 1;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker);
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity player) {
            if (player.inventory.getSelectedItem() != stack) {
                return;
            }

            if (player.isInFluid(Material.WATER)) {
                return;
            }

            float particleX = -MathHelper.sin(((player.yaw + 28.0F) / 180F) * 3.141593F) * MathHelper.cos((player.pitch / 180F) * 3.141593F);
            float particleY = -MathHelper.sin((player.pitch / 180F) * 3.141593F) + player.getEyeHeight();
            float particleZ = MathHelper.cos(((player.yaw + 28.0F) / 180F) * 3.141593F) * MathHelper.cos((player.pitch / 180F) * 3.141593F);

            if (RANDOM.nextInt(5) == 0) {
                world.addParticle("flame", player.x + particleX, player.y + particleY, player.z + particleZ, 0.0D, 0.0D, 0.0D);
            }

            if (RANDOM.nextInt(5) == 0) {
                world.addParticle("smoke", player.x + particleX, player.y + particleY, player.z + particleZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
