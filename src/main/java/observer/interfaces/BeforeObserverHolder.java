package observer.interfaces;

import java.util.Set;

/**
 * Created by hyp on 2017/8/28.
 * 观察者模式和aop切面的代理模式的区别是
 * 观察者只是观察,不会影响程序的执行流程
 * 而aop可以影响甚至阻断程序流程的执行,
 * 例如用aop做参数校验,校验不过就不往下执行了
 */
public interface BeforeObserverHolder {

    /**
     * 添加方法执行前的观察者
     */
    public void addObserver(BeforeMethodExecute observer);

    /**
     * 删除方法执行前的观察者
     *
     * @param observer
     */
    public void delObserver(BeforeMethodExecute observer);

    /**
     * 获取方法执行前的观察者
     *
     * @return
     */
    public Set<BeforeMethodExecute> getBeforeMethodExecuteObservers();
}
