package eventFlow.servs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

.api.base.service.impl.LocalBaseServiceImpl;
        .common.distribute.demo.events.UploadCompleteEvent;
        .common.distribute.demo.events.UploadEvent;
        .common.distribute.demo.intfs.*;
        .common.distribute.demo.procs.ProcessManager;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:22
 */
@Service
public class UploadHandlerService extends LocalBaseServiceImpl implements UploadEventHandler {

    @Autowired
    private UploadEventRegister uploadEventRegister;

    @Autowired
    private UploadCompleteEventPublisher uploadCompleteEventPublisher;

    private Map<String, Object> logs = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        uploadEventRegister.register(this);
    }

    @Override
    public void onUploadEvent(UploadEvent event) {
        logger.debug("UploadHandlerService 正在写操作日志!!!");
        String proId = event.getProId();
        logs.put(proId, "这是" + proId + "的日志");

        logger.debug("UploadHandlerService 正在上传文件...");
        logger.debug("UploadHandlerService 上传文件完成，通知EndHandlerService");
        ProcessManager.setStepStatus(event.getProId(), "UploadStep", Boolean.TRUE);

        uploadCompleteEventPublisher.publish(new UploadCompleteEvent("UploadHandlerService", event.getProId(), event.getParams()));
    }

}
