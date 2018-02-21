import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestFlowSuccess {
    private WebDriver driver;
    private String trainType;//0--all,1--GaoTie,2--others
    private String baseUrl;
    //获取指定位数的随机字符串(包含数字,0<length)

    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    public static void login(WebDriver driver,String username,String password){
        //Add random delay to emulate the waiting between user click
        try{
            Thread.sleep(new Random().nextInt(7000) + 3000);
        }catch(Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.id("flow_four_page")).click();
        driver.findElement(By.id("flow_advance_reserve_login_email")).clear();
        driver.findElement(By.id("flow_advance_reserve_login_email")).sendKeys(username);
        driver.findElement(By.id("flow_advance_reserve_login_password")).clear();
        driver.findElement(By.id("flow_advance_reserve_login_password")).sendKeys(password);
        driver.findElement(By.id("flow_advance_reserve_login_button")).click();
    }

    @BeforeClass
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "F:/gitwork/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.141.212.21";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    //Test Flow Preserve Step 1: - Login
    public void testLogin()throws Exception{

        driver.get(baseUrl + "/");


        //define username and password
        String username = "fdse_microservices@163.com";
        String password = "DefaultPassword";

        //call function login
        login(driver,username,password);
        Thread.sleep(1000);

        //get login status
//        String statusLogin = driver.findElement(By.id("flow_preserve_login_msg")).getText();
//        if("".equals(statusLogin)) {
//            System.out.println("Failed to Login! Status is Null!");
//        } else if(statusLogin.startsWith("Success")) {
//            System.out.println("Success to Login! Status:" + statusLogin);
//        } else {
//            System.out.println("Failed to Login! Status:" + statusLogin);
//        }
//        Assert.assertEquals(statusLogin.startsWith("Success"),true);
//
//        Thread.sleep(1000);
//        String js = "document.getElementById('flow_two_page').scrollIntoView(false)";
//        ((JavascriptExecutor)driver).executeScript(js);
//        Thread.sleep(1000);
//
//        driver.findElement(By.id("flow_two_page")).click();
    }




    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
    }
}
