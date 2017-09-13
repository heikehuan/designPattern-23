package eventFlow.publishers;

import eventFlow.events.UploadEvent;
import eventFlow.intfs.UploadEventHandler;
import eventFlow.intfs.UploadEventPublisher;
import eventFlow.intfs.UploadEventRegister;
import org.springframework.stereotype.Component;
import util.MethodExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:02
 */
@Component
public class UploadEventPublisherImpl implements UploadEventPublisher, UploadEventRegister {

    private Map<UploadEventHandler, MethodExecutor<UploadEvent>> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(UploadEventHandler handler) {
        if (null != handler) {
            MethodExecutor<UploadEvent> methodExecutor = new MethodExecutor<UploadEvent>(handler, "onUploadEvent", UploadEvent.class);
            handlers.put(handler, methodExecutor);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void unRegister(UploadEventHandler handler) {
        if (null != handler)
            handlers.remove(handler);
    }

    @Override
    public void publish(UploadEvent event) {
        MethodExecutor<UploadEvent> executor;
        for (Map.Entry<UploadEventHandler, MethodExecutor<UploadEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(null, event);
        }
    }
}
