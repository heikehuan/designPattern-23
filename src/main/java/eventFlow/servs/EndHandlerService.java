package eventFlow.servs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

.api.base.service.impl.LocalBaseServiceImpl;
        .api.base.util.TTLMapCache;
        .common.distribute.demo.events.DatabaseCompleteEvent;
        .common.distribute.demo.events.UploadCompleteEvent;
        .common.distribute.demo.intfs.DatabaseCompleteEventHandler;
        .common.distribute.demo.intfs.DatabaseCompleteEventRegister;
        .common.distribute.demo.intfs.UploadCompleteEventHandler;
        .common.distribute.demo.intfs.UploadCompleteEventRegister;
        .common.distribute.demo.procs.ProcessManager;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:32
 */
@Service
public class EndHandlerService extends LocalBaseServiceImpl implements UploadCompleteEventHandler, DatabaseCompleteEventHandler {

    @Autowired
    private UploadCompleteEventRegister uploadCompleteEventRegister;

    @Autowired
    private DatabaseCompleteEventRegister databaseCompleteEventRegister;

    @PostConstruct
    private void init() {
        uploadCompleteEventRegister.register(this);
        databaseCompleteEventRegister.register(this);
    }

    private TTLMapCache logs = new TTLMapCache();

    //最好是一个带生存时间的map，可以用redis实现
    private TTLMapCache params = new TTLMapCache();

    private void doBusiness(String proId) {
        Object[] ps = (Object[]) params.get(proId);
        boolean flag = true; //是否具备执行条件
        for (Object o : ps) {
            if (o == null) {
                flag = false;
                break;
            }
        }

        if (flag) {
            logger.debug("EndHandlerService具备执行条件(DatabaseCompleteEvent, UploadCompleteEvent全部完成), 正在梳理 " + proId + " 业务");
            params.remove(proId);
            logs.remove(proId);

            ProcessManager.setStepStatus(proId, "EndStep", Boolean.TRUE);
            if (ProcessManager.isAllStepsSuccess(proId, "BeginStep", "DatabaseStep", "UploadStep"))
                ProcessManager.removeProc(proId);
        }
    }

    @Override
    public void onDatabaseCompleteEvent(DatabaseCompleteEvent event) {
        logger.debug("EndHandlerService 收到DatabaseCompleteEvent完成，但是必须也收到UploadCompleteEvent，才能开始业务...");

        Object[] ps = (Object[]) params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
        }

        ps[0] = event;
        params.put(event.getProId(), ps);

        doBusiness(event.getProId());
    }

    @Override
    public void onUploadCompleteEvent(UploadCompleteEvent event) {
        logger.debug("EndHandlerService 收到UploadCompleteEvent完成，但是必须也收到DatabaseCompleteEvent，才能开始业务...");

        Object[] log = (Object[]) logs.get(event.getProId());
        if (null == log) {
            log = new Object[2];
        }
        log[1] = "这是" + event.getProId() + "的日志";
        logs.put(event.getProId(), log);

        Object[] ps = (Object[]) params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
        }
        ps[1] = event;
        params.put(event.getProId(), ps);

        doBusiness(event.getProId());
    }

}
