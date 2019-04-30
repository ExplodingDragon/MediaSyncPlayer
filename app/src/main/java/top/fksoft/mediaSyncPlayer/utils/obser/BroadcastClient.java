package top.fksoft.mediaSyncPlayer.utils.obser;


public interface BroadcastClient<T extends Enum>{
    void update(T type, Object... data);

}
