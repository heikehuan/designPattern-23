package eventFlow.servs;

import com.ptmind.ptengine.api.base.service.impl.LocalBaseServiceImpl;
import com.ptmind.ptengine.common.distribute.demo.events.*;
import com.ptmind.ptengine.common.distribute.demo.intfs.*;
import com.ptmind.ptengine.common.distribute.demo.procs.ProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:09
 */
@Service
public class BeginHandlerService extends LocalBaseServiceImpl implements BeginEventHandler {

    @Autowired
    private BeginEventRegister beginEventRegister;

    @Autowired
    private UploadEventPublisher uploadEventPublisher;

    @Autowired
    private DatabaseEventPublisher databaseEventPublisher;


    private Map<String, Object> logs = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        beginEventRegister.register(this);
    }


    @Override
    public void onBeginEvent(BeginEvent event) {
        logger.debug(event.getProId() + " BeginHandlerService 正在写操作日志!!!");
        String proId = event.getProId();
        logs.put(proId, "这是" + proId + "的日志");

        logger.debug(event.getProId() + " BeginHandlerService doing business...");
        logger.debug(event.getProId() + " BeginHandlerService任务完成, 通知文件上传和数据库操作");
        ProcessManager.setStepStatus(event.getProId(), "BeginStep", Boolean.TRUE);

        uploadEventPublisher.publish(new UploadEvent("BeginHandlerService", event.getProId(), event.getParams()));
        databaseEventPublisher.publish(new DatabaseEvent("BeginHandlerService", event.getProId(), event.getParams()));
    }

}
