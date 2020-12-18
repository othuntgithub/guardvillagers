package tallestegg.guardvillagers.entities.ai.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import tallestegg.guardvillagers.entities.GuardEntity;

public class GuardEatFoodGoal extends Goal {

    public final GuardEntity guard;

    public GuardEatFoodGoal(GuardEntity guard) {
        this.guard = guard;
    }

    @Override
    public boolean shouldExecute() {
        return guard.getHealth() < guard.getMaxHealth() && GuardEatFoodGoal.isConsumable(guard.getHeldItemOffhand()) && guard.isEating() 
                || guard.getHealth() < guard.getMaxHealth() && 
                GuardEatFoodGoal.isConsumable(guard.getHeldItemOffhand()) && guard.getAttackTarget() == null;
    }

    public static boolean isConsumable(ItemStack stack) {
        return stack.getUseAction() == UseAction.EAT || stack.getUseAction() == UseAction.DRINK;
    }

    @Override
    public void startExecuting() {
        if (guard.getAttackTarget() == null)
            guard.setEating(true);
        guard.setActiveHand(Hand.OFF_HAND);
    }
}
