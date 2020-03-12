package com.lazynessmind.blockactions.actions.breakaction;

import com.lazynessmind.blockactions.Configs;
import com.lazynessmind.blockactions.actions.placeaction.PlacerBlock;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.event.TileEntityRegister;
import com.lazynessmind.blockactions.utils.InvUtils;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;

public class BreakerTileEntity extends BlockActionTileEntity {

    private static final String TAG_HAS_SILK = "HasSilkTouch";
    public boolean hasSilkTouch;

    public Direction direction;

    public BreakerTileEntity() {
        super(TileEntityRegister.BREAKER_TILE.get());
    }

    @Override
    public void tick() {
        if (this.world != null) {
            if (!this.world.isRemote) {

                if (this.getBlockState().has(PlacerBlock.FACING)) {
                    this.direction = this.getBlockState().get(PlacerBlock.FACING);
                }

                if (this.world.getBlockState(pos.offset(this.getFacing())) != Blocks.AIR.getDefaultState()) {
                    this.decreaseWorkTime();
                }

                if (this.isEnergyMode()) {
                    this.energyManager.canInsert(true);
                    if (this.canWork()) {
                        this.doWork(this.world, this.pos, true);
                    }
                }
            }
        }
    }

    @Override
    public void doWork(@Nonnull World world, BlockPos pos, boolean energyMode) {
        if (this.getFacing() != null) {
            BlockPos facingPos = this.pos.offset(this.getFacing());
            BlockState facingState = this.world.getBlockState(facingPos);

            boolean isAir = facingState.isAir(this.world, facingPos);
            boolean isLiquid = BreakerUtil.isFluidAt(facingState.getBlock());
            boolean isUnbreakable = facingState.getBlockHardness(this.world, facingPos) >= 0;
            boolean isTileEntity = facingState.hasTileEntity();
            boolean hasInv = InvUtils.hasInv(facingPos, this.world);

            if (energyMode && !this.energyManager.canRemove(this.getEnergyCost())) return;
            if (isTileEntity && !Configs.BREAK_TE.get()) return;
            if (hasInv) return;
            if (!isAir && !isLiquid && isUnbreakable) {
                this.breakBlock(this.world, facingPos, this.hasSilkTouch);
                if (energyMode) this.energyManager.removeEnergy(this.getEnergyCost());
                this.setWorkTime(this.getCoolDown());
            }
        }
    }

    private void breakBlock(@Nonnull World world, BlockPos facingPos, boolean hasSilkTouch) {
        BlockState toDrop = world.getBlockState(facingPos);

        if (hasSilkTouch) {
            world.destroyBlock(facingPos, false);
            boolean canInsertItem = InvUtils.addToInvAbove(this.pos, world, Utils.asStack(toDrop.getBlock().asItem()));
            if (!canInsertItem) {
                Utils.spawnItem(facingPos, world, Utils.asStack(toDrop.getBlock().asItem()));
            }
        } else {
            List<ItemStack> drops = Block.getDrops(toDrop, (ServerWorld) world, facingPos, world.getTileEntity(facingPos));
            boolean canInsertItem = false;

            for (ItemStack stack : drops) {
                canInsertItem = InvUtils.addToInvAbove(this.pos, world, stack);
            }

            world.destroyBlock(facingPos, !canInsertItem);
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.hasSilkTouch = compound.getBoolean(TAG_HAS_SILK);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt.putBoolean(TAG_HAS_SILK, this.hasSilkTouch);
        return nbt;
    }

    public Direction getFacing() {
        return this.direction;
    }

    @Override
    public CompoundNBT getInfo() {
        CompoundNBT info = super.getInfo();
        info.putString("silkTouch", new TranslationTextComponent("infooverlay.silktouch").appendText(String.valueOf(this.hasSilkTouch)).getFormattedText());
        return info;
    }
}
