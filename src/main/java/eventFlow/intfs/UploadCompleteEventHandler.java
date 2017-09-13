package eventFlow.intfs;


import eventFlow.events.UploadCompleteEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:37
 */
public interface UploadCompleteEventHandler {

    public void onUploadCompleteEvent(UploadCompleteEvent event);
}
