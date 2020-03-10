package com.lazynessmind.blockactions.actions.hitaction;

import com.lazynessmind.blockactions.actions.placeaction.PlacerBlock;
import com.lazynessmind.blockactions.base.BlockActionTileEntity;
import com.lazynessmind.blockactions.event.TileEntityRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class HitTileEntity extends BlockActionTileEntity {

    private static final String TAG_DAMAGE = "Damage";
    public float baseDamage = 1f;

    private static final String TAG_ONLY_ADULT = "AttackOnlyAdults";
    public boolean attackOnlyAdults = false;

    private float dmg;
    private Direction direction;

    public HitTileEntity() {
        super(TileEntityRegister.HIT_TILE.get());
    }

    @Override
    public void tick() {
        if (this.world != null) {
            if (!this.world.isRemote) {

                this.setFakePlayer(this.world, "HitPlayer");

                if (this.getBlockState().has(PlacerBlock.FACING)) {
                    this.direction = this.getBlockState().get(PlacerBlock.FACING);
                }

                this.decreaseWorkTime();

                if(this.isEnergyMode()){
                    this.energyManager.canInsert(true);
                    if(this.canWork()){
                        this.doWork(this.world, this.pos, true);
                    }
                }
            }
        }
    }

    @Override
    public void doWork(@Nonnull World world, BlockPos pos, boolean energyMode) {
        if (this.getFacing() != null) {
            BlockPos posAtFacing = this.pos.offset(this.getFacing());
            AxisAlignedBB area = new AxisAlignedBB(posAtFacing);
            this.dmg = (float) HitUtils.getDamage(this.baseDamage, this.world, posAtFacing).getValue();

            if(this.isEnergyMode() && !this.energyManager.canRemove(this.getEnergyCost())) return;

            for (LivingEntity livingEntity : this.world.getEntitiesWithinAABB(LivingEntity.class, area)) {
                if (this.attackOnlyAdults) {
                    if (!livingEntity.isChild()) {
                        this.attackEntities(posAtFacing, livingEntity, dmg);
                        HitUtils.damageItem(world, posAtFacing, this.getFakePlayer());
                    }
                } else {
                    this.attackEntities(posAtFacing, livingEntity, dmg);
                    HitUtils.damageItem(world, posAtFacing, this.getFakePlayer());
                }
                if(energyMode) this.energyManager.removeEnergy(this.getEnergyCost());
            }
            this.setWorkTime(this.getCoolDown());
        }
    }

    private void attackEntities(BlockPos posAtFacing, LivingEntity livingEntity, float dmg) {
        if (HitUtils.causeFireDamage(world, posAtFacing)) {
            livingEntity.attackEntityFrom(DamageSource.ON_FIRE, dmg);
            livingEntity.setFire(3);
        } else {
            livingEntity.attackEntityFrom(DamageSource.GENERIC, dmg);
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.baseDamage = compound.getFloat(TAG_DAMAGE);
        this.attackOnlyAdults = compound.getBoolean(TAG_ONLY_ADULT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt.putFloat(TAG_DAMAGE, this.baseDamage);
        nbt.putBoolean(TAG_ONLY_ADULT, this.attackOnlyAdults);
        return nbt;
    }

    public Direction getFacing() {
        return this.direction;
    }

    @Override
    public CompoundNBT getInfo() {
        CompoundNBT nbt = super.getInfo();
        nbt.putString("damage", new TranslationTextComponent("infooverlay.hit.damage").appendText(String.valueOf(this.dmg)).getFormattedText());
        if (this.attackOnlyAdults) {
            nbt.putString("onlyAdults", new TranslationTextComponent("infooverlay.hit.onlyadults").getFormattedText());
        }
        return nbt;
    }
}
