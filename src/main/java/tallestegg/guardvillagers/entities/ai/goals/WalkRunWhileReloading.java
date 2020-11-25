package tallestegg.guardvillagers.entities.ai.goals;

import javax.annotation.Nullable;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;
import tallestegg.guardvillagers.entities.GuardEntity;

public class WalkRunWhileReloading extends RandomWalkingGoal {

    public WalkRunWhileReloading(CreatureEntity creatureIn, double speedIn) {
        super(creatureIn, speedIn);
    }

    @Override
    public boolean shouldExecute() {
        return ((GuardEntity) creature).isCharging() && creature.getAttackTarget() != null && this.findPosition();
    }

    public boolean findPosition() {
        Vector3d vector3d = this.getPosition();
        if (vector3d == null) {
            return false;
        } else {
            this.x = vector3d.x;
            this.y = vector3d.y;
            this.z = vector3d.z;
            return true;
        }
    }
    @Override
    @Nullable
    protected Vector3d getPosition() {
        return RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.creature, 16, 7, this.creature.getAttackTarget().getPositionVec());
    }
}