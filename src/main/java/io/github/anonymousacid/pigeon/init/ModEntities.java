package io.github.anonymousacid.pigeon.init;

import io.github.anonymousacid.pigeon.Pigeon;
import io.github.anonymousacid.pigeon.client.fakeentities.*;
import io.github.anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	
	private static int entityID = 0;
	
	public static void registerEntities() {
		registerEntity(EntityVoidgloomShield.class, "voidgloomshield", 0, 0, false);
		registerEntity(EntityHealerWish.class, "healerwish", 0, 0, false);
		registerEntity(EntityPigeon.class, "pigeon2", 0, 0, false);
		registerEntity(EntityFeroictyArrow.class, "fakearrow", 0, 0, false);
		registerEntity(EntityWishEffect.class, "wisheffect", 0, 0, false);
		registerEntity(EntityPoop.class, "pigeonpoop", 0, 0, false);
		registerEntity(EntityFeroictyArrow.class, "ferocitymelee", 0, 0, false);
	}
	
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, Pigeon.instance, trackingRange, updateFrequency, false);
	}
	
}
