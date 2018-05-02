package hu.frontrider.flyingapparatus.logic.flight;

import hu.frontrider.flyingapparatus.items.ApparatusHandHeld;
import hu.frontrider.flyingapparatus.items.FlyingApparatusItem;
import hu.frontrider.flyingapparatus.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static hu.frontrider.flyingapparatus.items.FlyingApparatusItem.ACTIVE_NAME;
import static hu.frontrider.flyingapparatus.items.FlyingApparatusItem.handleStrafe;
import static hu.frontrider.flyingapparatus.items.ItemWithFluid.drainFuel;

@Mod.EventBusSubscriber
public class Controls {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void control(TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (!player.hasItemInSlot(EntityEquipmentSlot.CHEST)
                && (!player.hasItemInSlot(EntityEquipmentSlot.MAINHAND)
                && !player.hasItemInSlot(EntityEquipmentSlot.OFFHAND)))
            return;

        final GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        final ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        final Item item = chest.getItem();
        final ItemStack handheld = player.hasItemInSlot(EntityEquipmentSlot.MAINHAND) ? player.getHeldItem(EnumHand.MAIN_HAND) : player.getHeldItem(EnumHand.OFF_HAND);

        if (item instanceof FlyingApparatusItem) {
            if (chest.hasTagCompound()) {
                final NBTTagCompound nbt = chest.getTagCompound();
                if (nbt.hasKey(ACTIVE_NAME)) {
                    if (!nbt.getBoolean(ACTIVE_NAME))
                        return;
                } else
                    return;
            } else
                return;
            player.motionY = 0;
            drainFuel(chest,1);
            if(!(handheld.getItem() instanceof ApparatusHandHeld))
            {
                player.motionZ =0;
                player.motionX =0;
                return;
            }

            if (gameSettings.keyBindJump.isKeyDown()) {
                FlyingApparatusItem.handleVerticalMovement(chest, player, true);
            } else {
                if (gameSettings.keyBindSneak.isKeyDown()) {
                    FlyingApparatusItem.handleVerticalMovement(chest, player, false);
                }
            }
            if (ClientProxy.STRAFE_LEFT.isKeyDown()) {
                handleStrafe(chest, player, true);
            }

            if (ClientProxy.STRAFE_RIGHT.isKeyDown()) {
                handleStrafe(chest, player, false);
            }
        }
    }
}