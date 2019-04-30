package top.fksoft.mediaSyncPlayer.utils.obser;

import java.util.Vector;

public class BroadcastManager <T extends Enum>{
    private Vector<BroadcastClient<T>> broadcastClientList;

    public BroadcastManager() {
        broadcastClientList = new Vector<>();
    }

    public synchronized void bindBroadcast(BroadcastClient<T> broadcastClient){
        if (broadcastClient == null) {
            throw new NullPointerException("Error:broadcastClient is null.");
        }
        if (!broadcastClientList.contains(broadcastClient)){
            broadcastClientList.add(broadcastClient);
        }
    }
    public synchronized void unbindBroadcast(BroadcastClient<T> broadcastClient){
        broadcastClientList.removeElement(broadcastClient);
    }
    public synchronized void destroyBroadcast(){
        broadcastClientList.removeAllElements();
    }
    public void sendBroadCast(T type){
        sendBroadCast(type,new Object[0]);
    }

    public void sendBroadCast(T type,Object ... args){
        BroadcastClient<T> [] clientArray = new BroadcastClient[0];
        synchronized (this){
            clientArray = broadcastClientList.toArray(clientArray);
        }
        for (int i = 0; i < clientArray.length; i++) {
            clientArray[i].update(type,args);
        }
    }

}
