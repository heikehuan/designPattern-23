package eventFlow.intfs;

import com.ptmind.ptengine.common.distribute.demo.events.UploadEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:39
 */
public interface UploadEventPublisher {

    public void publish(UploadEvent object);
}
