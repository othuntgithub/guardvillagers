package tallestegg.guardvillagers.entities.ai.goals;

import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardFindCoverGoal extends RandomWalkingGoal {

    protected final GuardEntity guard;
    private int walkTimer;

    public GuardFindCoverGoal(GuardEntity guard, double speedIn) {
        super(guard, speedIn);
        this.guard = guard;
    }

    @Override
    public boolean shouldExecute() {
        return guard.getHealth() < guard.getMaxHealth() && this.findPosition() && GuardEatFoodGoal.isConsumable(guard.getHeldItemOffhand()) && !guard.isEating();
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        if (walkTimer <= 0)
            this.walkTimer = 50;
    }

    @Override
    public void tick() {
        super.tick();
        this.walkTimer--;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() && super.shouldContinueExecuting() && walkTimer > 0;
    }

    @Override
    public void resetTask() {
        super.resetTask();
        if (walkTimer <= 0)
            guard.setEating(true);
    }

    public boolean findPosition() {
        Vector3d vector3d = this.getPosition();
        if (vector3d != null) {
            this.x = vector3d.x;
            this.y = vector3d.y;
            this.z = vector3d.z;
            return true;
        }
        return false;
    }
}
