package net.danygames2014.balkonsweapons.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;

public class EntityUtil {
    public static Box getBoundingBox(Entity entity) {
        Box box = entity == null ? null : entity.getBoundingBox();
        box = box != null || entity == null ? box : entity.boundingBox;
        return box == null ? Box.create(0, 0, 0, 0, 0, 0) : box;
    }

    public static void setFire(Entity entity, int seconds) {
        int ticks = seconds * 20;
        if(entity.fireTicks < ticks) {
            entity.fireTicks = ticks;
        }
    }
}
