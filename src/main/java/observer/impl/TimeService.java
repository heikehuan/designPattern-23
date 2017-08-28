package observer.impl;


import observer.interfaces.*;

import javax.annotation.PostConstruct;

/**
 * Created by ptmind on 2017/8/28.
 */
public class TimeService implements BeforeMethodExecute, AfterMethodExecute, OnExecptionThrow {

    @Autowired
    @Qualifier("methodOneBeforeObserverHolder")
    private BeforeObserverHolder methodOneBeforeObserverHolder;

    @Autowired
    @Qualifier("methodOneAfterObserverHolder")
    private AfterObserverHolder methodOneAfterObserverHolder;

    @Autowired
    @Qualifier("methodOneOnThrowObserverHolder")
    private OnThrowObserverHolder methodOneOnThrowObserverHolder;

    private long startTime;

    @PostConstruct
    private void init() {
        methodOneBeforeObserverHolder.addObserver(this);
        methodOneAfterObserverHolder.addObserver(this);
        methodOneOnThrowObserverHolder.addObserver(this);
    }

    @Override
    public void beforeExec(Object params) {
        System.out.println("方法执行前, 记录执行开始时间.");
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void afterExec(Object params, Object returnVal) {
        System.out.println("方法执行后, 记录执行结束时间, 并计算结果");
        long end = System.currentTimeMillis();
        System.out.println("执行时间为: " + (end - startTime) + " ms.");
    }

    @Override
    public void onThrow(Object params, Throwable throwable) {
        System.out.println("方法执抛出异常, 记录执行结束时间, 并计算结果");
        long end = System.currentTimeMillis();
        System.out.println("执行时间为: " + (end - startTime) + " ms.");
    }
}
