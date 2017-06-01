package publishSubscribe;

/**
 * @author <a href="mailto:huanhuan.zhan@ptmind.com">詹欢欢</a>
 * @since 2017/6/1 - 12:17
 */
//抽象观察类
interface Observer {
    public String getName();

    public void setName(String name);

    public void help(); //声明支援盟友方法

    public void beAttacked(AllyControlCenter acc); //声明遭受攻击方法
}