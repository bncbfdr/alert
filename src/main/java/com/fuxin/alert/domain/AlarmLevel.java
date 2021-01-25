package com.fuxin.alert.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AlarmLevel.
 */
@Entity
@Table(name = "alarm_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlarmLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "alarmLevel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AlarmRule> alarmRules = new HashSet<>();

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

    public AlarmLevel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public AlarmLevel color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public AlarmLevel description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AlarmRule> getAlarmRules() {
        return alarmRules;
    }

    public AlarmLevel alarmRules(Set<AlarmRule> alarmRules) {
        this.alarmRules = alarmRules;
        return this;
    }

    public AlarmLevel addAlarmRule(AlarmRule alarmRule) {
        this.alarmRules.add(alarmRule);
        alarmRule.setAlarmLevel(this);
        return this;
    }

    public AlarmLevel removeAlarmRule(AlarmRule alarmRule) {
        this.alarmRules.remove(alarmRule);
        alarmRule.setAlarmLevel(null);
        return this;
    }

    public void setAlarmRules(Set<AlarmRule> alarmRules) {
        this.alarmRules = alarmRules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlarmLevel)) {
            return false;
        }
        return id != null && id.equals(((AlarmLevel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlarmLevel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
