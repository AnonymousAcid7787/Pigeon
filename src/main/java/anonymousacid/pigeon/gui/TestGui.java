package anonymousacid.pigeon.gui;

import anonymousacid.pigeon.features.misc.LatencyCounter;
import anonymousacid.pigeon.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class TestGui extends Gui{
	
	public TestGui(Minecraft mc, String text) {
		ScaledResolution scaled = new ScaledResolution(mc);
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		int x = width/2;
		int y = height/2;
		drawString(mc.fontRendererObj,
				text,
				x, y,
				0);
	}
}
