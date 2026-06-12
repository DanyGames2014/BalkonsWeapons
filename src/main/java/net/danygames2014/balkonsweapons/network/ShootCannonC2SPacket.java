package net.danygames2014.balkonsweapons.network;

import net.danygames2014.balkonsweapons.entity.CannonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShootCannonC2SPacket extends Packet implements ManagedPacket<ShootCannonC2SPacket> {
    private int cannonEntityId;

    public static final PacketType<ShootCannonC2SPacket> TYPE = PacketType.builder(false, true, ShootCannonC2SPacket::new).build();

    public ShootCannonC2SPacket() {
    }

    public ShootCannonC2SPacket(int cannonEntityId) {
        this.cannonEntityId = cannonEntityId;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            cannonEntityId = stream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(cannonEntityId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity playerEntity = PlayerHelper.getPlayerFromPacketHandler(networkHandler);

        CannonEntity cannon = null;
        for(Object object : playerEntity.world.entities) {
            if(object instanceof CannonEntity cannonEntity && cannonEntity.id == cannonEntityId) {
                cannon = cannonEntity;
                break;
            }
        }

        if(cannon != null) {
            cannon.fireCannon();
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<ShootCannonC2SPacket> getType() {
        return TYPE;
    }
}
