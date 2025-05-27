package net.luxalmadomini.Infinite_spawner_range;

import com.mojang.logging.LogUtils;
import net.luxalmadomini.Infinite_spawner_range.Init.ModBlockEntities;
import net.luxalmadomini.Infinite_spawner_range.Init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Infinite_spawner_range.MOD_ID)
public class Infinite_spawner_range {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "infinite_spawner_range_vanilla_spawners";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Set<BlockPos> trackedSpawners = new HashSet<>();


    public Infinite_spawner_range() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        ModBlockEntities.register(modEventBus);

        ModBlocks.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onSpawnerRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        if ( event.getItemStack().getItem() != Items.EMERALD_BLOCK) return;

        BlockPos pos = event.getPos();
        ServerLevel level = (ServerLevel) event.getLevel();
        if (!(level.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner)) return;

        event.getEntity().getItemInHand(event.getHand()).shrink(1);

        spawner.getPersistentData().putBoolean("IgnorePlayerRange", true);
        spawner.setChanged();

        trackedSpawners.add(pos);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.LevelTickEvent event){
        if (event.phase != TickEvent.Phase.END || event.level.isClientSide()) return;
        ServerLevel level = (ServerLevel) event.level;
        for (BlockPos pos : trackedSpawners) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be == null) continue;

            SpawnerBlockEntity spawner = (SpawnerBlockEntity) be;
            try {
                Field rangeField = spawner.getSpawner().getClass().getDeclaredField("requiredPlayerRange");
                rangeField.setAccessible(true);
                rangeField.setInt(spawner.getSpawner(), 100000000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
