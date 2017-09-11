package event.events;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ptmind on 2017/9/7.
 */
@Component
public class BeginUnicastPublisherImpl implements BeginUnicastPublisher, BeginUnicastRegister {

    private List<BeginUnicastHandler> handlers = new CopyOnWriteArrayList<>();

    @Override
    public void register(BeginUnicastHandler handler) {
        if (null != handler) {
            if (!handlers.contains(handler))
                handlers.add(handler);
        } else {
            throw new NullPointerException("handler is null.");
        }
    }

    @Override
    public void publishBeginUnicastEvent(BeginUnicastEvent event) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        BeginUnicastHandler handler = handlers.get(rnd.nextInt(0, handlers.size()));
        handler.handleBeginUnicastEvent(event);
    }
}

