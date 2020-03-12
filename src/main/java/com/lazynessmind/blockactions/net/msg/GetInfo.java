package com.lazynessmind.blockactions.net.msg;

import com.lazynessmind.blockactions.net.NetHandler;
import com.lazynessmind.blockactions.api.IInfo;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GetInfo {

    private final BlockPos pos;
    private String infoNbtField;

    public GetInfo(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.infoNbtField = buf.readString(8);
    }

    public GetInfo(String infoNbtField, BlockPos pos) {
        this.pos = pos;
        this.infoNbtField = infoNbtField;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(this.pos);
        buf.writeString(this.infoNbtField);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity playerEntity = ctx.get().getSender();
            if (playerEntity != null) {
                ServerWorld world = (ServerWorld) playerEntity.world;
                if (world != null) {
                    TileEntity entity = world.getTileEntity(this.pos);
                    if (entity != null) {
                        if (entity instanceof IInfo) {
                            IInfo iInfo = (IInfo) entity;
                            ReturnInfo infoMsg = new ReturnInfo(iInfo.getInfo(), infoNbtField);
                            NetHandler.INSTANCE.sendTo(infoMsg, playerEntity.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                        }
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}