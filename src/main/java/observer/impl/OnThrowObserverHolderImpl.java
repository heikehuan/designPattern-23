package observer.impl;


import observer.interfaces.OnExecptionThrow;
import observer.interfaces.OnThrowObserverHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ptmind on 2017/8/28.
 */
public class OnThrowObserverHolderImpl implements OnThrowObserverHolder {

    /**
     * 方法抛出异常要通知的观察者
     */
    private Set<OnExecptionThrow> onThrowObservers;

    public OnThrowObserverHolderImpl() {
        this.onThrowObservers = new HashSet<>();
    }

    @Override
    public void addObserver(OnExecptionThrow observer) {
        if (null != observer) {
            onThrowObservers.add(observer);
        }
    }

    @Override
    public void delObserver(OnExecptionThrow observer) {
        onThrowObservers.remove(observer);
    }

    @Override
    public Set<OnExecptionThrow> getOnExecptionThrowObservers() {
        //让获取者无法修改
        return Collections.unmodifiableSet(onThrowObservers);
    }
}
