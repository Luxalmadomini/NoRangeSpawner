package net.luxalmadomini.Infinite_spawner_range.Init;

import net.luxalmadomini.Infinite_spawner_range.Entities.NoRangeSpawnerBlockEntity;
import net.luxalmadomini.Infinite_spawner_range.Blocks.NoRangeSpawnerBlock;
import net.luxalmadomini.Infinite_spawner_range.Infinite_spawner_range;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Infinite_spawner_range.MOD_ID);

    public static final RegistryObject<BlockEntityType<NoRangeSpawnerBlockEntity>> NO_RANGE_SPAWNER =
            BLOCK_ENTITIES.register("no_range_spawner",
                    () -> BlockEntityType.Builder.of(
                            NoRangeSpawnerBlockEntity::new,
                            ModBlocks.NO_RANGE_SPAWNER.get()
                    ).build(null)
            );

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}