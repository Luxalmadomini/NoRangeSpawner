package net.luxalmadomini.Infinite_spawner_range.Entities;

import net.luxalmadomini.Infinite_spawner_range.NoRangeSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.luxalmadomini.Infinite_spawner_range.Init.ModBlockEntities;

public class NoRangeSpawnerBlockEntity extends BlockEntity {
    private final NoRangeSpawner spawner;

    public NoRangeSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NO_RANGE_SPAWNER.get(), pos, state);
        this.spawner = new NoRangeSpawner(new CompoundTag());
    }

    public NoRangeSpawner getSpawner() {
        return spawner;
    }
}