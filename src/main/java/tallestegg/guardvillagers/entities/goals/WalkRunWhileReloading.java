package tallestegg.guardvillagers.entities.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.Vec3d;
import tallestegg.guardvillagers.entities.GuardEntity;

public class WalkRunWhileReloading extends RandomWalkingGoal
{
	public WalkRunWhileReloading(CreatureEntity p_i1648_1_, double p_i1648_2_) {
		super(p_i1648_1_, p_i1648_2_);
	}
	
	@Override
	public boolean shouldExecute()
	{
		return ((GuardEntity)creature).isCharging() && creature.getAttackTarget() != null;
	}
	
	@Override
	protected Vec3d getPosition() 
	{
	   return RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.creature, 16, 7, this.creature.getAttackTarget().getPositionVec());
	}
}