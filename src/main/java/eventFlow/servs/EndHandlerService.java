package eventFlow.servs;

import eventFlow.events.DatabaseCompleteEvent;
import eventFlow.events.UploadCompleteEvent;
import eventFlow.intfs.DatabaseCompleteEventHandler;
import eventFlow.intfs.DatabaseCompleteEventRegister;
import eventFlow.intfs.UploadCompleteEventHandler;
import eventFlow.intfs.UploadCompleteEventRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:32
 */
@Service
public class EndHandlerService implements UploadCompleteEventHandler, DatabaseCompleteEventHandler {

    @Autowired
    private UploadCompleteEventRegister uploadCompleteEventRegister;

    @Autowired
    private DatabaseCompleteEventRegister databaseCompleteEventRegister;
    private Map<String, Object[]> params = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        uploadCompleteEventRegister.register(this);
        databaseCompleteEventRegister.register(this);
    }

    private void doBusiness(String proId) {
        Object[] ps = params.get(proId);
        boolean flag = true; //是否具备执行条件
        for (Object o : ps) {
            if (o == null) {
                flag = false;
                break;
            }
        }

        if (flag) {
            System.out.println("EndHandlerService具备执行条件(DatabaseCompleteEvent, UploadCompleteEvent全部完成), 正在梳理 " + proId + " 业务");
            params.remove(proId);
        }
    }

    @Override
    public void onDatabaseCompleteEvent(DatabaseCompleteEvent event) {
        System.out.println("EndHandlerService 收到DatabaseCompleteEvent完成，但是必须也收到UploadCompleteEvent，才能开始业务...");

        Object[] ps = params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
        }

        ps[0] = event.getParams();
        params.put(event.getProId(), ps);

        doBusiness(event.getProId());
    }

    @Override
    public void onUploadCompleteEvent(UploadCompleteEvent event) {
        System.out.println("EndHandlerService 收到UploadCompleteEvent完成，但是必须也收到DatabaseCompleteEvent，才能开始业务...");

        Object[] ps = params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
        }

        ps[1] = event.getParams();
        params.put(event.getProId(), ps);

        doBusiness(event.getProId());
    }
}
