package eventFlow.intfs;


import eventFlow.events.BeginEvent;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:39
 */
public interface BeginEventPublisher {

    public void publish(BeginEvent object);
}
