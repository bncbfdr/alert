package com.fuxin.alert.web.rest;

import com.fuxin.alert.AlertApp;
import com.fuxin.alert.domain.AlarmInfo;
import com.fuxin.alert.repository.AlarmInfoRepository;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlarmInfoResource} REST controller.
 */
@SpringBootTest(classes = AlertApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlarmInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHECKED = 1;
    private static final Integer UPDATED_CHECKED = 2;

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private AlarmInfoRepository alarmInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmInfoMockMvc;

    private AlarmInfo alarmInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmInfo createEntity(EntityManager em) {
        AlarmInfo alarmInfo = new AlarmInfo()
            .name(DEFAULT_NAME)
            .info(DEFAULT_INFO)
            .checked(DEFAULT_CHECKED)
            .createdTime(DEFAULT_CREATED_TIME)
            .createdBy(DEFAULT_CREATED_BY)
            .modifiedTime(DEFAULT_MODIFIED_TIME)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return alarmInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmInfo createUpdatedEntity(EntityManager em) {
        AlarmInfo alarmInfo = new AlarmInfo()
            .name(UPDATED_NAME)
            .info(UPDATED_INFO)
            .checked(UPDATED_CHECKED)
            .createdTime(UPDATED_CREATED_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return alarmInfo;
    }

    @BeforeEach
    public void initTest() {
        alarmInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmInfo() throws Exception {
        int databaseSizeBeforeCreate = alarmInfoRepository.findAll().size();
        // Create the AlarmInfo
        restAlarmInfoMockMvc.perform(post("/api/alarm-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmInfo)))
            .andExpect(status().isCreated());

        // Validate the AlarmInfo in the database
        List<AlarmInfo> alarmInfoList = alarmInfoRepository.findAll();
        assertThat(alarmInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmInfo testAlarmInfo = alarmInfoList.get(alarmInfoList.size() - 1);
        assertThat(testAlarmInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlarmInfo.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testAlarmInfo.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testAlarmInfo.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testAlarmInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAlarmInfo.getModifiedTime()).isEqualTo(DEFAULT_MODIFIED_TIME);
        assertThat(testAlarmInfo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createAlarmInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmInfoRepository.findAll().size();

        // Create the AlarmInfo with an existing ID
        alarmInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmInfoMockMvc.perform(post("/api/alarm-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmInfo in the database
        List<AlarmInfo> alarmInfoList = alarmInfoRepository.findAll();
        assertThat(alarmInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmInfos() throws Exception {
        // Initialize the database
        alarmInfoRepository.saveAndFlush(alarmInfo);

        // Get all the alarmInfoList
        restAlarmInfoMockMvc.perform(get("/api/alarm-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].modifiedTime").value(hasItem(DEFAULT_MODIFIED_TIME.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getAlarmInfo() throws Exception {
        // Initialize the database
        alarmInfoRepository.saveAndFlush(alarmInfo);

        // Get the alarmInfo
        restAlarmInfoMockMvc.perform(get("/api/alarm-infos/{id}", alarmInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.modifiedTime").value(DEFAULT_MODIFIED_TIME.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingAlarmInfo() throws Exception {
        // Get the alarmInfo
        restAlarmInfoMockMvc.perform(get("/api/alarm-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmInfo() throws Exception {
        // Initialize the database
        alarmInfoRepository.saveAndFlush(alarmInfo);

        int databaseSizeBeforeUpdate = alarmInfoRepository.findAll().size();

        // Update the alarmInfo
        AlarmInfo updatedAlarmInfo = alarmInfoRepository.findById(alarmInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmInfo are not directly saved in db
        em.detach(updatedAlarmInfo);
        updatedAlarmInfo
            .name(UPDATED_NAME)
            .info(UPDATED_INFO)
            .checked(UPDATED_CHECKED)
            .createdTime(UPDATED_CREATED_TIME)
            .createdBy(UPDATED_CREATED_BY)
            .modifiedTime(UPDATED_MODIFIED_TIME)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restAlarmInfoMockMvc.perform(put("/api/alarm-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmInfo)))
            .andExpect(status().isOk());

        // Validate the AlarmInfo in the database
        List<AlarmInfo> alarmInfoList = alarmInfoRepository.findAll();
        assertThat(alarmInfoList).hasSize(databaseSizeBeforeUpdate);
        AlarmInfo testAlarmInfo = alarmInfoList.get(alarmInfoList.size() - 1);
        assertThat(testAlarmInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlarmInfo.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testAlarmInfo.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testAlarmInfo.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testAlarmInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlarmInfo.getModifiedTime()).isEqualTo(UPDATED_MODIFIED_TIME);
        assertThat(testAlarmInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmInfo() throws Exception {
        int databaseSizeBeforeUpdate = alarmInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmInfoMockMvc.perform(put("/api/alarm-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmInfo in the database
        List<AlarmInfo> alarmInfoList = alarmInfoRepository.findAll();
        assertThat(alarmInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmInfo() throws Exception {
        // Initialize the database
        alarmInfoRepository.saveAndFlush(alarmInfo);

        int databaseSizeBeforeDelete = alarmInfoRepository.findAll().size();

        // Delete the alarmInfo
        restAlarmInfoMockMvc.perform(delete("/api/alarm-infos/{id}", alarmInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmInfo> alarmInfoList = alarmInfoRepository.findAll();
        assertThat(alarmInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
