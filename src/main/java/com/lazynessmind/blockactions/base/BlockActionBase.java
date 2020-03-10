package com.lazynessmind.blockactions.base;

import com.lazynessmind.blockactions.api.IRedstoneWork;
import com.lazynessmind.blockactions.utils.RedstoneWorkRegistry;
import com.lazynessmind.blockactions.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.dispenser.ProxyBlockSource;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

import java.util.List;
import java.util.Random;

public class BlockActionBase extends ModBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public BlockActionBase(String name) {
        super(name, Block.Properties.from(Blocks.OBSERVER), ItemGroup.REDSTONE);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        this.doWork(worldIn, pos, state);
    }

    private void doWork(World world, BlockPos pos, BlockState state){
        ProxyBlockSource proxyblocksource = new ProxyBlockSource(world, pos);
        BlockActionTileEntity blockTileEntity = proxyblocksource.getBlockTileEntity();
        IRedstoneWork iRedstoneWork = RedstoneWorkRegistry.REDSTONE_WORKS.get(this);
        if(iRedstoneWork != null){
            iRedstoneWork.doWork(blockTileEntity, state, world, pos);
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!worldIn.isRemote){
            if (state.getBlock() != newState.getBlock()) {
                TileEntity tile = worldIn.getTileEntity(pos);
                if (tile instanceof BlockActionTileEntity) {
                    BlockActionTileEntity blockActionTileEntity = (BlockActionTileEntity) tile;
                    if (!blockActionTileEntity.getUpgradeItems().isEmpty()) {
                        InventoryHelper.dropItems(worldIn, pos, blockActionTileEntity.getUpgradeItems());
                    }
                }

                if (state.hasTileEntity()) {
                    worldIn.removeTileEntity(pos);
                }
            }
        }
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 60; //3sec
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return NonNullList.withSize(1, Utils.asStack(asItem()));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        ProxyBlockSource proxyBlockSource = new ProxyBlockSource(worldIn, pos);
        if(proxyBlockSource.getBlockTileEntity() instanceof BlockActionTileEntity){
            BlockActionTileEntity tileEntity = proxyBlockSource.getBlockTileEntity();
            if(!tileEntity.isEnergyMode()){
                boolean isPowered = worldIn.isBlockPowered(pos);
                boolean isTriggered = state.get(TRIGGERED);
                if (isPowered && !isTriggered) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
                    worldIn.setBlockState(pos, state.with(TRIGGERED, true), 4);
                } else if (!isPowered && isTriggered) {
                    worldIn.setBlockState(pos, state.with(TRIGGERED, false), 4);
                }
            }
        }
    }
}
