package eventFlow.publishers;

import com.ptmind.ptengine.api.base.util.MethodExecutor;
import com.ptmind.ptengine.common.distribute.demo.events.UploadCompleteEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.UploadCompleteEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.UploadCompleteEventPublisher;
import com.ptmind.ptengine.common.distribute.demo.intfs.UploadCompleteEventRegister;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:02
 */
@Component
public class UploadCompleteEventPublisherImpl implements UploadCompleteEventPublisher, UploadCompleteEventRegister {

    private Map<UploadCompleteEventHandler, MethodExecutor<UploadCompleteEvent>> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(UploadCompleteEventHandler handler) {
        if (null != handler) {
            MethodExecutor<UploadCompleteEvent> methodExecutor = new MethodExecutor<UploadCompleteEvent>(handler, "onUploadCompleteEvent", UploadCompleteEvent.class);
            handlers.put(handler, methodExecutor);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void unRegister(UploadCompleteEventHandler handler) {
        if (null != handler)
            handlers.remove(handler);
    }

    @Override
    public void publish(UploadCompleteEvent event) {
        MethodExecutor<UploadCompleteEvent> executor;
        for (Map.Entry<UploadCompleteEventHandler, MethodExecutor<UploadCompleteEvent>> entry : handlers.entrySet()) {
            executor = entry.getValue();
            executor.execRun(null, event);
        }
    }
}
