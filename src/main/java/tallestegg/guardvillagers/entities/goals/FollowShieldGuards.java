package tallestegg.guardvillagers.entities.goals;

import java.util.List;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ShieldItem;
import tallestegg.guardvillagers.entities.GuardEntity;

public class FollowShieldGuards extends Goal {
	   private final GuardEntity taskOwner;
	   private GuardEntity guardtofollow;
	   private int navigateTimer;

	   public FollowShieldGuards(GuardEntity taskOwnerIn) {
	      this.taskOwner = taskOwnerIn;
	   }

	   public boolean shouldExecute() {
	      if (this.taskOwner.getHeldItemOffhand().getItem() instanceof ShieldItem) {
	         return false;
	      } else {
	         List<GuardEntity> list = this.taskOwner.world.getEntitiesWithinAABB(this.taskOwner.getClass(), this.taskOwner.getBoundingBox().grow(8.0D, 8.0D, 8.0D));
		      if (!list.isEmpty()) {
			         for(GuardEntity guard : list) {
			            if (!guard.isInvisible() && guard.getHeldItemOffhand().getItem() instanceof ShieldItem) {
			               this.guardtofollow = guard;
			               return true;
			            }
			         }
			      }
	      }
		 return false;
	   }

	  /* public boolean shouldContinueExecuting() {
	      return this.taskOwner.hasGroupLeader() && this.taskOwner.inRangeOfGroupLeader();
	   } */

	   public void startExecuting() {
	      this.navigateTimer = 0;
	   }

	   /* public void resetTask() {
	      this.taskOwner.leaveGroup();
	   } */

	   public void tick() {
	         this.taskOwner.getNavigator().tryMoveToEntityLiving(guardtofollow, 1.0D);
	      }
	   }
