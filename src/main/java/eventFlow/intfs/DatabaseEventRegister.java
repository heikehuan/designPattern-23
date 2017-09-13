package eventFlow.intfs;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:38
 */
public interface DatabaseEventRegister {

    public void register(DatabaseEventHandler handler);

    public void unRegister(DatabaseEventHandler handler);
}
