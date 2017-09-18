package eventFlow.servs;

import com.ptmind.ptengine.api.base.service.impl.LocalBaseServiceImpl;
import com.ptmind.ptengine.common.distribute.demo.events.DatabaseEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseEventHandler;
import com.ptmind.ptengine.common.distribute.demo.intfs.DatabaseEventRegister;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/14 - 14:43
 */
//@Service
public class RandomExceptionService extends LocalBaseServiceImpl implements DatabaseEventHandler {
    @Autowired
    private DatabaseEventRegister databaseEventRegister;

    @PostConstruct
    private void init() {
        databaseEventRegister.register(this);
    }

    @Override
    public void onDatabaseEvent(DatabaseEvent event) {
        logger.debug("RandomExceptionService 正在操作数据库...");

        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        if (rnd.nextInt(0, 2) % 2 == 1) {
            logger.debug("RandomExceptionService 操作数据库异常, 业务出错了");
        } else {
            logger.debug("RandomExceptionService 操作数据库完成.");
        }
    }
}
