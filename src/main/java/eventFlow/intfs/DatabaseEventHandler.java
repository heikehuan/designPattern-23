package eventFlow.intfs;

.common.distribute.demo.events.DatabaseEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:37
 */
public interface DatabaseEventHandler {

    public void onDatabaseEvent(DatabaseEvent event);
}
