package observer.impl;


import observer.interfaces.BeforeMethodExecute;
import observer.interfaces.BeforeObserverHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ptmind on 2017/8/28.
 */
public class BeforeObserverHolderImpl implements BeforeObserverHolder {

    /**
     * 方法执行前要通知的观察者
     */
    private Set<BeforeMethodExecute> beforeExecObservers;

    public BeforeObserverHolderImpl() {
        this.beforeExecObservers = new HashSet<>();
    }

    @Override
    public void addObserver(BeforeMethodExecute observer) {
        if (null != observer) {
            beforeExecObservers.add(observer);
        }
    }

    @Override
    public void delObserver(BeforeMethodExecute observer) {
        beforeExecObservers.remove(observer);
    }

    @Override
    public Set<BeforeMethodExecute> getBeforeMethodExecuteObservers() {
        //让获取者无法修改
        return Collections.unmodifiableSet(beforeExecObservers);
    }
}
