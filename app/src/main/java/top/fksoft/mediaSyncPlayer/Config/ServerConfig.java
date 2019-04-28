package top.fksoft.mediaSyncPlayer.Config;

public class ServerConfig {
    private int udpBroadPort = 25577;




    private static ServerConfig config = null;
    public static ServerConfig newInstance() {
        if (config == null)
            config = new ServerConfig();
        return config;
    }

}
