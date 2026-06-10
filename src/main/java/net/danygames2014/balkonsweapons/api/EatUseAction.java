package net.danygames2014.balkonsweapons.api;

import net.danygames2014.nyalib.particle.ParticleHelper;
import net.danygames2014.nyalib.sound.SoundHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.opengl.GL11;

public class EatUseAction extends UseAction{
    public EatUseAction(Identifier identifier) {
        super(identifier);
    }

    @Override
    public void updateInUse(ItemStack stack, PlayerEntity player, int time, boolean finished) {
        int particles = 0;
        if(time <= stack.getMaxUseDuration() - 5 && time % 4 == 0) {
            particles = 5;
        }
        else if(finished) {
            particles = 16;
        }

        for(int i = 0; i < particles; i++) {
            Vec3d var4 = Vec3d.create(((double)this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            var4.rotateX(-player.pitch * (float)Math.PI / 180.0F);
            var4.rotateY(-player.yaw * (float)Math.PI / 180.0F);
            Vec3d var5 = Vec3d.create(((double)this.random.nextFloat() - 0.5D) * 0.3D, (double)(-this.random.nextFloat()) * 0.6D - 0.3D, 0.6D);
            var5.rotateX(-player.pitch * (float)Math.PI / 180.0F);
            var5.rotateY(-player.yaw * (float)Math.PI / 180.0F);
            var5 = var5.add(player.x, player.y + (double)player.getEyeHeight(), player.z);

            ParticleHelper.addItemParticle(player.world, stack.getItem(),var5.x, var5.y, var5.z, var4.x, var4.y + 0.05D, var4.z);
        }

        if(finished) {
            SoundHelper.playSound(player, "balkonsweapons:random.burp", 0.5F, this.random.nextFloat() * 0.1F + 0.9F);
            SoundHelper.playSound(player, "balkonsweapons:random.eat", 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        } else if(time <= stack.getMaxUseDuration() - 5 && time % 4 == 0) {
            SoundHelper.playSound(player, "balkonsweapons:random.eat", 0.5F + 0.5F * (float)this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void transformFirstPersonSwingProgressVanilla(PlayerEntity player, ItemStack stack, float tickDelta) {
        float var14 = (float)player.getItemInUseDuration() - tickDelta + 1.0F;
        float var15 = 1.0F - var14 / (float)stack.getMaxUseDuration();
        float var16 = 1.0F - var15;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        float var17 = 1.0F - var16;
        GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var14 / 4.0F * (float)Math.PI) * 0.1F) * (float)((double)var15 > 0.2D ? 1 : 0), 0.0F);
        GL11.glTranslatef(var17 * 0.6F, -var17 * 0.5F, 0.0F);
        GL11.glRotatef(var17 * 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(var17 * 10.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var17 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void transformFirstPersonSwingProgressModel(PlayerEntity player, ItemStack stack, float tickDelta) {
        this.transformFirstPersonSwingProgressVanilla(player, stack, tickDelta);
    }
}
