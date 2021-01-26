package com.fuxin.alert.web.rest;

import com.fuxin.alert.AlertApp;
import com.fuxin.alert.domain.AlarmRule;
import com.fuxin.alert.repository.AlarmRuleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.fuxin.alert.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fuxin.alert.domain.enumeration.AlarmType;
import com.fuxin.alert.domain.enumeration.NotifyType;
/**
 * Integration tests for the {@link AlarmRuleResource} REST controller.
 */
@SpringBootTest(classes = AlertApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlarmRuleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final AlarmType DEFAULT_ALARM_TYPE = AlarmType.EVENT_COUNT;
    private static final AlarmType UPDATED_ALARM_TYPE = AlarmType.FIELD;

    private static final String DEFAULT_CONF = "AAAAAAAAAA";
    private static final String UPDATED_CONF = "BBBBBBBBBB";

    private static final NotifyType DEFAULT_NOTIFY_TYPE = NotifyType.NULL;
    private static final NotifyType UPDATED_NOTIFY_TYPE = NotifyType.EMAIL;

    private static final String DEFAULT_NOTIFY_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFY_MEMBER = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFY_MEMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFIED_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String DEFAULT_EVENT_RULE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_RULE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_EVENT_RULE_ID = 1L;
    private static final Long UPDATED_EVENT_RULE_ID = 2L;

    private static final String DEFAULT_ALERT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_SOLVE_SOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_SOLVE_SOLUTION = "BBBBBBBBBB";

    @Autowired
    private AlarmRuleRepository alarmRuleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmRuleMockMvc;

    private AlarmRule alarmRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmRule createEntity(EntityManager em) {
        AlarmRule alarmRule = new AlarmRule()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .alarmType(DEFAULT_ALARM_TYPE)
            .conf(DEFAULT_CONF)
            .notifyType(DEFAULT_NOTIFY_TYPE)
            .notifyRole(DEFAULT_NOTIFY_ROLE)
            .notifyMember(DEFAULT_NOTIFY_MEMBER)
            .createTime(DEFAULT_CREATE_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedTime(DEFAULT_MODIFIED_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .status(DEFAULT_STATUS)
            .category(DEFAULT_CATEGORY)
            .categoryId(DEFAULT_CATEGORY_ID)
            .eventRuleName(DEFAULT_EVENT_RULE_NAME)
            .eventRuleId(DEFAULT_EVENT_RULE_ID)
            .alertTemplate(DEFAULT_ALERT_TEMPLATE)
            .solveSolution(DEFAULT_SOLVE_SOLUTION);
        return alarmRule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmRule createUpdatedEntity(EntityManager em) {
        AlarmRule alarmRule = new AlarmRule()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .alarmType(UPDATED_ALARM_TYPE)
            .conf(UPDATED_CONF)
            .notifyType(UPDATED_NOTIFY_TYPE)
            .notifyRole(UPDATED_NOTIFY_ROLE)
            .notifyMember(UPDATED_NOTIFY_MEMBER)
            .createTime(UPDATED_CREATE_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .category(UPDATED_CATEGORY)
            .categoryId(UPDATED_CATEGORY_ID)
            .eventRuleName(UPDATED_EVENT_RULE_NAME)
            .eventRuleId(UPDATED_EVENT_RULE_ID)
            .alertTemplate(UPDATED_ALERT_TEMPLATE)
            .solveSolution(UPDATED_SOLVE_SOLUTION);
        return alarmRule;
    }

    @BeforeEach
    public void initTest() {
        alarmRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmRule() throws Exception {
        int databaseSizeBeforeCreate = alarmRuleRepository.findAll().size();
        // Create the AlarmRule
        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isCreated());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmRule testAlarmRule = alarmRuleList.get(alarmRuleList.size() - 1);
        assertThat(testAlarmRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlarmRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAlarmRule.getAlarmType()).isEqualTo(DEFAULT_ALARM_TYPE);
        assertThat(testAlarmRule.getConf()).isEqualTo(DEFAULT_CONF);
        assertThat(testAlarmRule.getNotifyType()).isEqualTo(DEFAULT_NOTIFY_TYPE);
        assertThat(testAlarmRule.getNotifyRole()).isEqualTo(DEFAULT_NOTIFY_ROLE);
        assertThat(testAlarmRule.getNotifyMember()).isEqualTo(DEFAULT_NOTIFY_MEMBER);
        assertThat(testAlarmRule.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testAlarmRule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAlarmRule.getModifiedTime()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testAlarmRule.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAlarmRule.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAlarmRule.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testAlarmRule.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testAlarmRule.getEventRuleName()).isEqualTo(DEFAULT_EVENT_RULE_NAME);
        assertThat(testAlarmRule.getEventRuleId()).isEqualTo(DEFAULT_EVENT_RULE_ID);
        assertThat(testAlarmRule.getAlertTemplate()).isEqualTo(DEFAULT_ALERT_TEMPLATE);
        assertThat(testAlarmRule.getSolveSolution()).isEqualTo(DEFAULT_SOLVE_SOLUTION);
    }

    @Test
    @Transactional
    public void createAlarmRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmRuleRepository.findAll().size();

        // Create the AlarmRule with an existing ID
        alarmRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmRuleMockMvc.perform(post("/api/alarm-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmRules() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        // Get all the alarmRuleList
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].alarmType").value(hasItem(DEFAULT_ALARM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].conf").value(hasItem(DEFAULT_CONF)))
            .andExpect(jsonPath("$.[*].notifyType").value(hasItem(DEFAULT_NOTIFY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].notifyRole").value(hasItem(DEFAULT_NOTIFY_ROLE)))
            .andExpect(jsonPath("$.[*].notifyMember").value(hasItem(DEFAULT_NOTIFY_MEMBER)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(sameInstant(DEFAULT_MODIFIED_TIME))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].eventRuleName").value(hasItem(DEFAULT_EVENT_RULE_NAME)))
            .andExpect(jsonPath("$.[*].eventRuleId").value(hasItem(DEFAULT_EVENT_RULE_ID.intValue())))
            .andExpect(jsonPath("$.[*].alertTemplate").value(hasItem(DEFAULT_ALERT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].solveSolution").value(hasItem(DEFAULT_SOLVE_SOLUTION)));
    }
    
    @Test
    @Transactional
    public void getAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        // Get the alarmRule
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules/{id}", alarmRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmRule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.alarmType").value(DEFAULT_ALARM_TYPE.toString()))
            .andExpect(jsonPath("$.conf").value(DEFAULT_CONF))
            .andExpect(jsonPath("$.notifyType").value(DEFAULT_NOTIFY_TYPE.toString()))
            .andExpect(jsonPath("$.notifyRole").value(DEFAULT_NOTIFY_ROLE))
            .andExpect(jsonPath("$.notifyMember").value(DEFAULT_NOTIFY_MEMBER))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedTime").value(sameInstant(DEFAULT_MODIFIED_TIME)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.eventRuleName").value(DEFAULT_EVENT_RULE_NAME))
            .andExpect(jsonPath("$.eventRuleId").value(DEFAULT_EVENT_RULE_ID.intValue()))
            .andExpect(jsonPath("$.alertTemplate").value(DEFAULT_ALERT_TEMPLATE))
            .andExpect(jsonPath("$.solveSolution").value(DEFAULT_SOLVE_SOLUTION));
    }
    @Test
    @Transactional
    public void getNonExistingAlarmRule() throws Exception {
        // Get the alarmRule
        restAlarmRuleMockMvc.perform(get("/api/alarm-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        int databaseSizeBeforeUpdate = alarmRuleRepository.findAll().size();

        // Update the alarmRule
        AlarmRule updatedAlarmRule = alarmRuleRepository.findById(alarmRule.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmRule are not directly saved in db
        em.detach(updatedAlarmRule);
        updatedAlarmRule
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .alarmType(UPDATED_ALARM_TYPE)
            .conf(UPDATED_CONF)
            .notifyType(UPDATED_NOTIFY_TYPE)
            .notifyRole(UPDATED_NOTIFY_ROLE)
            .notifyMember(UPDATED_NOTIFY_MEMBER)
            .createTime(UPDATED_CREATE_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .status(UPDATED_STATUS)
            .category(UPDATED_CATEGORY)
            .categoryId(UPDATED_CATEGORY_ID)
            .eventRuleName(UPDATED_EVENT_RULE_NAME)
            .eventRuleId(UPDATED_EVENT_RULE_ID)
            .alertTemplate(UPDATED_ALERT_TEMPLATE)
            .solveSolution(UPDATED_SOLVE_SOLUTION);

        restAlarmRuleMockMvc.perform(put("/api/alarm-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmRule)))
            .andExpect(status().isOk());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeUpdate);
        AlarmRule testAlarmRule = alarmRuleList.get(alarmRuleList.size() - 1);
        assertThat(testAlarmRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlarmRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAlarmRule.getAlarmType()).isEqualTo(UPDATED_ALARM_TYPE);
        assertThat(testAlarmRule.getConf()).isEqualTo(UPDATED_CONF);
        assertThat(testAlarmRule.getNotifyType()).isEqualTo(UPDATED_NOTIFY_TYPE);
        assertThat(testAlarmRule.getNotifyRole()).isEqualTo(UPDATED_NOTIFY_ROLE);
        assertThat(testAlarmRule.getNotifyMember()).isEqualTo(UPDATED_NOTIFY_MEMBER);
        assertThat(testAlarmRule.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testAlarmRule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlarmRule.getModifiedTime()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testAlarmRule.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAlarmRule.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAlarmRule.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testAlarmRule.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testAlarmRule.getEventRuleName()).isEqualTo(UPDATED_EVENT_RULE_NAME);
        assertThat(testAlarmRule.getEventRuleId()).isEqualTo(UPDATED_EVENT_RULE_ID);
        assertThat(testAlarmRule.getAlertTemplate()).isEqualTo(UPDATED_ALERT_TEMPLATE);
        assertThat(testAlarmRule.getSolveSolution()).isEqualTo(UPDATED_SOLVE_SOLUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmRule() throws Exception {
        int databaseSizeBeforeUpdate = alarmRuleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmRuleMockMvc.perform(put("/api/alarm-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmRule)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmRule in the database
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmRule() throws Exception {
        // Initialize the database
        alarmRuleRepository.saveAndFlush(alarmRule);

        int databaseSizeBeforeDelete = alarmRuleRepository.findAll().size();

        // Delete the alarmRule
        restAlarmRuleMockMvc.perform(delete("/api/alarm-rules/{id}", alarmRule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmRule> alarmRuleList = alarmRuleRepository.findAll();
        assertThat(alarmRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
