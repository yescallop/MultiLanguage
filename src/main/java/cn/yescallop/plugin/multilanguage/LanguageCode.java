package cn.yescallop.plugin.multilanguage;

import com.maxmind.geoip2.DatabaseReader;

import java.util.Map;
import java.util.Locale;
import java.net.InetAddress;

public class LanguageCode{

    private static final String[] DEFAULT_LANGUAGE = {"en", "US"};
    
    public Locale fromIp(String ip){
        return fromCountry(getIpCountry(ip));
    }
    
    public Locale fromCountry(String code){
        String[] locale = MultiLanguage.languages.get(code);
        return (locale == null) ? new Locale(DEFAULT_LANGUAGE[0], DEFAULT_LANGUAGE[1]) : ((locale.length == 2) ? new Locale(locale[0], locale[1]) : new Locale(locale[0]));
    }
    
    public String getIpCountry(String ip){
        try{
            DatabaseReader reader = new DatabaseReader.Builder(MultiLanguage.database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            return reader.country(ipAddress).getCountry().getIsoCode();
        }catch(Exception e){
            return null;
        }
    }
}