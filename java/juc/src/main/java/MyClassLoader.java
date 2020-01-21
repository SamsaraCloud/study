import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName com.MyClassLoader
 * @Description:
 * @Author 86155
 * @Date 2019/12/17 22:16
 * @Version 1.0
 **/
@Data
public class MyClassLoader extends ClassLoader {

    private String name; // 类加载器名字
    private String path; // 加载类的路径
    private String fileType = ".class"; // 加载类的扩展名

    public MyClassLoader(String name) {
        super();
        this.name = name;
    }

    public MyClassLoader(ClassLoader c, String name) {
        super(c);
        this.name = name;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = this.loadClassData(name);

        return this.defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] loadClassData(String name) {
        byte [] data = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
//            this.name = StringUtils.replace(this.name, ".","\\");
            this.name = this.name.replace(".", "\\");
            is = new FileInputStream(new File(path + name + fileType));
            baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = is.read()) != -1){
                baos.write(len);
            }

            data = baos.toByteArray();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (baos != null){
                    baos.close();
                }
                if (is != null){
                    is.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return data;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("g:\\myApp\\serverlib\\");

        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        loader2.setPath("g:\\myApp\\clientlib\\");

        MyClassLoader loader3 = new MyClassLoader(loader2, "loader3");
        loader3.setPath("g:\\myApp\\otherlib\\");

        test(loader2);
        test(loader3);
    }

    public static void test (ClassLoader classLoader) throws Exception{
        Class<?> aClass = classLoader.loadClass("Animal");
        Object o = aClass.newInstance();
    }
}