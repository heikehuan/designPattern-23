package eventFlow.servs;

import eventFlow.events.UploadCompleteEvent;
import eventFlow.events.UploadEvent;
import eventFlow.intfs.UploadCompleteEventPublisher;
import eventFlow.intfs.UploadEventHandler;
import eventFlow.intfs.UploadEventRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:22
 */
@Service
public class UploadHandlerService implements UploadEventHandler {

    @Autowired
    private UploadEventRegister uploadEventRegister;

    @Autowired
    private UploadCompleteEventPublisher uploadCompleteEventPublisher;

    @PostConstruct
    private void init() {
        uploadEventRegister.register(this);
    }

    @Override
    public void onUploadEvent(UploadEvent event) {
        System.out.println("UploadHandlerService 正在上传文件...");
//        sleep();
        System.out.println("UploadHandlerService 上传文件完成，通知EndHandlerService");
        uploadCompleteEventPublisher.publish(new UploadCompleteEvent("UploadHandlerService", event.getProId(), event.getParams()));
    }

    private void sleep() {
        try {
            for (int i = 2; i > 0; i--) {
                Thread.sleep(1000L);
                System.out.println("UploadHandlerService倒数, " + i + "...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
