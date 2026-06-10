package net.danygames2014.balkonsweapons.entity.projectile;

import net.minecraft.entity.Entity;

public interface ThrowableEntity {
    Entity getThrower();
    void setThrower(Entity entity);
}
