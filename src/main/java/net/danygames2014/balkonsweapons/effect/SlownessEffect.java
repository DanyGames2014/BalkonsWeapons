package net.danygames2014.balkonsweapons.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.effect.EntityEffect;
import net.modificationstation.stationapi.api.effect.EntityEffectType;

public class SlownessEffect extends EntityEffect<SlownessEffect> {

    private final LivingEntity livingEntity;

    public static final EntityEffectType<SlownessEffect> TYPE = EntityEffectType.builder(SlownessEffect::new).build();

    protected SlownessEffect(Entity entity, int ticks) {
        super(entity, ticks);
        if(!(entity instanceof LivingEntity livingEntity)) {
            throw new RuntimeException("Effect can be applied only on living entities");
        }
        this.livingEntity = livingEntity;
    }

    @Override
    public void onAdded(boolean appliedNow) {

    }

    @Override
    public void onTick() {
        livingEntity.velocityX *= 0.3F;
        livingEntity.velocityZ *= 0.3F;
    }

    @Override
    public void onRemoved() {

    }

    @Override
    protected void writeNbt(NbtCompound tag) {

    }

    @Override
    protected void readNbt(NbtCompound tag) {

    }

    @Override
    public EntityEffectType<SlownessEffect> getType() {
        return TYPE;
    }
}
