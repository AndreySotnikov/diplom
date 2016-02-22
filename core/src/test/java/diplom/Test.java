package diplom;

import diplom.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 22.02.2016.
 */
public class Test {
    @org.testng.annotations.Test
    public void test() throws Exception{
        Map<String, String> map = new HashMap<>();
        map.put(null, "Karina");
        System.out.println(map.get(null));
    }
}
