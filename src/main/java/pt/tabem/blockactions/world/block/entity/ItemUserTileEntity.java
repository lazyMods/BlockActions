package pt.tabem.blockactions.world.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import pt.tabem.blockactions.ModBlockEntities;
import pt.tabem.blockactions.util.InvUtils;

import javax.annotation.Nonnull;

public class ItemUserTileEntity extends BlockActionTileEntity {

    public ItemUserTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLAYER_SIMULATOR.get(), pos, state);
    }

    @Override
    public void init(BlockState state, BlockPos pos) {
        super.init(state, pos);
        this.setFakePlayer(this.level, "ItemUser");
    }

    @Override
    public boolean stopIf(@NotNull Level level) {
        return false;
    }

    @Override
    public void doWork(@Nonnull Level world) {
        var facingPos = this.worldPosition.relative(direction);
        var optionalContainer = InvUtils.tryGetContainer(this.worldPosition.above(), world);
        if (optionalContainer.isPresent()) {
            optionalContainer.ifPresent(container -> {
                var stackOpt = InvUtils.getNotEmpty(container);
                if (stackOpt.isEmpty()) {
                    var block = new BlockHitResult(Vec3.ZERO, this.direction, this.worldPosition.relative(this.direction), false);
                    this.getFakePlayer().gameMode.useItemOn(this.getFakePlayer(), world, ItemStack.EMPTY, InteractionHand.MAIN_HAND, block);
                    return;
                }
                var stack = stackOpt.get();
                if (stack.isDamageableItem()) {
                    if (stack.isCorrectToolForDrops(this.getFacingState())) {
                        this.getFakePlayer().setItemInHand(InteractionHand.MAIN_HAND, stack);
                        this.getFakePlayer().gameMode.destroyBlock(facingPos);
                        this.getFakePlayer().setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    }
                } else {
                    var block = new BlockHitResult(Vec3.ZERO, this.direction, this.worldPosition.relative(this.direction), false);
                    this.getFakePlayer().setItemInHand(InteractionHand.MAIN_HAND, stack);
                    this.getFakePlayer().gameMode.useItemOn(getFakePlayer(), world, stack, InteractionHand.MAIN_HAND, block);
                    this.getFakePlayer().setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            });
        } else {
            var block = new BlockHitResult(Vec3.ZERO, this.direction, this.worldPosition.relative(this.direction), false);
            this.getFakePlayer().gameMode.useItemOn(this.getFakePlayer(), world, ItemStack.EMPTY, InteractionHand.MAIN_HAND, block);
        }
    }
}
