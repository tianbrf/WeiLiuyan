package com.zjf.weike.bean;

import java.io.Serializable;

/**
 * @author :ZJF
 * @version : 2016-12-27 上午 11:14
 */

public class ImageDetial implements Serializable {
    private int[] screenLocation;
    private int width;
    private int height;
    private String url;

    public ImageDetial(int[] screenLocation, int width, int height, String url) {
        this.screenLocation = screenLocation;
        this.width = width;
        this.height = height;
        this.url = url;
    }

    public int[] getScreenLocation() {
        return screenLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }
}
