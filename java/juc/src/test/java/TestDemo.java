import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TestDemo
 * @Description:
 * @Author yangyun
 * @Date 2019/10/17 0017 10:10
 * @Version 1.0
 **/
public class TestDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,5,9,3,5,7,5);
        Comparator<Integer> com = (a,b) -> b.compareTo(a);
        list.stream().sorted(com).collect(Collectors.toList()).stream().forEach(s -> {
            System.out.println(s);
        });
    }
}
