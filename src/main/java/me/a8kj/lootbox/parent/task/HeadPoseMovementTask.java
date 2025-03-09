package me.a8kj.lootbox.parent.task;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.bukkit.scheduler.BukkitRunnable;

import me.a8kj.lootbox.parent.entity.lootbox.Lootbox;

public class HeadPoseMovementTask extends BukkitRunnable {

    private final Lootbox lootbox;

    public HeadPoseMovementTask(Lootbox lootbox) {
        this.lootbox = lootbox;
    }

    @Override
    public void run() {
        if (lootbox == null || lootbox.getEntity() == null || !(lootbox.getEntity() instanceof ArmorStand)) {
            this.cancel();
            return;
        }

        ArmorStand armorStand = (ArmorStand) lootbox.getEntity();
        EulerAngle currentPose = armorStand.getHeadPose();
        EulerAngle newPose = currentPose.setY(currentPose.getY() + 0.2);
        armorStand.setHeadPose(newPose);
    }

}
