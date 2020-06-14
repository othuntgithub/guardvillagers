package tallestegg.guardvillagers.entities.goals;

import java.util.EnumSet;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import tallestegg.guardvillagers.entities.GuardEntity;

public class HeroHurtTargetGoal extends TargetGoal {
	   private final GuardEntity guard;
	   private LivingEntity attacker;
	   private int timestamp;

	   public HeroHurtTargetGoal(GuardEntity theEntityTameableIn) {
	      super(theEntityTameableIn, false);
	      this.guard = theEntityTameableIn;
	      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
	   }

	   public boolean shouldExecute() {
	         LivingEntity livingentity = this.guard.hero;
	         if (livingentity == null) {
	            return false;
	         } else {
	            this.attacker = livingentity.getLastAttackedEntity();
	            int i = livingentity.getLastAttackedEntityTime();
	            return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && !(attacker instanceof GuardEntity);
	         }
	   }

	   public void startExecuting() {
	      this.goalOwner.setAttackTarget(this.attacker);
	      LivingEntity livingentity = this.guard.hero;
	      if (livingentity != null) {
	         this.timestamp = livingentity.getLastAttackedEntityTime();
	      }
	      super.startExecuting();
	   }
}
