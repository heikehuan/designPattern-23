package observer.impl;


import observer.interfaces.AfterMethodExecute;
import observer.interfaces.AfterObserverHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ptmind on 2017/8/28.
 */
public class AfterObserverHolderImpl implements AfterObserverHolder {

    /**
     * 方法执行后要通知的观察者
     */
    private Set<AfterMethodExecute> afterExecObservers;

    public AfterObserverHolderImpl() {
        this.afterExecObservers = new HashSet<>();
    }

    @Override
    public void addObserver(AfterMethodExecute observer) {
        if (null != observer) {
            afterExecObservers.add(observer);
        }
    }

    @Override
    public void delObserver(AfterMethodExecute observer) {
        afterExecObservers.remove(observer);
    }

    @Override
    public Set<AfterMethodExecute> getAfterMethodExecuteObservers() {
        //让获取者无法修改
        return Collections.unmodifiableSet(afterExecObservers);
    }
}
