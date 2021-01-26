package com.fuxin.alert.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fuxin.alert.domain.enumeration.AlarmType;

import com.fuxin.alert.domain.enumeration.NotifyType;

/**
 * A AlarmRule.
 */
@Entity
@Table(name = "alarm_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlarmRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private AlarmType alarmType;

    @Column(name = "conf")
    private String conf;

    @Enumerated(EnumType.STRING)
    @Column(name = "notify_type")
    private NotifyType notifyType;

    @Column(name = "notify_role")
    private String notifyRole;

    @Column(name = "notify_member")
    private String notifyMember;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_time")
    private ZonedDateTime modifiedTime;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "category")
    private String category;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "event_rule_name")
    private String eventRuleName;

    @Column(name = "event_rule_id")
    private Long eventRuleId;

    @Column(name = "alert_template")
    private String alertTemplate;

    @Column(name = "solve_solution")
    private String solveSolution;

    @ManyToOne
    @JsonIgnoreProperties(value = "alarmRules", allowSetters = true)
    private AlarmLevel alarmLevel;

    @OneToMany(mappedBy = "alarmRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AlarmInfo> alarmInfos = new HashSet<>();

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

    public AlarmRule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AlarmRule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public AlarmRule alarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
        return this;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public String getConf() {
        return conf;
    }

    public AlarmRule conf(String conf) {
        this.conf = conf;
        return this;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public AlarmRule notifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
        return this;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyRole() {
        return notifyRole;
    }

    public AlarmRule notifyRole(String notifyRole) {
        this.notifyRole = notifyRole;
        return this;
    }

    public void setNotifyRole(String notifyRole) {
        this.notifyRole = notifyRole;
    }

    public String getNotifyMember() {
        return notifyMember;
    }

    public AlarmRule notifyMember(String notifyMember) {
        this.notifyMember = notifyMember;
        return this;
    }

    public void setNotifyMember(String notifyMember) {
        this.notifyMember = notifyMember;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public AlarmRule createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AlarmRule createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getModifiedTime() {
        return modifiedTime;
    }

    public AlarmRule modifiedTime(ZonedDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public void setModifiedTime(ZonedDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public AlarmRule modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public AlarmRule status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public AlarmRule category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public AlarmRule categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getEventRuleName() {
        return eventRuleName;
    }

    public AlarmRule eventRuleName(String eventRuleName) {
        this.eventRuleName = eventRuleName;
        return this;
    }

    public void setEventRuleName(String eventRuleName) {
        this.eventRuleName = eventRuleName;
    }

    public Long getEventRuleId() {
        return eventRuleId;
    }

    public AlarmRule eventRuleId(Long eventRuleId) {
        this.eventRuleId = eventRuleId;
        return this;
    }

    public void setEventRuleId(Long eventRuleId) {
        this.eventRuleId = eventRuleId;
    }

    public String getAlertTemplate() {
        return alertTemplate;
    }

    public AlarmRule alertTemplate(String alertTemplate) {
        this.alertTemplate = alertTemplate;
        return this;
    }

    public void setAlertTemplate(String alertTemplate) {
        this.alertTemplate = alertTemplate;
    }

    public String getSolveSolution() {
        return solveSolution;
    }

    public AlarmRule solveSolution(String solveSolution) {
        this.solveSolution = solveSolution;
        return this;
    }

    public void setSolveSolution(String solveSolution) {
        this.solveSolution = solveSolution;
    }

    public AlarmLevel getAlarmLevel() {
        return alarmLevel;
    }

    public AlarmRule alarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
        return this;
    }

    public void setAlarmLevel(AlarmLevel alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Set<AlarmInfo> getAlarmInfos() {
        return alarmInfos;
    }

    public AlarmRule alarmInfos(Set<AlarmInfo> alarmInfos) {
        this.alarmInfos = alarmInfos;
        return this;
    }

    public AlarmRule addAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfos.add(alarmInfo);
        alarmInfo.setAlarmRule(this);
        return this;
    }

    public AlarmRule removeAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfos.remove(alarmInfo);
        alarmInfo.setAlarmRule(null);
        return this;
    }

    public void setAlarmInfos(Set<AlarmInfo> alarmInfos) {
        this.alarmInfos = alarmInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmRule)) {
            return false;
        }
        return id != null && id.equals(((AlarmRule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmRule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", alarmType='" + getAlarmType() + "'" +
            ", conf='" + getConf() + "'" +
            ", notifyType='" + getNotifyType() + "'" +
            ", notifyRole='" + getNotifyRole() + "'" +
            ", notifyMember='" + getNotifyMember() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", modifiedTime='" + getModifiedTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", category='" + getCategory() + "'" +
            ", categoryId=" + getCategoryId() +
            ", eventRuleName='" + getEventRuleName() + "'" +
            ", eventRuleId=" + getEventRuleId() +
            ", alertTemplate='" + getAlertTemplate() + "'" +
            ", solveSolution='" + getSolveSolution() + "'" +
            "}";
    }
}
