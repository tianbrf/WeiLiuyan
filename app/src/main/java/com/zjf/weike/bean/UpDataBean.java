package com.zjf.weike.bean;

/**
 * @author :ZJF
 * @version : 2016-12-28 下午 3:31
 */

public class UpDataBean {

    /**
     * version : 1.2 新版本
     * force : true 是否强制升级
     * url : http://www.baidu.com/fire.apk 下载地址
     */

    private String version;
    private boolean force;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
