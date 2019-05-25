package com.github.apachefoundation.jerrymouse.container.loader;

import com.github.apachefoundation.jerrymouse.container.Container;
import com.github.apachefoundation.jerrymouse.context.WebContext;
import com.github.apachefoundation.jerrymouse.context.WebHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author: xiantang
 * @Date: 2019/5/24 20:55
 */
public class SimpleLoader implements Loader {
    public static final String WEB_ROOT = "file:target/test-classes/";
    private ClassLoader classLoader = null;
    private Container container = null;
    private static WebContext webContext;

    /*
    初始化webContext存入servlet以及他的映射
     */
    static {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parse = factory.newSAXParser();
            WebHandler phandler = new WebHandler();
            // 当前线程的类加载器
            parse.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("web.xml"), phandler);
            webContext = new WebContext( phandler.getEntities(),phandler.getMappings());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public SimpleLoader() {
        try {
            URL classUrl = new URL(WEB_ROOT);
            classLoader = new URLClassLoader(new URL[]{classUrl});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
}