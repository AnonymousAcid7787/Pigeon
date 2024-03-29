package anonymousacid.pigeon.commands.test;

import static anonymousacid.pigeon.McIf.player;
import static anonymousacid.pigeon.McIf.world;
import static anonymousacid.pigeon.McIf.mc;

import java.util.List;

import anonymousacid.pigeon.McIf;
import anonymousacid.pigeon.client.fakeentities.EntityPigeon;
import anonymousacid.pigeon.events.ChestSlotClickedEvent;
import anonymousacid.pigeon.gui.TestGui;
import anonymousacid.pigeon.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

//This command is just to test some code that might be added to future features
public class TestCommand extends CommandBase {
	
	public static TestCommand instance = new TestCommand();
	public static boolean commandOn = false;
	
	private boolean showGui = false;
	
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
//			commandOn = !commandOn;
//			if(commandOn) MinecraftForge.EVENT_BUS.register(this); else MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	
	@SubscribeEvent
	public void onTick(ChestSlotClickedEvent e) {
		
	}
}
