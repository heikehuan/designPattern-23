package eventFlow.servs;

import com.ptmind.ptengine.api.base.service.impl.LocalBaseServiceImpl;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseCompleteEvent;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.*;
import com.ptmind.ptengine.common.distribute.demo.procs.ProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:25
 */
@Service
public class DatabaseHandlerService extends LocalBaseServiceImpl implements DatabaseEventHandler {

    @Autowired
    private DatabaseEventRegister databaseEventRegister;

    @Autowired
    private DatabaseCompleteEventPublisher databaseCompleteEventPublisher;

    private Map<String, Object> logs = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        databaseEventRegister.register(this);
    }

    @Override
    public void onDatabaseEvent(DatabaseEvent event) {
        logger.debug(event.getProId() + " DatabaseHandlerService 正在写操作日志!!!");
        String proId = event.getProId();
        logs.put(proId, "这是" + proId + "的日志");

        logger.debug(event.getProId() + " DatabaseHandlerService 正在操作数据库...");
        logger.debug(event.getProId() + " DatabaseHandlerService 操作数据库完成，通知EndHandlerService");
        ProcessManager.setStepStatus(event.getProId(), "DatabaseStep", Boolean.TRUE);

        databaseCompleteEventPublisher.publish(new DatabaseCompleteEvent("DatabaseHandlerService", event.getProId(), event.getParams()));
    }

}
