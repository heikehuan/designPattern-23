package event.controller;

import event.events.BeginMulticastEvent;
import event.events.BeginMulticastPublisher;
import event.events.BeginUnicastEvent;
import event.events.BeginUnicastPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/7 - 18:24
 */
@RestController
public class TestEventController {

    @Autowired
    BeginMulticastPublisher multicastPublisher;

    @Autowired
    BeginUnicastPublisher unicastPublisher;

    @RequestMapping(value = "eventtest", method = RequestMethod.GET)
    public JsonView eventtest() {
        JsonView jsonView = JsonViewFactory.createJsonView();

        multicastPublisher.publishMulticastEvent(new BeginMulticastEvent(100));

        return jsonView;
    }

    @RequestMapping(value = "eventtest2", method = RequestMethod.GET)
    public JsonView eventtest2() {
        JsonView jsonView = JsonViewFactory.createJsonView();

        unicastPublisher.publishBeginUnicastEvent(new BeginUnicastEvent(200));

        return jsonView;
    }
}
