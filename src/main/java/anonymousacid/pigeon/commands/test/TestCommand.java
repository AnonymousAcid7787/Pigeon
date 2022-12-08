package anonymousacid.pigeon.commands.test;

import static anonymousacid.pigeon.McIf.player;

import java.util.List;

import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//This command is just to test some code that might be added to future features
public class TestCommand extends CommandBase {
	
	public static TestCommand instance = new TestCommand();
	public static boolean commandOn = false;
	
	@Override
	public String getCommandName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "";
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		
		return 0;
	}
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public List<String> addTabCompletionOptions(ICommandSender icommandsender, String[] strings, BlockPos blockpos ) {
		
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		
		return true;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] args) throws CommandException {
		if (icommandsender instanceof EntityPlayer) {
			commandOn = !commandOn;
			if(commandOn) MinecraftForge.EVENT_BUS.register(this); else MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	
	@SubscribeEvent
	public void onRender(Pre<EntityLivingBase> e) {
		if(!(e.entity instanceof EntityArmorStand)) return;
		if(!Utils.canSeeEntity(player(), e.entity)) return;
//		RenderUtils.renderHPBar(e.x, e.y+e.entity.height, e.z, e.entity.getHealth() / e.entity.getMaxHealth(), mc().fontRendererObj.getStringWidth("hamburger"));
		EntityArmorStand ent = (EntityArmorStand) e.entity;	
		if(!ent.isInvisible()) return;
		if(!ent.hasCustomName()) return;
		if(!ent.getCustomNameTag().contains("Blaze"))GuiScreen.setClipboardString(ent.getCustomNameTag());//TODO; Make it check hp and make HP bar.
	}
}