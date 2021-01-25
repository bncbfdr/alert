package com.fuxin.alert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AlarmInfo.
 */
@Entity
@Table(name = "alarm_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlarmInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "info")
    private String info;

    @Column(name = "checked")
    private Integer checked;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_time")
    private Instant modifiedTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "alarmInfos", allowSetters = true)
    private AlarmRule alarmRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AlarmInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public AlarmInfo info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getChecked() {
        return checked;
    }

    public AlarmInfo checked(Integer checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public AlarmInfo createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AlarmInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedTime() {
        return modifiedTime;
    }

    public AlarmInfo modifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(Instant modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public AlarmInfo modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AlarmRule getAlarmRule() {
        return alarmRule;
    }

    public AlarmInfo alarmRule(AlarmRule alarmRule) {
        this.alarmRule = alarmRule;
        return this;
    }

    public void setAlarmRule(AlarmRule alarmRule) {
        this.alarmRule = alarmRule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmInfo)) {
            return false;
        }
        return id != null && id.equals(((AlarmInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", checked=" + getChecked() +
            ", createdTime='" + getCreatedTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedTime='" + getModifiedTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
