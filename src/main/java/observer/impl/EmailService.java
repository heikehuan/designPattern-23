package observer.impl;


import observer.interfaces.AfterMethodExecute;
import observer.interfaces.AfterObserverHolder;
import observer.interfaces.OnExecptionThrow;
import observer.interfaces.OnThrowObserverHolder;

import javax.annotation.PostConstruct;

/**
 * Created by ptmind on 2017/8/28.
 */
@Service
public class EmailService implements AfterMethodExecute, OnExecptionThrow {

    @Autowired
    @Qualifier("methodOneAfterObserverHolder")
    private AfterObserverHolder methodOneBeforeObserverHolder;

    @Autowired
    @Qualifier("methodOneOnThrowObserverHolder")
    private OnThrowObserverHolder methodOneOnThrowObserverHolder;

    @Autowired
    @Qualifier("methodTwoAfterObserverHolder")
    private AfterObserverHolder methodTwoBeforeObserverHolder;

    /**
     * 构造结束后,把自己加入到ObserverHolder中
     */
    @PostConstruct
    private void init() {
        methodOneBeforeObserverHolder.addObserver(this);
        methodOneOnThrowObserverHolder.addObserver(this);
        methodTwoBeforeObserverHolder.addObserver(this);
    }

    @Override
    public void afterExec(Object params, Object returnVal) {
        System.out.println("方法执行成功了, 我向用户发送邮件, 通知用户执行成功!!!");
    }

    @Override
    public void onThrow(Object params, Throwable throwable) {
        System.out.println("方法执行异常了, 向用户发送邮件, 通知用户操作失败!!!");
    }
}
