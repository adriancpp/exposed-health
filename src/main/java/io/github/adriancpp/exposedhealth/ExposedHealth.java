package io.github.adriancpp.exposedhealth;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.adriancpp.exposedhealth.Events.EventsClass;

public class ExposedHealth extends JavaPlugin
{
	@Override
	public void onLoad() {

	}
	
	@Override
    public void onEnable() 
	{
		getServer().getPluginManager().registerEvents(new EventsClass(), this);
		runnable();
		runnableDelayed();
	}
    
    @Override
    public void onDisable() 
    {

    }
    
    public void runnable()
    {
    	new BukkitRunnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() 
			{
				//e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()
				//List<LivingEntity> allE = getServer().getWorld("world").getLivingEntities();
				
				for(World w : Bukkit.getWorlds())
				{
					List<LivingEntity> ens;
		            try
		            {
		            	ens = w.getLivingEntities();	
		            }
		            catch(ArrayIndexOutOfBoundsException e) 
		            {
		            	ens = w.getLivingEntities();
	            	}
		            
		            Iterator<LivingEntity> itr = ens.iterator();
		            
					while(itr.hasNext()) 
					{
						LivingEntity e = itr.next();
						
						String ename = e.getCustomName();
						String cname = "";
						
						if( ename != null )
						{
							String REGEX = "(.§a[0-9]+§2\\/§a[0-9]+.*)";
							String INPUT = ename;
		
							Pattern pattern = Pattern.compile(REGEX);
					        Matcher matcher = pattern.matcher(INPUT);
					        
					        while(matcher.find()) 
					        {
					        	cname =  matcher.group(1);
					        }
					        
					        cname = ename.replace(cname,"");
							e.setCustomName( cname +  " §a" + (int)e.getHealth() + "§2/§a" + (int)e.getMaxHealth() + "§4" + (char)Globals.c); 
						}
						else
						{ 
							e.setCustomName( " §a" + (int)e.getHealth() + "§2/§a" + (int)e.getMaxHealth() + "§4" + (char)Globals.c); 
						}
						e.setCustomNameVisible(false);
	
					}
				}
				
				for(World w : Bukkit.getWorlds())
				for (Player e : w.getPlayers()) 
				{
					String ename = e.getPlayerListName();
					String cname = "";
					
					if( ename != null )
					{
						String REGEX = "(.§a[0-9]+§2\\/§a[0-9]+.*)";
						String INPUT = ename;
	
						Pattern pattern = Pattern.compile(REGEX);
				        Matcher matcher = pattern.matcher(INPUT);
				        
				        while(matcher.find()) 
				        {
				        	cname =  matcher.group(1);
				        }
				        
				        cname = ename.replace(cname,"");
						e.setPlayerListName( cname +  " §a" + (int)e.getHealth() + "§2/§a" + (int)e.getMaxHealth() + "§4" + (char)Globals.c); 
					}
					else
					{ 
						e.setPlayerListName( " §a" + (int)e.getHealth() + "§2/§a" + (int)e.getMaxHealth() + "§4" + (char)Globals.c); 
					}
					e.setCustomNameVisible(false);

				}

			}

		}.runTaskTimerAsynchronously(this, 0, 5);
    }
    
    public void runnableDelayed()
    {
    	new BukkitRunnable() {

			@Override
			public void run() {
				getServer().broadcastMessage("Server has Started");
			}
				
		}.runTaskLaterAsynchronously(this, 40);
    }
}