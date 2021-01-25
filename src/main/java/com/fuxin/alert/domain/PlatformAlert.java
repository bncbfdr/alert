package com.fuxin.alert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PlatformAlert.
 */
@Entity
@Table(name = "plaform_alert")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlatformAlert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "happen_time")
    private String happenTime;

    @Column(name = "close_time")
    private String closeTime;

    @Column(name = "event_status")
    private String eventStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public PlatformAlert title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public PlatformAlert desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public PlatformAlert happenTime(String happenTime) {
        this.happenTime = happenTime;
        return this;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public PlatformAlert closeTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public PlatformAlert eventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
        return this;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlatformAlert)) {
            return false;
        }
        return id != null && id.equals(((PlatformAlert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlatformAlert{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", desc='" + getDesc() + "'" +
            ", happenTime='" + getHappenTime() + "'" +
            ", closeTime='" + getCloseTime() + "'" +
            ", eventStatus='" + getEventStatus() + "'" +
            "}";
    }
}
