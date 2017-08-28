package observer.interfaces;

import java.util.Set;

/**
 * Created by ptmind on 2017/8/28.
 */
public interface OnThrowObserverHolder {

    /**
     * 添加方法执行抛出异常的观察者
     */
    public void addObserver(OnExecptionThrow observer);

    /**
     * 删除方法执行抛出异常的观察者
     *
     * @param observer
     */
    public void delObserver(OnExecptionThrow observer);

    /**
     * 获取方法执行抛出异常的观察者
     *
     * @return
     */
    public Set<OnExecptionThrow> getOnExecptionThrowObservers();
}
