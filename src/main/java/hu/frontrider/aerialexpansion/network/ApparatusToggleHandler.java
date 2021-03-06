package hu.frontrider.aerialexpansion.network;

import hu.frontrider.aerialexpansion.items.FlyingApparatusItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static hu.frontrider.aerialexpansion.items.FlyingApparatusItem.ACTIVE_NAME;

public class ApparatusToggleHandler implements IMessageHandler<ApparatusToggleMessage, IMessage> {

    @Override
    public IMessage onMessage(ApparatusToggleMessage apparatusFunctionMessage, MessageContext messageContext) {

        final EntityPlayerMP player = messageContext.getServerHandler().player;
        ItemStack chestSlot = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

        if (chestSlot.getItem() instanceof FlyingApparatusItem){
                NBTTagCompound nbt = chestSlot.getTagCompound();
                if (nbt == null) {
                    nbt = new NBTTagCompound();
                }
                if(nbt.hasKey(ACTIVE_NAME)){
                    nbt.setBoolean(ACTIVE_NAME,!nbt.getBoolean(ACTIVE_NAME));
                }
                else
                nbt.setBoolean(ACTIVE_NAME, true);
                chestSlot.setTagCompound(nbt);
            }
        return null;
    }
}
