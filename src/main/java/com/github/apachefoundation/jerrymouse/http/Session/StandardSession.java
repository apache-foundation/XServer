package com.github.apachefoundation.jerrymouse.http.Session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.*;
import java.time.Instant;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @Author: xiantang
 * @Date: 2019/4/17 14:45
 */
public class StandardSession implements HttpSession, Serializable {

    private String id;
    private Map<String, Object> attributes;
    private boolean isVaild;
    private Instant lastAccessed;
    private Instant creationTime;
    private int maxInactiveInterval;

    public StandardSession(String id) {
        this.id = id;
        this.attributes = new ConcurrentHashMap<>();
        this.isVaild = true;
        this.lastAccessed = Instant.now();
        this.creationTime = Instant.now();
        this.maxInactiveInterval = 3600;
    }

    public StandardSession(String id, Instant lastAccessed, int maxInactiveInterval) {
        this.id = id;
        this.lastAccessed = lastAccessed;
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public String getId() {
        return id;
    }

    /**
     * 設置元素
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value) {
        if (isVaild) {
            this.lastAccessed = Instant.now();
            attributes.put(key, value);
        } else {
            throw new IllegalStateException("session has invalidated!");
        }

    }

    /**
     * 獲取session中的元素
     * @param key
     * @return
     */
    public Object getAttribute(String key) {
        if (isVaild) {
            this.lastAccessed = Instant.now();
            return attributes.get(key);
        } else {
            throw new IllegalArgumentException("session has invalidated!");
        }

    }
    @Override
    public long getCreationTime() {
        return creationTime.getEpochSecond();
    }

    /**
     * 獲取最後使用的時間
     * @return long
     */
    @Override
    public long getLastAccessedTime() {
        return lastAccessed.getEpochSecond();
    }

    /**
     * 设置过期时间
     * @param i
     */

    @Override
    public void setMaxInactiveInterval(int i) {
        maxInactiveInterval = i;
    }

    /**
     * 获取过期时间
     * @return int
     */
    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }


    /**
     * 获取session中的所有键
     * @return
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        if (isVaild) {
            return Collections.enumeration(attributes.keySet());
        }
        throw new IllegalArgumentException("session has invalidated!");
    }



    @Override
    public void removeAttribute(String s) {
        if (isVaild) {
            attributes.remove(s);
        }
        throw new IllegalArgumentException("session has invalidated!");
    }


    /**
     * 清除session
     * 将状态置为false
     * 并且清除session内的数据
     */
    @Override
    public void invalidate() {
        isVaild = false;
        attributes.clear();

    }

    @Override
    public boolean isNew() {
        return false;
    }


    public byte[] getAttributesByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(attributes);
        return baos.toByteArray();
    }

    public void setAttributesByte(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        attributes = (HashMap)ois.readObject();
    }
    //--------------------------------------------------------------------------
    //--------------------------------未实现-------------------------------------
    //--------------------------------------------------------------------------

    @Override
    @Deprecated
    public ServletContext getServletContext() {
        return null;
    }

    /**
     * @param s
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public void putValue(String s, Object o) {

    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    @Deprecated
    public void removeValue(String s) {

    }
    /** 被弃用 暂时不实现
     * @param s
     * @deprecated
     */
    @Override
    @Deprecated
    public Object getValue(String s) {

        return null;
    }


    @Override
    @Deprecated
    public String[] getValueNames() {
        return new String[0];
    }


    @Override
    @Deprecated
    public HttpSessionContext getSessionContext() {
        return null;
    }
}