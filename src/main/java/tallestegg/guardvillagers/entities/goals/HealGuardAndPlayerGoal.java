package tallestegg.guardvillagers.entities.goals;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import tallestegg.guardvillagers.entities.GuardEntity;

@SuppressWarnings("unused")
public class HealGuardAndPlayerGoal extends Goal {
    private final MobEntity healer;
    private LivingEntity mob;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;
    protected final EntityPredicate predicate = (new EntityPredicate()).setDistance(64.0D);

    public HealGuardAndPlayerGoal(MobEntity healer, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
        this.healer = healer;
        this.entityMoveSpeed = movespeed;
        this.attackIntervalMin = p_i1650_4_;
        this.maxRangedAttackTime = maxAttackTime;
        this.attackRadius = maxAttackDistanceIn;
        this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (((VillagerEntity)this.healer).getVillagerData().getProfession() != VillagerProfession.CLERIC || this.healer.isSleeping())
        {
            return false;
        }
        List<GuardEntity> list = this.healer.world.getEntitiesWithinAABB(GuardEntity.class, this.healer.getBoundingBox().grow(10.0D));
        if (!list.isEmpty()) {
            for(GuardEntity mob : list) {
                if (mob.isAlive()) {
                    this.mob = mob;
                    return true;
                }
            }
        }
        AxisAlignedBB axisalignedbb = this.healer.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
        List<PlayerEntity> list2 = this.healer.world.getEntitiesWithinAABB(PlayerEntity.class, this.healer.getBoundingBox().grow(10.0D));
        if (!list2.isEmpty()) {
            for(PlayerEntity player : list2) {
                if (player.isAlive() && player.isPotionActive(Effects.HERO_OF_THE_VILLAGE) && !player.abilities.isCreativeMode) {
                    this.mob = player;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || mob.getHealth() < mob.getMaxHealth();
    }

    public void resetTask() {
        this.mob = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
    }

    public void tick() {
        double d0 = this.healer.getDistanceSq(this.healer.getPositionVec().getX(), this.healer.getPositionVec().getY(), this.healer.getPositionVec().getZ());
        boolean flag = this.healer.getEntitySenses().canSee(this.healer);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        this.healer.getLookController().setLookPositionWithEntity(this.healer, 30.0F, 30.0F);
        if (--this.rangedAttackTime == 0 && mob.getHealth() < mob.getMaxHealth() && mob.isAlive()) {
            if (!flag) {
                return;
            }
            this.healer.faceEntity(mob, 30.0F, 30.0F);
            if (healer.getDistance(mob) >= 5.0D) {
                healer.getNavigator().tryMoveToEntityLiving(mob, 0.5D);
            }
            float f = MathHelper.sqrt(d0) / this.attackRadius;
            this.throwPotion(mob, 1.0F);
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.rangedAttackTime < 0) {
            float f2 = MathHelper.sqrt(d0) / this.attackRadius;
            this.rangedAttackTime = MathHelper.floor(f2 * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        }
    }

    public void throwPotion(LivingEntity target, float distanceFactor)
    {
        Vec3d vec3d = target.getMotion();
        double d0 = target.getPositionVec().getX() + vec3d.x - healer.getPositionVec().getX();
        double d1 = (target.getPositionVec().getY() + (double)target.getEyeHeight()) - (double)1.1F - healer.getPositionVec().getY();
        double d2 = target.getPositionVec().getZ() + vec3d.z - healer.getPositionVec().getZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        Potion potion = Potions.REGENERATION;
        if (target.getHealth() <= 4.0F)
        {
            potion = Potions.HEALING;
        } else {
            potion = Potions.REGENERATION;
        }

        PotionEntity potionentity = new PotionEntity(healer.world, healer);
        potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
        potionentity.rotationPitch -= -20.0F;
        potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
        healer.world.playSound((PlayerEntity)null, healer.getPositionVec().getX(), healer.getPositionVec().getY(), healer.getPositionVec().getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, healer.getSoundCategory(), 1.0F, 0.8F + healer.getRNG().nextFloat() * 0.4F);
        healer.world.addEntity(potionentity);
    }
}