package eventFlow.intfs;

import com.ptmind.ptengine.common.distribute.demo.events.DatabaseEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:39
 */
public interface DatabaseEventPublisher {

    public void publish(DatabaseEvent object);
}
