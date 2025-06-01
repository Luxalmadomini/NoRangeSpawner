package net.luxalmadomini.Infinite_spawner_range.Init;

import net.luxalmadomini.Infinite_spawner_range.Blocks.NoRangeSpawnerBlock;
import net.luxalmadomini.Infinite_spawner_range.Infinite_spawner_range;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Infinite_spawner_range.MOD_ID);

    public static final RegistryObject<Block> NO_RANGE_SPAWNER =
            BLOCKS.register("no_range_spawner",
                    () -> new NoRangeSpawnerBlock(BlockBehaviour.Properties.of().strength(3.5F).noOcclusion()));

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}