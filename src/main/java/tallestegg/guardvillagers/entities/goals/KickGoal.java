package tallestegg.guardvillagers.entities.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.CrossbowItem;
import tallestegg.guardvillagers.entities.GuardEntity;

public class KickGoal extends Goal {

    public final GuardEntity guard;

    public KickGoal(GuardEntity guard) {
        this.guard = guard;
    }

    @Override
    public boolean shouldExecute() {
        return guard.getAttackTarget() != null && guard.getAttackTarget().getDistance(guard) <= 2.5D && guard.getHeldItemMainhand().getItem() instanceof CrossbowItem && guard.kickCoolDown == 0;
    }

    @Override
    public void startExecuting() {
        guard.setKicking(true);
        if (guard.kickTicks <= 0) {
            guard.kickTicks = 10;
        }
        guard.attackEntityAsMob(guard.getAttackTarget());
    }

    @Override
    public void resetTask() {
        guard.setKicking(false);
        guard.kickCoolDown = 10;
    }
}
