package anonymousacid.pigeon.features.misc;

import static anonymousacid.pigeon.McIf.mc;
import static anonymousacid.pigeon.McIf.player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anonymousacid.pigeon.handlers.ConfigHandler;
import anonymousacid.pigeon.utils.RenderUtils;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/**
 * Shows health bar of mobs in Hypixel Skyblock
 * TODO: If possible, find a way to detect of a mob is a boss.
 */
public class HealthBars {
	
	public static HealthBars instance = new HealthBars();
	
	@SubscribeEvent
	public void onRender(Pre<EntityLivingBase> e) {
		if(!ConfigHandler.hpBars) return;
		if(!(e.entity instanceof EntityArmorStand)) return;
		if(!Utils.canSeeEntity(player(), e.entity)) return;
		EntityArmorStand ent = (EntityArmorStand) e.entity;
		if(!ent.hasCustomName()) return;
		String str = Utils.removeFormat(ent.getCustomNameTag());
		if(!str.contains("/")) return;
        Pattern p = Pattern.compile("(\\w+/\\w+)");
        Matcher m = p.matcher(str);
        if(m.find()) {
            String group = m.group(1);
            String str1 = Utils.compactToIntegerForm(group.split("/")[0]);
            String str2 = Utils.compactToIntegerForm(group.split("/")[1]);
            RenderUtils.renderHPBar(e.x, e.y+e.entity.height+0.3, e.z, 
            		(double)Integer.parseInt(str1)/Integer.parseInt(str2),
            		mc().fontRendererObj.getStringWidth("hamburger"));//idk what to set the size to, so i chose the nametag size of "hamburger" lol
        }
	}
}
