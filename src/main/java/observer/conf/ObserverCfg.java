package observer.conf;


import observer.impl.AfterObserverHolderImpl;
import observer.impl.BeforeObserverHolderImpl;
import observer.impl.OnThrowObserverHolderImpl;
import observer.interfaces.AfterObserverHolder;
import observer.interfaces.BeforeObserverHolder;
import observer.interfaces.OnThrowObserverHolder;

/**
 * Created by ptmind on 2017/8/28.
 */
@Configuration
public class ObserverCfg {

    @Bean(name = "methodOneBeforeObserverHolder")
    private BeforeObserverHolder methodOneBeforeObserverHolder() {
        return new BeforeObserverHolderImpl();
    }

    @Bean(name = "methodOneAfterObserverHolder")
    public AfterObserverHolder methodOneAfterObserverHolder() {
        return new AfterObserverHolderImpl();
    }

    @Bean(name = "methodOneOnThrowObserverHolder")
    public OnThrowObserverHolder methodOneOnThrowObserverHolder() {
        return new OnThrowObserverHolderImpl();
    }

    @Bean(name = "methodTwoBeforeObserverHolder")
    public BeforeObserverHolder methodTwoObserverHolder() {
        return new BeforeObserverHolderImpl();
    }

    @Bean(name = "methodTwoAfterObserverHolder")
    public AfterObserverHolder methodTwoAfterObserverHolder() {
        return new AfterObserverHolderImpl();
    }

    @Bean(name = "methodTwoOnThrowObserverHolder")
    public OnThrowObserverHolder methodTwoOnThrowObserverHolder() {
        return new OnThrowObserverHolderImpl();
    }
}
