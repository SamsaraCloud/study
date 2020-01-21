/**
 * @ClassName Animal
 * @Description:
 * @Author 86155
 * @Date 2019/12/17 23:23
 * @Version 1.0
 **/
public class Animal {

    public Animal (){
        System.out.println("Animal is loaded by " + this.getClass().getClassLoader());
        new Dog();
    }
}
