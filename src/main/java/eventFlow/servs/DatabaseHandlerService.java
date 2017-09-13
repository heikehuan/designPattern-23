package eventFlow.servs;

import eventFlow.events.DatabaseCompleteEvent;
import eventFlow.events.DatabaseEvent;
import eventFlow.intfs.DatabaseCompleteEventPublisher;
import eventFlow.intfs.DatabaseEventHandler;
import eventFlow.intfs.DatabaseEventRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:25
 */
@Service
public class DatabaseHandlerService implements DatabaseEventHandler {

    @Autowired
    private DatabaseEventRegister databaseEventRegister;

    @Autowired
    private DatabaseCompleteEventPublisher databaseCompleteEventPublisher;

    @PostConstruct
    private void init() {
        databaseEventRegister.register(this);
    }

    @Override
    public void onDatabaseEvent(DatabaseEvent event) {
        System.out.println("DatabaseHandlerService 正在操作数据库...");
//        sleep();
        System.out.println("DatabaseHandlerService 操作数据库完成，通知EndHandlerService");
        databaseCompleteEventPublisher.publish(new DatabaseCompleteEvent("DatabaseHandlerService", event.getProId(), event.getParams()));
    }

    private void sleep() {
        try {
            for (int i = 1; i > 0; i--) {
                Thread.sleep(1000L);
                System.out.println("DatabaseHandlerService倒数, " + i + "...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
