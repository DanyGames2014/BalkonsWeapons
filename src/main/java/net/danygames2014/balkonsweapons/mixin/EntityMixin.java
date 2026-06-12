package net.danygames2014.balkonsweapons.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
    @WrapOperation(
            method = "tickRiding",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/entity/Entity;yaw:F"
            )
    )
    public void ignoreCannonYawChange(Entity instance, float value, Operation<Void> original){
        if(!(instance.vehicle instanceof CannonEntity)){
            instance.yaw = value;
        }
    }

    @WrapOperation(
            method = "tickRiding",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/entity/Entity;pitch:F"
            )
    )
    public void ignoreCannonPitchChange(Entity instance, float value, Operation<Void> original){
        if(!(instance.vehicle instanceof CannonEntity)){
            instance.pitch = value;
        }
    }
}
