package eventFlow.intfs;


import eventFlow.events.UploadEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:37
 */
public interface UploadEventHandler {

    public void onUploadEvent(UploadEvent event);
}
