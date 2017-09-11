package event.events;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ptmind on 2017/9/7.
 */
@Component
public class BeginMulticastPublisherImpl implements BeginMulticastPublisher, BeginMulticastRegister {

    private List<BeginMulticastHandler> handlers = new CopyOnWriteArrayList<>();

    @Override
    public void register(BeginMulticastHandler handler) {
        if (null != handler) {
            if (!handlers.contains(handler))
                handlers.add(handler);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void publishMulticastEvent(BeginMulticastEvent event) {
        for (BeginMulticastHandler handler : handlers) {
            handler.handleBeginMulticastEvent(event);
        }
    }
}
