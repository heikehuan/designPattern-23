package eventFlow.events;

import java.util.UUID;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 16:30
 */
public class UploadCompleteEvent {

    /**
     * 事件id,每个事件的id都是唯一的,如果接收到的事件id是一致的,表明
     * 这个事件是重传的事件,例如A系统向B系统发送消息,如果第一次发送过程中
     * 这个消息在网络中消失或被丢包了，那么A将向B系统重新发送本事件,
     * 又或者B系统收到了A系统发送的消息id=100,但是B系统处理完业务后又收到
     * id=100的事件，则证明该事件是重复发送，故不做处理。
     */
    private String id;

    /**
     * 事件源,特指引发事件的组件instance
     */
    private Object src;

    /**
     * 过程id，同一个proId表示同一个业务流程
     */
    private String proId;

    /**
     * 事件的发生事件
     */
    private long date;

    /**
     * 事件携带的参数
     */
    private Object params;

    public UploadCompleteEvent(Object src, String proId, Object params) {
        this.id = UUID.randomUUID().toString();
        this.src = src;
        this.proId = proId;
        this.date = System.currentTimeMillis();
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public Object getSrc() {
        return src;
    }

    public String getProId() {
        return proId;
    }

    public long getDate() {
        return date;
    }

    public Object getParams() {
        return params;
    }
}
