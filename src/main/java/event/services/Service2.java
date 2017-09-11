package event.services;

import event.events.BeginMulticastEvent;
import event.events.BeginMulticastHandler;
import event.events.BeginMulticastRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/7 - 18:15
 */
@Service
public class Service2 implements BeginMulticastHandler {

    @Autowired
    private BeginMulticastRegister register;

    @PostConstruct
    private void init() {
        register.register(this);
    }

    @Override
    @Async
    public void handleBeginMulticastEvent(BeginMulticastEvent event) {
        System.out.println("server2 ===> " + event.getId());
    }
}
