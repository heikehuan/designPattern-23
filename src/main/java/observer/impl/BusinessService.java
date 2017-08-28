package observer.impl;


import java.util.Map;

/**
 * Created by ptmind on 2017/8/28.
 */
@Service
public class BusinessService {

    public String methodOne(String name, String password) {
        return "methodOne";
    }


    public String methodTwo(Map<String, String> params) {
        return "methodTwo";
    }
}
