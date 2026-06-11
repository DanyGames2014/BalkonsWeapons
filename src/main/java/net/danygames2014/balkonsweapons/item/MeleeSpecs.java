package net.danygames2014.balkonsweapons.item;

import net.minecraft.item.ToolMaterial;

public enum MeleeSpecs {
    SPEAR(0, 1.0f, 3.0f, 1.0f, 1.0f, 0.2f, 1, 2, 2.7f),
    HALBERD(0, 1.0f, 4.0f, 1.0f, 1.5f, 0.6f, 1, 2, 3.2f),
    BATTLEAXE(0, 1.0f, 3.0f, 1.0f, 1.5f, 0.5f, 1, 2, 3.0f),
    WARHAMMER(0, 1.0f, 4.0f, 1.0f, 1.0f, 0.7f, 1, 2, 3.0f),
    KNIFE(0, 0.5f, 3.0f, 1.0f, 1.5f, 0.2f, 1, 2, 2.0f),
    KATANA(0, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1, 2, 0.2f),
    FIREROD(1, 0.0f, 1.0f, 0.0f, 1.0f, 0.4f, 2, 0, 0.0f),
    BOOMERANG(0, 0.5f, 2.0f, 1.0f, 1.0f, 0.4f, 1, 1, 2.0f),
    NONE(0, 1.0f, 1.0f, 0.0f, 1.0f, 0.4f, 0, 0, 0.0f);

    public final int durabilityBase;
    public final float durabilityMult;
    public final float damageBase;
    public final float damageMult;
    public final float blockDamage;
    public final float knockback;
    public final float attackDelay;
    public final int dmgFromEntity;
    public final int dmgFromBlock;

    MeleeSpecs(int durbase, float durmult, float dmgbase, float dmgmult,
               float blockdmg, float knockback, int dmgfromentity, int dmgfromblock,
               float attackdelay) {
        durabilityBase = durbase;
        durabilityMult = durmult;
        damageBase = dmgbase;
        damageMult = dmgmult;
        blockDamage = blockdmg;
        this.knockback = knockback;
        dmgFromEntity = dmgfromentity;
        dmgFromBlock = dmgfromblock;
        attackDelay = attackdelay;
    }

    public float getKnockBack(final ToolMaterial material) {
        return (material == ToolMaterial.GOLD) ? (knockback * 1.5f) : knockback;
    }
}
