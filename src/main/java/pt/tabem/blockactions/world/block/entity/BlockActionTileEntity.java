package pt.tabem.blockactions.world.block.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;
import pt.tabem.blockactions.world.block.BlockActionBase;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class BlockActionTileEntity extends BlockEntity {

    private boolean firstInit = false;

    private FakePlayer fakePlayer;
    protected Direction direction;

    public BlockActionTileEntity(BlockEntityType<?> tileEntityType, BlockPos pos, BlockState state) {
        super(tileEntityType, pos, state);
    }

    public abstract boolean stopIf(@Nonnull Level level);

    public abstract void doWork(@Nonnull Level world);

    public void init(BlockState state, BlockPos pos) {
        this.direction = state.getValue(BlockActionBase.FACING);
    }

    public void tick() {
        if (this.level != null) {
            if (!this.level.isClientSide()) {

                if (!firstInit) {
                    this.init(this.getBlockState(), this.worldPosition);
                    this.firstInit = true;
                }

                if (this.getBlockState().getValue(BlockActionBase.POWERED)) return;
                if (stopIf(this.level)) return;
                this.doWork(this.level);
            }
        }
    }

    public BlockState getFacingState() {
        return this.direction != null
                ? Objects.requireNonNull(this.level).getBlockState(this.worldPosition.relative(this.direction))
                : Blocks.AIR.defaultBlockState();
    }

    public Direction getFacing() {
        return this.direction;
    }

    public void setFakePlayer(Level world, String playerName) {
        this.fakePlayer = new FakePlayer((ServerLevel) world, new GameProfile(null, playerName)) {
            @Override
            public boolean isSpectator() {
                return false;
            }

            @Override
            public boolean isCreative() {
                return false;
            }
        };
    }

    public FakePlayer getFakePlayer() {
        return this.fakePlayer;
    }
}
