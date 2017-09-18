package eventFlow.publishers;

import com.ptmind.ptengine.api.base.util.MethodExecutor;
import com.ptmind.ptengine.common.distribute.demo.events.BeginEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.BeginEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.BeginEventPublisher;
import com.ptmind.ptengine.common.distribute.demo.intfs.BeginEventRegister;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:02
 */
@Component
public class BeginEventPublisherImpl implements BeginEventPublisher, BeginEventRegister {

    private Map<BeginEventHandler, MethodExecutor<BeginEvent>> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(BeginEventHandler handler) {
        if (null != handler) {
            MethodExecutor<BeginEvent> methodExecutor = new MethodExecutor<BeginEvent>(handler, "onBeginEvent", BeginEvent.class);
            handlers.put(handler, methodExecutor);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void unRegister(BeginEventHandler handler) {
        if (null != handler)
            handlers.remove(handler);
    }

    @Override
    public void publish(BeginEvent event) {
        MethodExecutor<BeginEvent> executor;
        for (Map.Entry<BeginEventHandler, MethodExecutor<BeginEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(null, event);
        }
    }
}
