package java0.nio01;

import cn.hutool.http.HttpUtil;

/**
 * @author qrn
 * @version 1.0
 * @date 2021/1/22 10:26
 * 使用 hutool 的http 直接访问请求nio中例子:
 */
public class HttpUtilDemo {
    public static void main(String[] args) {
        String result3= HttpUtil.get("http://localhost:8802");
        System.out.println(result3);
    }
}
