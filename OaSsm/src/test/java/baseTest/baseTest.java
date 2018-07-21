package baseTest;

import api.dto.R;
import org.junit.Test;

/**
 * Created by Administrator on 2018/6/28.
 */
public class baseTest {
    @Test
    public void RTest(){
        System.out.println( R.ok("qwe").put("hello","hello"));
    }
}
