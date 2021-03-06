package eventFlow.publishers;

import com.ptmind.ptengine.api.base.util.MethodExecutor;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseCompleteEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseCompleteEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseCompleteEventPublisher;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseCompleteEventRegister;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:02
 */
@Component
public class DatabaseCompleteEventPublisherImpl implements DatabaseCompleteEventPublisher, DatabaseCompleteEventRegister {

    private Map<DatabaseCompleteEventHandler, MethodExecutor<DatabaseCompleteEvent>> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(DatabaseCompleteEventHandler handler) {
        if (null != handler) {
            MethodExecutor<DatabaseCompleteEvent> methodExecutor = new MethodExecutor<DatabaseCompleteEvent>(handler, "onDatabaseCompleteEvent", DatabaseCompleteEvent.class);
            handlers.put(handler, methodExecutor);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void unRegister(DatabaseCompleteEventHandler handler) {
        if (null != handler)
            handlers.remove(handler);
    }

    @Override
    public void publish(DatabaseCompleteEvent event) {
        MethodExecutor<DatabaseCompleteEvent> executor;
        for (Map.Entry<DatabaseCompleteEventHandler, MethodExecutor<DatabaseCompleteEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(null, event);
        }
    }
}
