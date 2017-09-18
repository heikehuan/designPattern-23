package eventFlow.procs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/15 - 11:41
 */
public class ProcessManager {

    private static ConcurrentMap<String, ConcurrentMap<String, Boolean>> processStatus = new ConcurrentHashMap<>();

    /**
     * 查询过程是否发生了错误
     * @param procId
     * @return
     */
    public static boolean isProcError(String procId) {
        ConcurrentMap<String, Boolean> procStatus = processStatus.get(procId);
        if (null != procStatus) {
            return procStatus.containsValue(Boolean.FALSE);
        } else {
            throw new NullPointerException(procId + " process is not define");
        }
    }

    /**
     * 过程状态
     * 如果为null就表示该step正在执行
     * 如果为false就表示该setp执行失败
     * 如果为true就表示该setp执行成功
     * 查询指定的steps是否都成功了
     * @param procId
     * @param steps
     * @return
     */
    public static boolean isAllStepsSuccess(String procId, String... steps) {
        ConcurrentMap<String, Boolean> procStatus = processStatus.get(procId);
        for (String step : steps) {
            if (!procStatus.getOrDefault(step, false))
                return false;
        }

        return true;
    }

    /**
     * 过程状态
     * 如果为null就表示该step正在执行
     * 如果为false就表示该setp执行失败
     * 如果为true就表示该setp执行成功
     * 查询指定的steps是否有其中一步失败了
     * @param procId
     * @param steps
     * @return
     */
    public static boolean isAnyStepsError(String procId, String... steps) {
        ConcurrentMap<String, Boolean> procStatus = processStatus.get(procId);
        for (String step : steps) {
            //1. null -> true -> false    false
            //2. false-> false-> true     true
            //3. true -> true -> false    false
            if (!procStatus.getOrDefault(step, true))
                return true;
        }

        return false;
    }

    /**
     * 设置procId过程中指定的step的状态
     * @param procId
     * @param stepId
     * @param status
     */
    public static void setStepStatus(String procId, String stepId, boolean status) {
        /*if (null == procId)
            throw new NullPointerException("procId can not be null");
        if (null == stepId)
            throw new NullPointerException("stepId can not be null");*/

        ConcurrentMap<String, Boolean> procStatus;
        if (processStatus.containsKey(procId)) {
            procStatus = processStatus.get(procId);
        } else {
            //1 -> success -> return null
            //2 -> false   -> map
            procStatus = processStatus.putIfAbsent(procId, new ConcurrentHashMap<String, Boolean>());
            if (null == procStatus)
                procStatus = processStatus.get(procId);
        }

        procStatus.put(stepId, status);
    }

    /**
     * 删除procId指定的过程
     * @param procId
     */
    public static void removeProc(String procId) {
        if (null != procId)
            processStatus.remove(procId);
    }

//    public static void main(String[] args) {
//        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
//        map.put("name", "1111111111111");
//        System.out.println(map.get("name"));
//        map.putIfAbsent("name", "zzzzz");
//        map.putIfAbsent("name2", "aaaaaa");
//        System.out.println(map.get("name"));
//        System.out.println(map.get("name2"));
//    }

}
