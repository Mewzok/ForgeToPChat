package Chat.system;

import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Config
{
    public final static TypeToken<Config> type = TypeToken.of(Config.class);
    @Setting public String prefix;
    @Setting public LocalInfo localInfo = new LocalInfo();
    @Setting public WorldInfo worldInfo = new WorldInfo();
    @Setting public TradeInfo tradeInfo = new TradeInfo();
    @ConfigSerializable
    public static class LocalInfo {
        @Setting public int distance = 100;
        @Setting public double cooldown = 0;
        @Setting public double cost = 0;
    }
    @ConfigSerializable
    public static class TradeInfo {
        @Setting public double cooldown = 1200;
        @Setting public double cost = 0;
    }
    @ConfigSerializable
    public static class WorldInfo {
        @Setting public double cooldown = 0;
        @Setting public double cost = 0;
    }
}
