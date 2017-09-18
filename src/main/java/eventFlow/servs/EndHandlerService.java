package eventFlow.servs;

import com.ptmind.ptengine.api.base.service.impl.LocalBaseServiceImpl;
import com.ptmind.ptengine.api.base.util.TTLMapCache;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseCompleteEvent;
import com.ptmind.ptengine.common.distribute.demo.events.UploadCompleteEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseCompleteEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseCompleteEventRegister;
import com.ptmind.ptengine.common.distribute.demo.intfs.UploadCompleteEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.UploadCompleteEventRegister;
import com.ptmind.ptengine.common.distribute.demo.procs.ProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
//    private TTLMapCache params = new TTLMapCache();
    private ConcurrentMap<String, Object> params = new ConcurrentHashMap<>();

    private ConcurrentMap<String, ReentrantReadWriteLock> locks = new ConcurrentHashMap<>();

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
            logger.debug(proId + " EndHandlerService具备执行条件(DatabaseCompleteEvent, UploadCompleteEvent全部完成), 正在梳理 " + proId + " 业务");
            params.remove(proId);
            logs.remove(proId);
            locks.remove(proId);

            ProcessManager.setStepStatus(proId, "EndStep", Boolean.TRUE);
            if (ProcessManager.isAllStepsSuccess(proId, "BeginStep", "DatabaseStep", "UploadStep"))
                ProcessManager.removeProc(proId);
        }
    }

    @Override
    public void onDatabaseCompleteEvent(DatabaseCompleteEvent event) {
        logger.debug(event.getProId() + " EndHandlerService 收到DatabaseCompleteEvent完成，但是必须也收到UploadCompleteEvent，才能开始业务...");

        //写日志
        Object[] log = (Object[]) logs.get(event.getProId());
        if (null == log) {
            log = new Object[2];
            logs.put(event.getProId(), log);
        }
        log[0] = "这是" + event.getProId() + "的日志";

        //获取锁
        ReentrantReadWriteLock lock = locks.get(event.getProId());
        if (null == lock) {
            lock = new ReentrantReadWriteLock();
            lock = locks.putIfAbsent(event.getProId(), lock);
            if (null == lock)
                lock = locks.get(event.getProId());
        }

        //保存参数
        Object[] ps = (Object[]) params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
            ps = (Object[]) params.putIfAbsent(event.getProId(), ps);
            if (null == ps)
                ps = (Object[]) params.get(event.getProId());
        }

        try {
            lock.writeLock().lockInterruptibly();
            ps[0] = event;

            doBusiness(event.getProId());
        } catch (InterruptedException e) {
            logger.debug(event.getProId() + " 被中断!!!");
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void onUploadCompleteEvent(UploadCompleteEvent event) {
        logger.debug(event.getProId() + " EndHandlerService 收到UploadCompleteEvent完成，但是必须也收到DatabaseCompleteEvent，才能开始业务...");

        //写日志
        Object[] log = (Object[]) logs.get(event.getProId());
        if (null == log) {
            log = new Object[2];
            logs.put(event.getProId(), log);
        }
        log[1] = "这是" + event.getProId() + "的日志";

        //获取锁
        ReentrantReadWriteLock lock = locks.get(event.getProId());
        if (null == lock) {
            lock = new ReentrantReadWriteLock();
            lock = locks.putIfAbsent(event.getProId(), lock);
            if (null == lock)
                lock = locks.get(event.getProId());
        }

        //保存参数
        Object[] ps = (Object[]) params.get(event.getProId());
        if (null == ps) {
            ps = new Object[2];
            ps = (Object[]) params.putIfAbsent(event.getProId(), ps);
            if (null == ps)
                ps = (Object[]) params.get(event.getProId());
        }

        try {
            lock.writeLock().lockInterruptibly();
            ps[1] = event;

            doBusiness(event.getProId());
        } catch (InterruptedException e) {
            logger.debug(event.getProId() + " 被中断!!!");
        } finally {
            lock.writeLock().unlock();
        }
    }
}
