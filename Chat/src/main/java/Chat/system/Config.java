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
        @Setting public int distance = 100; // in blocks
        @Setting public long cooldown = 0; // in seconds
        @Setting public double cost = 0;
    }
    @ConfigSerializable
    public static class TradeInfo {
        @Setting public long cooldown = 120; // in seconds
        @Setting public double cost = 0;
    }
    @ConfigSerializable
    public static class WorldInfo {
        @Setting public long cooldown = 0; // in seconds
        @Setting public double cost = 500;
    }
}
