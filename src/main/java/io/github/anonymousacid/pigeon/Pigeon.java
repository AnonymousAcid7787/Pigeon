package io.github.anonymousacid.pigeon;

import static io.github.anonymousacid.pigeon.McIf.player;
import static io.github.anonymousacid.pigeon.McIf.world;

import java.util.Random;

import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon2;
import io.github.anonymousacid.pigeon.commands.ConfigCommand;
import io.github.anonymousacid.pigeon.commands.animations.FerocityAnimation;
import io.github.anonymousacid.pigeon.commands.animations.HealerWish;
import io.github.anonymousacid.pigeon.commands.animations.PigeonEntity;
import io.github.anonymousacid.pigeon.commands.animations.VoidgloomShield;
import io.github.anonymousacid.pigeon.commands.test.KillEntities;
import io.github.anonymousacid.pigeon.commands.test.LogNearbyEntity;
import io.github.anonymousacid.pigeon.commands.test.TestCommand;
import io.github.anonymousacid.pigeon.features.chat.ChatStuff;
import io.github.anonymousacid.pigeon.features.chat.chatbubbles.ChatBubbleNametags;
import io.github.anonymousacid.pigeon.features.chat.chatbubbles.ChatBubbleTimer;
import io.github.anonymousacid.pigeon.features.chat.chatbubbles.ChatBubbles;
import io.github.anonymousacid.pigeon.features.chat.kaomojis.ChatKaomojis;
import io.github.anonymousacid.pigeon.features.chat.kaomojis.KaomojiGuiInit;
import io.github.anonymousacid.pigeon.features.chat.kaomojis.TabKeyTimer;
import io.github.anonymousacid.pigeon.features.dungeons.ProfessorFireFreeze;
import io.github.anonymousacid.pigeon.features.misc.HealthBars;
import io.github.anonymousacid.pigeon.features.misc.LatencyCounter;
import io.github.anonymousacid.pigeon.features.misc.SBPetName;
import io.github.anonymousacid.pigeon.features.misc.cooldown.CooldownHandler;
import io.github.anonymousacid.pigeon.features.misc.cooldown.CooldownTimer;
import io.github.anonymousacid.pigeon.features.misc.miniontiers.MinionTierRender;
import io.github.anonymousacid.pigeon.gui.PigeonButton;
import io.github.anonymousacid.pigeon.gui.config.ExperimentalFeaturesGui;
import io.github.anonymousacid.pigeon.handlers.ConfigHandler;
import io.github.anonymousacid.pigeon.init.ModEntities;
import io.github.anonymousacid.pigeon.proxy.CommonProxy;
import io.github.anonymousacid.pigeon.utils.Utils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Pigeon {
	
	@Mod.Instance(Reference.MODID)
	public static Pigeon instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static PigeonButton pigeonButton;
	public static Random random;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit();
		
		ModEntities.registerEntities();

		ConfigHandler.init();
		
		System.out.println("Pigeon pre-initialized!");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		
		McIf.setMinecraftSingleton();
		ConfigHandler.reloadConfig();
		
		{//Gui stuff
			pigeonButton = new PigeonButton(0, ConfigHandler.pigeonButtonX, ConfigHandler.pigeonButtonY, 50);
		}
		
		random = new Random();
		
//		MinecraftForge.EVENT_BUS.register(KeybindsInit.class);
		
		{//Proxy stuff
			proxy.registerEntityRenders();
			proxy.registerRenders();
			proxy.init();
		}
		
		{//adding experimental features to a TreeMap(for Experimental feature config GUI's buttons)
			ExperimentalFeaturesGui.featureConfig.put("miscellaneous animations", "ferocityAnimations");
			ExperimentalFeaturesGui.featureConfig.put("dungeon animations", "healerWish");
			ExperimentalFeaturesGui.featureConfig.put("slayer animations", "voidgloomShield");
			ExperimentalFeaturesGui.featureConfig.put("dungeons", "professorFireFreeze");
			ExperimentalFeaturesGui.featureConfig.put("miscellaneous", "hpBars");
		}
		
		{//Adding regular features to a TreeMap for regular Config GUI
			
		}
		
		{//Command registry
			ClientCommandHandler.instance.registerCommand(new ConfigCommand());
			ClientCommandHandler.instance.registerCommand(new ProfessorFireFreeze());
			
			{//Commands used for testing.
				ClientCommandHandler.instance.registerCommand(new LogNearbyEntity());
//				ClientCommandHandler.instance.registerCommand(new NBTTest());
//				ClientCommandHandler.instance.registerCommand(new ChatRecieved());
				ClientCommandHandler.instance.registerCommand(TestCommand.instance);
//				MinecraftForge.EVENT_BUS.register(TestCommand.instance);
				ClientCommandHandler.instance.registerCommand(new KillEntities());
			}
		}
		
		{//Utils
			MinecraftForge.EVENT_BUS.register(new ChatStuff());
		}
		
		{//Features
			MinecraftForge.EVENT_BUS.register(ChatBubbles.instance);
			MinecraftForge.EVENT_BUS.register(ChatBubbleNametags.instance);
			MinecraftForge.EVENT_BUS.register(ChatBubbleTimer.instance);
		
			MinecraftForge.EVENT_BUS.register(ChatKaomojis.instance);
			MinecraftForge.EVENT_BUS.register(KaomojiGuiInit.instance);
			MinecraftForge.EVENT_BUS.register(TabKeyTimer.instance);
			
			MinecraftForge.EVENT_BUS.register(SBPetName.instance);
			
			MinecraftForge.EVENT_BUS.register(CooldownTimer.instance);
			MinecraftForge.EVENT_BUS.register(CooldownHandler.instance);
			
			MinecraftForge.EVENT_BUS.register(ProfessorFireFreeze.instance);
			for(int i=0; i<CooldownHandler.abilities.length; i++) {
				CooldownHandler.abilities[i] = "";
				CooldownHandler.itemsOnCooldown.add(null);
			}
			MinecraftForge.EVENT_BUS.register(LatencyCounter.instance);
			
			MinecraftForge.EVENT_BUS.register(MinionTierRender.instance);
			
			MinecraftForge.EVENT_BUS.register(HealthBars.instance);
		}
		
		{//cosmetic features
			ClientCommandHandler.instance.registerCommand(VoidgloomShield.instance);
			ClientCommandHandler.instance.registerCommand(HealerWish.instance);
			ClientCommandHandler.instance.registerCommand(PigeonEntity.instance);
			ClientCommandHandler.instance.registerCommand(FerocityAnimation.instance);
			
			MinecraftForge.EVENT_BUS.register(VoidgloomShield.instance);
			MinecraftForge.EVENT_BUS.register(HealerWish.instance);
			MinecraftForge.EVENT_BUS.register(FerocityAnimation.instance); 
		}
		MinecraftForge.EVENT_BUS.register(Utils.instance);
		
		System.out.println("Pigeon initialized!");
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this.instance);
		System.out.println("Pigeon post-initialized");
	}
	
	/**
	 * Spawning pigeon entity when connecting to a new world
	 */
	private boolean worldJustLoaded = false;
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
	    this.worldJustLoaded = true;
	}
	
	/**
	 * Spawning pigeon entity when connecting to a new world
	 */
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
	    if(this.worldJustLoaded) {
	        this.worldJustLoaded = false;
	        if(ConfigHandler.getBoolean("miscellaneous animations", "spawnPigeon")) {
	        	Utils.spawnEntity(
						new EntityPigeon2(world(), 0.7, 0.05),
						player().posX,player().posY,player().posZ);
	        }
	    }
	}
}