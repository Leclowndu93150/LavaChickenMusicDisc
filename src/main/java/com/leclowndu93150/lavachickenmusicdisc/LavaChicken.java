package com.leclowndu93150.lavachickenmusicdisc;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(LavaChicken.MODID)
public class LavaChicken {

    public static final String MODID = "lavachickenmusicdisc";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_LAVA_CHICKEN_SOUND = SOUNDS.register("music_disc.lava_chicken",
            () -> new SoundEvent(new ResourceLocation(MODID, "music_disc.lava_chicken")));

    public static final RegistryObject<Item> MUSIC_DISC_LAVA_CHICKEN = ITEMS.register("music_disc_lava_chicken",
            () -> new RecordItem(15, MUSIC_DISC_LAVA_CHICKEN_SOUND.get(),
                    new Item.Properties().stacksTo(1).rarity(Rarity.RARE).tab(CreativeModeTab.TAB_TOOLS), 3080));

    public LavaChicken() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(modEventBus);
        SOUNDS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ForgeEvents());
    }

    public static class ForgeEvents {
        @SubscribeEvent
        public void onLivingDrops(LivingDropsEvent event) {
            if (event.getEntity() instanceof Zombie zombie && zombie.isBaby() &&
                    zombie.getVehicle() != null && zombie.getVehicle().getType() == EntityType.CHICKEN) {
                event.getDrops().add(new net.minecraft.world.entity.item.ItemEntity(
                        event.getEntity().level,
                        event.getEntity().getX(),
                        event.getEntity().getY(),
                        event.getEntity().getZ(),
                        new ItemStack(MUSIC_DISC_LAVA_CHICKEN.get())));
            }
        }
    }

}