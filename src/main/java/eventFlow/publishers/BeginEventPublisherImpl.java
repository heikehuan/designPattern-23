package eventFlow.publishers;

import eventFlow.events.BeginEvent;
import eventFlow.intfs.BeginEventHandler;
import eventFlow.intfs.BeginEventPublisher;
import eventFlow.intfs.BeginEventRegister;
import org.springframework.stereotype.Component;
import util.MethodExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

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
        CountDownLatch latch = new CountDownLatch(handlers.entrySet().size());
        MethodExecutor<BeginEvent> executor;
        for (Map.Entry<BeginEventHandler, MethodExecutor<BeginEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(latch, event);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
