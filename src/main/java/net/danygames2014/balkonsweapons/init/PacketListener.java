package net.danygames2014.balkonsweapons.init;

import net.danygames2014.balkonsweapons.BalkonsWeapons;
import net.danygames2014.balkonsweapons.network.ShootCannonC2SPacket;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;

public class PacketListener {

    @EventListener
    public void registerPacketTypes(PacketRegisterEvent event) {
        event.register(BalkonsWeapons.NAMESPACE.id("shoot_cannon"), ShootCannonC2SPacket.TYPE);
    }
}
