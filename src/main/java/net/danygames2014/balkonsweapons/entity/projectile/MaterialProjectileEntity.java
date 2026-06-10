package net.danygames2014.balkonsweapons.entity.projectile;

import net.danygames2014.balkonsweapons.item.ItemHitEffect;
import net.danygames2014.balkonsweapons.item.component.WeaponItem;
import net.danygames2014.balkonsweapons.util.DataTrackerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MaterialProjectileEntity  extends ProjectileEntity{
    private static final int WEAPON_MATERIAL = 18;
    private static final int WEAPON_ITEM = 19;
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f}, {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};

    public MaterialProjectileEntity(World world) {
        super(world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(WEAPON_MATERIAL, 0);
        dataTracker.startTracking(WEAPON_ITEM, 5);
    }

    @Override
    public void onEntityHit(HitResult hitResult) {
        super.onEntityHit(hitResult);
        ItemStack thrownItem = getWeapon();
        if(thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect effect) {
            effect.onEntityHit(this, hitResult);
        }
    }

    @Override
    public void onBlockHit(HitResult hitResult) {
        super.onBlockHit(hitResult);
        ItemStack thrownItem = getWeapon();
        if(thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect effect) {
            effect.onBlockHit(this, hitResult);
        }
    }

    public void setThrownItemStack(@Nullable ItemStack itemstack) {
        dataTracker.set(WEAPON_ITEM, itemstack);
        updateWeaponMaterial();
    }

    @Override
    public ItemStack getPickupItem() {
        return getWeapon();
    }

    public int getWeaponMaterialId() {
        return dataTracker.getInt(WEAPON_MATERIAL);
    }

    public ItemStack getWeapon() {
        return DataTrackerUtil.getItemStack(dataTracker ,WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null && thrownItem.getItem() instanceof WeaponItem weaponItem) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material = weaponItem.getToolMaterial().ordinal();
            }
            dataTracker.set(WEAPON_MATERIAL, material);
        }
    }

    public float[] getMaterialColor() {
        int id = getWeaponMaterialId();
        if (id >= 0 && id < MATERIAL_COLORS.length) {
            return MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        ItemStack thrownItem = getWeapon();
        if(getWeapon() != null) {
            nbt.put("thrI", thrownItem.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.contains("thrI")) {
            setThrownItemStack(new ItemStack(nbt.getCompound("thrI")));
        }
    }
}
