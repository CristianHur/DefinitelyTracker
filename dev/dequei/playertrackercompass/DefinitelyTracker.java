package dev.dequei.playertrackercompass;
 
import static org.bukkit.GameMode.SURVIVAL;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
 
public class DefinitelyTracker extends JavaPlugin implements Listener
{
    @Override
    public void onEnable()
    {
        System.out.println("[DefinitelyTracker] Español:");
        System.out.println("[DefinitelyTracker] El plugin ha cargado.");
        System.out.println("[DefinitelyTracker] Plugin creado por Dequei");
        System.out.println("[DefinitelyTracker] English:");
        System.out.println("[DefinitelyTracker] The plugin loaded");
        System.out.println("[DefinitelyTracker] Plugin created by Dequei"); 
        
        getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
    }
    
    @Override
    public void onDisable() 
    {
        System.out.println("[DefinitelyTracker] Español:");
        System.out.println("[DefinitelyTracker] El plugin se ha detenido.");
        System.out.println("[DefinitelyTracker] English:");
        System.out.println("[DefinitelyTracker] The plugin has stopped.");
    }
    
    @EventHandler
    public void on(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();  
        if (p.hasPermission("dt.tracker") || p.hasPermission("dt.*"))
        {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) 
        {
            try 
            {
                if (p.getItemInHand().getType() == Material.getMaterial(getConfig().getString("Item")))
                {
                    int cuantos = 0;
                    float configdistance = getConfig().getInt("Distance");
                    for (Entity entity : p.getNearbyEntities(configdistance, 250.0D, configdistance)) 
                    {
                        if ((entity instanceof Player)) 
                            {
                                cuantos ++;
                                Player Jugador = (Player)entity;
                                if(Jugador.getGameMode() == SURVIVAL)
                                {
                                    p.setCompassTarget(Jugador.getLocation());
                                    int distancia = (int)p.getLocation().distance(Jugador.getLocation());
                                    p.sendMessage(getConfig().getString("TAG").replace("&", "§") + " " + getConfig().getString("Message").replace("&", "§").replaceAll("%player%", Jugador.getName()).replaceAll("%distance%",String.valueOf(distancia))); 
                                }
                            }
                    }
                    
                    if(cuantos == 0)
                    {
                        p.sendMessage(getConfig().getString("NoPlayersNearby").replace("&", "§"));
                    }
                }
            }
            catch (Exception localException) {}
        }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender Envio, Command Comando, String label, String[] args)
    {
        Player Jugador = (Player) Envio;
        
        if (Comando.getName().equalsIgnoreCase("dt") || label.equalsIgnoreCase("DefinitelyTracker")) 
        {
            if(args.length > 0)
                {
                    if(args[0].equalsIgnoreCase("reload"))
                    {
                    if(Jugador.hasPermission("dt.reload") || Jugador.hasPermission("dt.*"))
                    {
                        reloadConfig();
                        Jugador.sendMessage(getConfig().getString("Reload").replace("&", "§"));
                        
                        return true;
                    }
                    else
                    {
                        Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                        return true;
                    }
                    }
                }
                    else
                if(Jugador.hasPermission("dt.help") || Jugador.hasPermission("dt.*"))
                {
                    String titulo, help, reload, lines, message;
            
                    titulo = "&6&lDefinitelyTracker";
                    help = "&e&l/dt &b&lShow DefinitelyTracker commands";
                    reload = "&e&l/dt reload &b&lReload DefinitelyTracker";
            
                    Jugador.sendMessage(titulo.replace("&", "§"));
                    Jugador.sendMessage(help.replace("&", "§"));
                    Jugador.sendMessage(reload.replace("&", "§"));
                    
                    return true;
                }
                else
                {
                    Jugador.sendMessage(getConfig().getString("NoPermission").replace("&", "§"));
                    return true;
                }
        }
        
        return false;
    }

}