package net.danygames2014.balkonsweapons.item;

import net.danygames2014.balkonsweapons.api.UseAction;
import net.danygames2014.balkonsweapons.api.UseActions;
import net.danygames2014.balkonsweapons.mixininterface.ItemWithHold;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class TestItem extends TemplateItem implements ItemWithHold {
    public TestItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public UseAction getUseAction(ItemStack stack, World world, PlayerEntity player) {
        return UseActions.BLOCK;
    }

    @Override
    public int getMaxUseDuration() {
        return 4000;
    }

    @Override
    public void startUsing(ItemStack stack, World world, PlayerEntity player) {
        System.out.println("start using");
    }

    @Override
    public int getTextureId(int damage) {
        return 67;
//        return 21;
    }

    @Override
    public void usingTick(ItemStack stack, World world, PlayerEntity player, int time) {
//        System.out.println("Using " + time);
    }

    @Override
    public boolean stopUsing(ItemStack stack, World world, PlayerEntity player, int time) {
        return true;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        System.out.println("Finished holding");
        System.out.println(user.getItemInUseTime());
        return stack;
    }

    @Override
    public boolean isHandheld() {
        return true;
    }
}
