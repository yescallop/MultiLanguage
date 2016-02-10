package cn.yescallop.plugin.multilanguage;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.Player;
import cn.nukkit.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.io.File;
import java.io.IOException;

public class MultiLanguage extends PluginBase implements Listener{

    protected static HashMap<String, String[]> languages;
    protected static File database;

    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveResource("languages.json");
        try{
            languages = new Gson().fromJson(Utils.readFile(new File(this.getDataFolder(), "languages.json")), new TypeToken<HashMap<String, String[]>>(){}.getType());
        }catch(IOException e){
            this.setEnabled(false);
        }
        this.saveResource("GeoLite2-Country.mmdb");
        database = new File(this.getDataFolder(), "GeoLite2-Country.mmdb");
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Locale locale = new LanguageCode().fromIp(player.getAddress());
        player.setLocale(locale);
        this.getLogger().debug(player.getName() + "'s language has been set to " + locale.getDisplayName());
    }
}