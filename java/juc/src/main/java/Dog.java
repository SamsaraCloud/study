/**
 * @ClassName Dog
 * @Description:
 * @Author 86155
 * @Date 2019/12/17 23:23
 * @Version 1.0
 **/
public class Dog {

    public Dog (){
        System.out.println("Dog is loaded by " + this.getClass().getClassLoader());
    }
}
