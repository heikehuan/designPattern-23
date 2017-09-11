package event.events;

/**
 * Created by ptmind on 2017/9/7.
 */
public class BeginUnicastEvent {

    private int id;

    private String src;


    public BeginUnicastEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
