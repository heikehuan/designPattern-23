package eventFlow.ctrls;

import com.ptmind.api.base.rest.common.JsonView;
import com.ptmind.api.base.rest.common.JsonViewFactory;
import com.ptmind.api.base.util.DateUtils;
import com.ptmind.ptengine.api.userInfo.common.bean.UserBean;
import com.ptmind.ptengine.common.distribute.demo.events.BeginEvent;
import com.ptmind.ptengine.common.distribute.demo.intfs.BeginEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/9/13 - 15:33
 */
@RestController
public class TestCtrl {

    @Autowired
    private BeginEventPublisher beginEventPublisher;

    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public JsonView test() throws InterruptedException {
        JsonView jsonView = JsonViewFactory.createJsonView();
        System.out.println(" ----------test event controller , begin " + DateUtils.getSMSCurrTime());

        UserBean userBean = new UserBean();
        userBean.setUid(3L);
        userBean.setUserName("huanhuan.zhan@ptmind.com");
        userBean.setPassword("111111");
        userBean.setChannel(0);
        userBean.setVersion(0);

        //发布事件
        beginEventPublisher.publish(new BeginEvent("TestCtrl", UUID.randomUUID().toString(), userBean));
        System.out.println(" ----------test event controller ,end " + DateUtils.getSMSCurrTime());
        return jsonView;
    }
}
