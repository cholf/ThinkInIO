package nomal;

import com.thinkin.io.normal.ServerClient;
import com.thinkin.io.normal.UserClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by xugangwen on 17/5/12.
 */
public class NomalTest {

    @Before
    private  void init(){

    }
    @Test
    public void test(){
       //client
        new Thread(new Runnable() {
            public void run() {
                UserClient.sendMsg("hello_io_user");
            }
        }).start();
    }

    @Test
    private  void dd(){
        System.out.printf("sjsdflk");
    }
}
