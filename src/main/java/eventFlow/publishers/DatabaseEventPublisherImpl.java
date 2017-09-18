package eventFlow.publishers;

import com.ptmind.ptengine.api.base.util.MethodExecutor;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseEventPublisher;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseEventRegister;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:02
 */
@Component
public class DatabaseEventPublisherImpl implements DatabaseEventPublisher, DatabaseEventRegister {

    private Map<DatabaseEventHandler, MethodExecutor<DatabaseEvent>> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(DatabaseEventHandler handler) {
        if (null != handler) {
            MethodExecutor<DatabaseEvent> methodExecutor = new MethodExecutor<DatabaseEvent>(handler, "onDatabaseEvent", DatabaseEvent.class);
            handlers.put(handler, methodExecutor);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void unRegister(DatabaseEventHandler handler) {
        if (null != handler)
            handlers.remove(handler);
    }

    @Override
    public void publish(DatabaseEvent event) {
        MethodExecutor<DatabaseEvent> executor;
        for (Map.Entry<DatabaseEventHandler, MethodExecutor<DatabaseEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(null, event);
        }
    }
}
