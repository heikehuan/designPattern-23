package observer.impl;


import observer.interfaces.OnExecptionThrow;
import observer.interfaces.OnThrowObserverHolder;

import javax.annotation.PostConstruct;

/**
 * Created by ptmind on 2017/8/28.
 */
@Service
public class ExceptionService implements OnExecptionThrow {

    @Autowired
    @Qualifier("methodOneOnThrowObserverHolder")
    private OnThrowObserverHolder methodOneOnThrowObserverHolder;

    @Autowired
    @Qualifier("methodTwoOnThrowObserverHolder")
    private OnThrowObserverHolder methodTwoOnThrowObserverHolder;

    /**
     * 构造结束后,把自己加入到ObserverHolder中
     */
    @PostConstruct
    private void init() {
        methodOneOnThrowObserverHolder.addObserver(this);
        methodTwoOnThrowObserverHolder.addObserver(this);
    }

    @Override
    public void onThrow(Object params, Throwable throwable) {
        System.out.println("方法抛出异常了, 我要打印日子, 并做一些补救措施!!!");
    }
}
