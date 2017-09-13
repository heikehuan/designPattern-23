package eventFlow.servs;

import eventFlow.events.BeginEvent;
import eventFlow.events.DatabaseEvent;
import eventFlow.events.UploadEvent;
import eventFlow.intfs.BeginEventHandler;
import eventFlow.intfs.BeginEventRegister;
import eventFlow.intfs.DatabaseEventPublisher;
import eventFlow.intfs.UploadEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:09
 */
@Service
public class BeginHandlerService implements BeginEventHandler {

    @Autowired
    private BeginEventRegister beginEventRegister;

    @Autowired
    private UploadEventPublisher uploadEventPublisher;

    @Autowired
    private DatabaseEventPublisher databaseEventPublisher;

    @PostConstruct
    private void init() {
        beginEventRegister.register(this);
    }


    @Override
    public void onBeginEvent(BeginEvent event) {
        System.out.println("BeginHandlerService doing business...");
        System.out.println("BeginHandlerService任务完成, 通知文件上传和数据库操作");

        uploadEventPublisher.publish(new UploadEvent("BeginHandlerService", event.getProId(), event.getParams()));
        databaseEventPublisher.publish(new DatabaseEvent("BeginHandlerService", event.getProId(), event.getParams()));
    }
}
