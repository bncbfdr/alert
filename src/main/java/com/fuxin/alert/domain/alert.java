package com.fuxin.alert.domain;

/**
 * @author Gao
 * @date 2020/9/27 10:48
 * @description
 */
public class alert {
    private String id;
    private String title;
    private String desc;
    private String happenTime;
    private String closeTime;
    private String eventStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    @Override
    public String toString() {
        return "alert{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", desc='" + desc + '\'' +
            ", happenTime='" + happenTime + '\'' +
            ", closeTime='" + closeTime + '\'' +
            ", eventStatus='" + eventStatus + '\'' +
            '}';
    }
}
