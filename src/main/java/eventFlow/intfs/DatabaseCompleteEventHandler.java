package eventFlow.intfs;

import com.ptmind.ptengine.common.distribute.demo.events.DatabaseCompleteEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:37
 */
public interface DatabaseCompleteEventHandler {

    public void onDatabaseCompleteEvent(DatabaseCompleteEvent event);
}
