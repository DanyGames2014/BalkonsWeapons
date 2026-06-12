package net.danygames2014.balkonsweapons.mixin;

import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.danygames2014.balkonsweapons.network.ShootCannonC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    public ClientPlayerEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "updateKey", at = @At("HEAD"))
    void shootCannon(int key, boolean state, CallbackInfo ci) {
        if(key == Minecraft.INSTANCE.options.jumpKey.code && state && vehicle instanceof CannonEntity cannonEntity) {
            PacketHelper.send(new ShootCannonC2SPacket(cannonEntity.id));
        }
    }
}
