package event.services;

import event.events.BeginUnicastEvent;
import event.events.BeginUnicastHandler;
import event.events.BeginUnicastRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/7 - 18:29
 */
@Service
public class Service4 implements BeginUnicastHandler {
    @Autowired
    private BeginUnicastRegister register;

    @PostConstruct
    private void init() {
        register.register(this);
    }

    @Override
    public void handleBeginUnicastEvent(BeginUnicastEvent event) {
        System.out.println("server4 ===> " + event.getId());
    }
}
