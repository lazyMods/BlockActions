package com.lazynessmind.blockactions.actions.planteraction;

import com.lazynessmind.blockactions.actions.placeaction.PlacerBlock;
import com.lazynessmind.blockactions.actions.planteraction.PlanterUtils.PlaceEvent;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.client.util.RayTraceUtil;
import com.lazynessmind.blockactions.event.TileEntityRegister;
import com.lazynessmind.blockactions.utils.InvUtils;
import com.lazynessmind.blockactions.utils.InvUtils.InvObject;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;

public class PlanterTileEntity extends BlockActionTileEntity {

    private Direction facing;
    private BlockPos lastPos;

    public PlanterTileEntity() {
        super(TileEntityRegister.PLANTER_TILE.get());
    }

    @Override
    public void tick() {
        if (this.world != null) {
            if (!this.world.isRemote) {
                this.setFakePlayer(this.world, "PlanterPlayer");

                if (this.getBlockState().has(PlacerBlock.FACING) && this.facing == null) { //Run only once
                    this.facing = this.getBlockState().get(PlacerBlock.FACING);
                    this.lastPos = this.pos.offset(this.facing);
                }

                this.decreaseWorkTime();

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
        boolean hasInv = InvUtils.hasInv(pos.up(), world);
        boolean hasWater = Utils.isWater(world, pos.down());

        if (this.getFacing() != null) {
            if (hasInv && hasWater) {
                InvObject invItem = PlanterUtils.getSeedFromInv(pos, world);

                if (invItem.getState().getBlock() != Blocks.AIR) {
                    PlaceEvent placeEvent = this.placeSeed(invItem.getState(), lastPos, world);
                    lastPos = placeEvent.getNewPos();

                    if (placeEvent.isSuccess()) {
                        InvUtils.removeFromInvAbove(pos, world, invItem.getIndex());
                        if (energyMode) {
                            this.energyManager.removeEnergy(this.getEnergyCost());
                            this.setWorkTime(this.getCoolDown());
                        }
                    }
                }
            }
        }
    }

    private PlaceEvent placeSeed(BlockState cropState, BlockPos lastPos, World world) {
        int xDir = this.getFacing().getDirectionVec().getX();
        int zDir = this.getFacing().getDirectionVec().getZ();
        BlockPos facingPos = this.pos.offset(this.getFacing());

        if (xDir == 0) {
            int xStart = facingPos.getX() - 2;
            int xEnd = facingPos.getX() + 3;
            if (zDir > 0) {
                int zEnd = facingPos.getZ() + 4;
                if (lastPos.getX() + 1 > xEnd && lastPos.getZ() + 1 < zEnd) {
                    lastPos = new BlockPos(xStart, facingPos.getY(), lastPos.getZ() + 1);
                } else if (lastPos.getX() == xEnd) {
                    lastPos = new BlockPos(xStart, facingPos.getY(), facingPos.getZ());
                } else {
                    lastPos = lastPos.add(1, 0, 0);
                }
            } else if (zDir < 0) {
                int zEnd = facingPos.getZ() - 4;
                if (lastPos.getX() + 1 > xEnd && lastPos.getZ() - 1 > zEnd) {
                    lastPos = new BlockPos(xStart, facingPos.getY(), lastPos.getZ() - 1);
                } else if (lastPos.getX() == xEnd) {
                    lastPos = new BlockPos(xStart, facingPos.getY(), facingPos.getZ());
                } else {
                    lastPos = lastPos.add(1, 0, 0);
                }
            }
        }
        if (zDir == 0) {
            int zStart = facingPos.getZ() - 2;
            int zEnd = facingPos.getZ() + 3;
            if (xDir > 0) {
                int xEnd = facingPos.getX() + 4;
                if (lastPos.getZ() + 1 > zEnd && lastPos.getX() + 1 < xEnd) {
                    lastPos = new BlockPos(lastPos.getX() + 1, facingPos.getY(), zStart);
                } else if (lastPos.getZ() == zEnd) {
                    lastPos = new BlockPos(facingPos.getX(), facingPos.getY(), zStart);
                } else {
                    lastPos = lastPos.add(0, 0, 1);
                }
            } else if (xDir < 0) {
                int xEnd = facingPos.getX() - 4;
                if (lastPos.getZ() + 1 > zEnd && lastPos.getX() - 1 > xEnd) {
                    lastPos = new BlockPos(lastPos.getX() - 1, facingPos.getY(), zStart);
                } else if (lastPos.getZ() == zEnd) {
                    lastPos = new BlockPos(facingPos.getX(), facingPos.getY(), zStart);
                } else {
                    lastPos = lastPos.add(0, 0, 1);
                }
            }
        }

        boolean hasFarmland = world.getBlockState(lastPos.down()).getBlock() == Blocks.FARMLAND;
        boolean canPlaceSeed = world.getBlockState(lastPos).getBlock() == Blocks.AIR;
        boolean hasEnergy = this.energyManager.canRemove(this.getEnergyCost());

        if (this.isEnergyMode() && !hasEnergy) {
            return new PlaceEvent(false, lastPos);
        }

        if (hasFarmland && canPlaceSeed) {
            world.setBlockState(lastPos, cropState, 2);
            return new PlaceEvent(true, lastPos);
        } else {
            if (!hasFarmland && !world.getBlockState(lastPos).isSolid()) {
                world.setBlockState(lastPos.down(), Blocks.FARMLAND.getDefaultState(), 2);
            }
            return new PlaceEvent(false, lastPos);
        }
    }

    public Direction getFacing() {
        return this.facing;
    }
}
