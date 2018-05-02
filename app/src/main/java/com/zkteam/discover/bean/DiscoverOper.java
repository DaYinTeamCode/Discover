package com.zkteam.discover.bean;

import com.zkteam.discover.util.IOUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/6 下午8:16
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class DiscoverOper extends Oper implements Serializable {

    public static final String TYPE_TITLE = "title";  //本地标记

    private int parentId;
    private int parentChannelId;
    private String parentTitle;
    private int childPosition;
    private int parentPosition;

    public int getChildPosition() {

        return childPosition;
    }

    public void setChildPosition(int childPosition) {

        this.childPosition = childPosition;
    }

    public int getParentPosition() {

        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {

        this.parentPosition = parentPosition;
    }

    public int getParentId() {

        return parentId;
    }

    public void setParentId(int parentId) {

        this.parentId = parentId;
    }

    public int getParentChannelId() {

        return parentChannelId;
    }

    public void setParentChannelId(int channelId) {

        this.parentChannelId = channelId;
    }

    public String getParentTitle() {

        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {

        this.parentTitle = parentTitle;
    }

    public boolean isTypeTitle() {

        return TYPE_TITLE.equals(getElement_type());
    }


    public DiscoverOper clone() {

        DiscoverOper outer = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try {
            // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            bais = new ByteArrayInputStream(baos.toByteArray());
            ois = new ObjectInputStream(bais);
            outer = (DiscoverOper) ois.readObject();
        } catch (Exception e) {

            return this;
        } finally {

            IOUtil.closeOutStream(baos);
            IOUtil.closeOutStream(oos);
            IOUtil.closeInStream(ois);
            IOUtil.closeInStream(bais);
        }
        return outer;
    }
}
