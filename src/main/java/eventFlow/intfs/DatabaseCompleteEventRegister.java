package eventFlow.intfs;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:38
 */
public interface DatabaseCompleteEventRegister {

    public void register(DatabaseCompleteEventHandler handler);

    public void unRegister(DatabaseCompleteEventHandler handler);
}
