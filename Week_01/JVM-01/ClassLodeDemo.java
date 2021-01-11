package com.company;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qrn
 * @version 1.0
 * 1.使用 字节流读取文件，并转换成字节数组
 * 2.处理字节数组，x=255-x
 * 3.反射加载，调用对象的hello方法
 * @date 2021/1/10 16:16
 */
public class ClassLodeDemo extends   ClassLoader{

        public static void main(String[] args)  {
            try{
                Class<?> aClass = new ClassLodeDemo().findClass("/Users/qurenneng/Downloads/Hello/Hello.xlass");
                Object o =aClass.newInstance();
                Method method =aClass.getDeclaredMethod("hello", null); //调用对象的方法
                method.invoke(o, null);
            }catch (IllegalAccessException e){
            }catch (InstantiationException e){
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    /**
     * 对读取的字节数组进行处理:
     * @param filePath
     * @return
     */
    protected  Class<?> findClass(String filePath){
            byte[] fileToByteArray = fileToByteArray(filePath);
            byte[] bytes = new byte[fileToByteArray.length];
            for(int i=0;i<fileToByteArray.length;i++){
                bytes[i] =(byte)(255-fileToByteArray[i]);
            }
            return  defineClass(bytes,0,bytes.length);
        }

        /**
         * 从文件中读取数据，放到字节数组中：
         * @param filePath
         * @return
         */
        public static byte[] fileToByteArray(String filePath) {
            //创建源与目的地
            File src = new File(filePath);//获得文件的源头，从哪开始传入(源)
            byte[] dest = null;//最后在内存中形成的字节数组(目的地)
            //选择流
            InputStream is = null;//此流是文件到程序的输入流
            ByteArrayOutputStream baos= null;//此流是程序到新文件的输出流
            //操作(输入操作)
            try {
                is = new FileInputStream(src);//文件输入流
                baos = new ByteArrayOutputStream();//字节输出流，不需要指定文件，内存中存在
                byte[] flush = new byte[1024*10];//设置缓冲，这样便于传输，大大提高传输效率
                int len = -1;//设置每次传输的个数,若没有缓冲的数据大，则返回剩下的数据，没有数据返回-1
                while((len = is.read(flush)) != -1) {
                    baos.write(flush,0,len);//每次读取len长度数据后，将其写出
                }
                baos.flush();//刷新管道数据
                dest = baos.toByteArray();//最终获得的字节数组
                return dest;//返回baos在内存中所形成的字节数组
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //释放资源,文件需要关闭,字节数组流无需关闭
                if(null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }

}