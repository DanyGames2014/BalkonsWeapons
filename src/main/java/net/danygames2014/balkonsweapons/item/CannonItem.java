package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class CannonItem extends TemplateItem {
    public CannonItem(Identifier identifier) {
        super(identifier);
        setMaxCount(1);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        float f = 1.0f;
        float f2 =
                user.prevPitch + (user.pitch - user.prevPitch) * f;
        float f3 = user.prevYaw + (user.yaw - user.prevYaw) * f;
        double d = user.prevX + (user.x - user.prevX) * f;
        double d2 = user.prevY + (user.y - user.prevY) * f
                            + 1.6200000000000001D - user.standingEyeHeight;
        double d3 = user.prevZ + (user.z - user.prevZ) * f;
        Vec3d vec3d = Vec3d.create(d, d2, d3);
        float f4 = MathHelper.cos(-f3 * 0.01745329f - 3.141593f);
        float f5 = MathHelper.sin(-f3 * 0.01745329f - 3.141593f);
        float f6 = -MathHelper.cos(-f2 * 0.01745329f);
        float f7 = MathHelper.sin(-f2 * 0.01745329f);
        float f8 = f5 * f6;
        float f10 = f4 * f6;
        double d4 = 5.0;
        Vec3d vec3d2 = vec3d.add(f8 * d4, f7 * d4, f10 * d4);
        HitResult raytraceresult = world.raycast(vec3d, vec3d2, true);
        if (raytraceresult == null || raytraceresult.type != HitResultType.BLOCK || raytraceresult.side != 1) {
            return false;
        }
        int x1 = raytraceresult.blockX;
        int y1 = raytraceresult.blockY;
        int z1 = raytraceresult.blockZ;
        Block block = world.getBlockState(x1, y1, z1).getBlock();
        boolean flag1 = block == Block.SNOW;
        CannonEntity cannonEntity = new CannonEntity(world, x1 + 0.5, y1 + (flag1 ? 0.88 : 1), z1 + 0.5);
        if (!world.isRemote) {
            world.spawnEntity(cannonEntity);
        }
        if (true) { // creative check
            --stack.count;
        }
//        user.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return true;
    }
}
