package eventFlow.intfs;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:38
 */
public interface UploadEventRegister {

    public void register(UploadEventHandler handler);

    public void unRegister(UploadEventHandler handler);
}
