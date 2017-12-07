package bwie.com.qqshangchuan.net;

/**
 * Created by 不将就 on 2017/12/7.
 */

public interface Api {
    boolean ISONLINE = true;
    String DEV = "http://169.27.23.105";
    public static String ONLINE = "http://120.27.23.105";

    public static String HOST = ISONLINE ? ONLINE : DEV;
    public static String Login = HOST + "/user/login";
    public static String REGISTER = HOST + "/user/reg";
    public static String CATAGORY = HOST + "/product/getCatagory";
    public static String PRODUCT_CATAGORY = HOST + "/product/getProductCatagory?cid=%s";
    public static String PRODUCT_CATAGORY_LIST = HOST + "/product/getProducts";
}
