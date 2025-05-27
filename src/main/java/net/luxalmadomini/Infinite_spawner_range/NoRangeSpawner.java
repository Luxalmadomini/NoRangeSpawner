package net.luxalmadomini.Infinite_spawner_range;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

public  class NoRangeSpawner {
    private final CompoundTag entityTag;


    public NoRangeSpawner(CompoundTag entityTag){
        this.entityTag = entityTag;
    }

    public void serverTick(ServerLevel level, BlockPos pos){


        double halfRange = 25.0;

        AABB box = new AABB(
                pos.getX() - halfRange, pos.getY() - halfRange, pos.getZ() - halfRange,
                pos.getX() + halfRange + 1, pos.getY() + halfRange + 1, pos.getZ() + halfRange + 1
        );

        int hostileCount = level.getEntitiesOfClass(
                net.minecraft.world.entity.Mob.class,
                box,
                mob -> mob instanceof net.minecraft.world.entity.monster.Enemy
        ).size();

        if (hostileCount <= 100){
            Entity entity = EntityType.loadEntityRecursive(entityTag, level, e -> e);
            if (entity != null){
                entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);
                if (entity instanceof Mob mob) {
                    mob.spawnAnim();

                }
                level.tryAddFreshEntityWithPassengers(entity);
                level.levelEvent(2004, pos, 0);
                level.gameEvent(entity, net.minecraft.world.level.gameevent.GameEvent.ENTITY_PLACE, pos);
            }

        }




    }
}
