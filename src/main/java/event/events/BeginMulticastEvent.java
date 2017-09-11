package event.events;

/**
 * Created by ptmind on 2017/9/7.
 */
public class BeginMulticastEvent {

    private int id;

    public BeginMulticastEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
