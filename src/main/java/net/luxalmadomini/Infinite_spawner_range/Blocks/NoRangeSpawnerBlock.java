package net.luxalmadomini.Infinite_spawner_range.Blocks;

import net.luxalmadomini.Infinite_spawner_range.Entities.NoRangeSpawnerBlockEntity;
import net.luxalmadomini.Infinite_spawner_range.NoRangeSpawner;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class NoRangeSpawnerBlock extends Block implements EntityBlock {

    public static final EnumProperty<SpawnerType> TYPE = EnumProperty.create("type", SpawnerType.class);

    public NoRangeSpawnerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, SpawnerType.VANILLA));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(TYPE);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // Return your custom BlockEntity here if needed
        CompoundTag entityTag = new CompoundTag();
        return new NoRangeSpawnerBlockEntity(pos, state);
    }

    public enum SpawnerType implements StringRepresentable{
        VANILLA, ZOMBIE, SKELETON, BLAZE, CREEPER, ENDERMAN, SPIDER;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }
}


