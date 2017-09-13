package eventFlow.intfs;


import eventFlow.events.DatabaseCompleteEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:37
 */
public interface DatabaseCompleteEventHandler {

    public void onDatabaseCompleteEvent(DatabaseCompleteEvent event);
}
