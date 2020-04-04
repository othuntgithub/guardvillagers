package tallestegg.guardvillagers.entities.goals;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import tallestegg.guardvillagers.entities.GuardEntity;

public class DefendVillageGuardGoal extends TargetGoal {
   private final GuardEntity guard;
   private LivingEntity villageAgressorTarget;
   private final EntityPredicate field_223190_c = (new EntityPredicate()).setDistance(64.0D);

   public DefendVillageGuardGoal(GuardEntity guardIn) {
      super(guardIn, false, true);
      this.guard = guardIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean shouldExecute() {
      AxisAlignedBB axisalignedbb = this.guard.getBoundingBox().grow(10.0D, 8.0D, 10.0D);
      List<LivingEntity> list = this.guard.world.getTargettableEntitiesWithinAABB(VillagerEntity.class, this.field_223190_c, this.guard, axisalignedbb);
      List<PlayerEntity> list1 = this.guard.world.getTargettablePlayersWithinAABB(this.field_223190_c, this.guard, axisalignedbb);

      for(LivingEntity livingentity : list) {
         VillagerEntity villagerentity = (VillagerEntity)livingentity;

         for(PlayerEntity playerentity : list1) {
            int i = villagerentity.getPlayerReputation(playerentity);
            if (i <= -100) {
               this.villageAgressorTarget = playerentity;
            }
         }
      }

      if (this.villageAgressorTarget == null) {
         return false;
      } else {
         return !(this.villageAgressorTarget instanceof PlayerEntity) || !this.villageAgressorTarget.isSpectator() && !((PlayerEntity)this.villageAgressorTarget).isCreative();
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.guard.setAttackTarget(this.villageAgressorTarget);
      super.startExecuting();
   }
}