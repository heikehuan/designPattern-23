package observer.ctrls;


import observer.impl.BusinessService;
import observer.interfaces.AfterObserverHolder;
import observer.interfaces.BeforeObserverHolder;
import observer.interfaces.OnThrowObserverHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ptmind on 2017/8/28.
 */
public class ExampleCtrl {

    @Autowired
    @Qualifier("methodOneOnThrowObserverHolder")
    public OnThrowObserverHolder methodOneOnThrowObserverHolder;
    @Autowired
    @Qualifier("methodTwoOnThrowObserverHolder")
    public OnThrowObserverHolder methodTwoOnThrowObserverHolder;
    @Autowired
    private BusinessService service;
    @Autowired
    @Qualifier("methodOneBeforeObserverHolder")
    private BeforeObserverHolder methodOneBeforeObserverHolder;
    @Autowired
    @Qualifier("methodOneAfterObserverHolder")
    private AfterObserverHolder methodOneAfterObserverHolder;
    @Autowired
    @Qualifier("methodTwoBeforeObserverHolder")
    private BeforeObserverHolder methodTwoBeforeObserverHolder;

    @Autowired
    @Qualifier("methodTwoAfterObserverHolder")
    private AfterObserverHolder methodTwoAfterObserverHolder;

    @RequestMapping(path = "/methodOne")
    public String methodOne(String name, String password) {
        String returnVal;

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("password", password);

        try {
            //通知方法执行前的观察者
            Set<BeforeMethodExecute> beforeObservers = methodOneBeforeObserverHolder.getBeforeMethodExecuteObservers();
            for (BeforeMethodExecute observer : beforeObservers) {
                observer.beforeExec(params);
            }

            //业务逻辑
            returnVal = service.methodOne(name, password);

            //通知方法执行后的观察者
            Set<AfterMethodExecute> afterObservers = methodOneAfterObserverHolder.getAfterMethodExecuteObservers();
            for (AfterMethodExecute observer : afterObservers) {
                observer.afterExec(params, returnVal);
            }
        } catch (Exception e) {
            //通知方法异常的观察者
            Set<OnExecptionThrow> onThrowObservers = methodOneOnThrowObserverHolder.getOnExecptionThrowObservers();
            for (OnExecptionThrow observer : onThrowObservers) {
                observer.onThrow(params, e);
            }

            //抛出异常时给前端一个错误提示
            returnVal = "error";
        }

        return returnVal;
    }

    @RequestMapping(path = "/methodTwo")
    public String methodTwo(Map<String, String> params) {
        String returnVal;

        try {
            //通知方法执行前的观察者
            Set<BeforeMethodExecute> beforeObservers = methodTwoBeforeObserverHolder.getBeforeMethodExecuteObservers();
            for (BeforeMethodExecute observer : beforeObservers) {
                observer.beforeExec(params);
            }

            //业务逻辑
            returnVal = service.methodTwo(params);

            //通知方法执行后的观察者
            Set<AfterMethodExecute> afterObservers = methodTwoAfterObserverHolder.getAfterMethodExecuteObservers();
            for (AfterMethodExecute observer : afterObservers) {
                observer.afterExec(params, returnVal);
            }
        } catch (Exception e) {
            //通知方法异常的观察者
            Set<OnExecptionThrow> onThrowObservers = methodTwoOnThrowObserverHolder.getOnExecptionThrowObservers();
            for (OnExecptionThrow observer : onThrowObservers) {
                observer.onThrow(params, e);
            }

            //抛出异常时给前端一个错误提示
            returnVal = "error";
        }

        return returnVal;
    }
}
