package tallestegg.guardvillagers.entities.goals;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;

public class HelpVillagerGoal extends TargetGoal 
{
	   protected final MobEntity mob;
	   protected LivingEntity villageAggressorTarget;
	   protected final EntityPredicate field_223190_c = (new EntityPredicate()).setDistance(64.0D);

	   public HelpVillagerGoal(MobEntity mob) {
	      super(mob, false, true);
	      this.mob = mob;
	      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET, Goal.Flag.MOVE));
	   }
	
	   public boolean shouldExecute() {
		      List<MobEntity> list = this.goalOwner.world.getEntitiesWithinAABB(MobEntity.class, this.goalOwner.getBoundingBox().grow((double)100.0D));

		         for(LivingEntity entity : list) {
		        	 if (((MobEntity) entity).getAttackTarget() instanceof VillagerEntity) {
		               this.villageAggressorTarget = entity;
		        	 }
		      }
		         
		      if (this.villageAggressorTarget == null) {
		         return false;
		      } else {
		         return !this.villageAggressorTarget.isSpectator();
		      }
		   }

	   @Override
	   protected double getTargetDistance() 
	   {
		  return villageAggressorTarget.getDistance(goalOwner) + 10.0F; //broadcasts location
	   }
	   
	   @Override
	   public void startExecuting() 
	   {
		   this.goalOwner.getNavigator().tryMoveToEntityLiving(villageAggressorTarget, goalOwner.getAIMoveSpeed());
		   this.goalOwner.setAttackTarget(this.villageAggressorTarget);
	   }
}