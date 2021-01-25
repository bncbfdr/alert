package com.fuxin.alert.web.rest;

import com.fuxin.alert.AlertApp;
import com.fuxin.alert.domain.AlarmLevel;
import com.fuxin.alert.repository.AlarmLevelRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlarmLevelResource} REST controller.
 */
@SpringBootTest(classes = AlertApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlarmLevelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AlarmLevelRepository alarmLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlarmLevelMockMvc;

    private AlarmLevel alarmLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmLevel createEntity(EntityManager em) {
        AlarmLevel alarmLevel = new AlarmLevel()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .description(DEFAULT_DESCRIPTION);
        return alarmLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlarmLevel createUpdatedEntity(EntityManager em) {
        AlarmLevel alarmLevel = new AlarmLevel()
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .description(UPDATED_DESCRIPTION);
        return alarmLevel;
    }

    @BeforeEach
    public void initTest() {
        alarmLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlarmLevel() throws Exception {
        int databaseSizeBeforeCreate = alarmLevelRepository.findAll().size();
        // Create the AlarmLevel
        restAlarmLevelMockMvc.perform(post("/api/alarm-levels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmLevel)))
            .andExpect(status().isCreated());

        // Validate the AlarmLevel in the database
        List<AlarmLevel> alarmLevelList = alarmLevelRepository.findAll();
        assertThat(alarmLevelList).hasSize(databaseSizeBeforeCreate + 1);
        AlarmLevel testAlarmLevel = alarmLevelList.get(alarmLevelList.size() - 1);
        assertThat(testAlarmLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlarmLevel.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testAlarmLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAlarmLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alarmLevelRepository.findAll().size();

        // Create the AlarmLevel with an existing ID
        alarmLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlarmLevelMockMvc.perform(post("/api/alarm-levels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmLevel)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmLevel in the database
        List<AlarmLevel> alarmLevelList = alarmLevelRepository.findAll();
        assertThat(alarmLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlarmLevels() throws Exception {
        // Initialize the database
        alarmLevelRepository.saveAndFlush(alarmLevel);

        // Get all the alarmLevelList
        restAlarmLevelMockMvc.perform(get("/api/alarm-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alarmLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAlarmLevel() throws Exception {
        // Initialize the database
        alarmLevelRepository.saveAndFlush(alarmLevel);

        // Get the alarmLevel
        restAlarmLevelMockMvc.perform(get("/api/alarm-levels/{id}", alarmLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alarmLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingAlarmLevel() throws Exception {
        // Get the alarmLevel
        restAlarmLevelMockMvc.perform(get("/api/alarm-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlarmLevel() throws Exception {
        // Initialize the database
        alarmLevelRepository.saveAndFlush(alarmLevel);

        int databaseSizeBeforeUpdate = alarmLevelRepository.findAll().size();

        // Update the alarmLevel
        AlarmLevel updatedAlarmLevel = alarmLevelRepository.findById(alarmLevel.getId()).get();
        // Disconnect from session so that the updates on updatedAlarmLevel are not directly saved in db
        em.detach(updatedAlarmLevel);
        updatedAlarmLevel
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .description(UPDATED_DESCRIPTION);

        restAlarmLevelMockMvc.perform(put("/api/alarm-levels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlarmLevel)))
            .andExpect(status().isOk());

        // Validate the AlarmLevel in the database
        List<AlarmLevel> alarmLevelList = alarmLevelRepository.findAll();
        assertThat(alarmLevelList).hasSize(databaseSizeBeforeUpdate);
        AlarmLevel testAlarmLevel = alarmLevelList.get(alarmLevelList.size() - 1);
        assertThat(testAlarmLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlarmLevel.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testAlarmLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAlarmLevel() throws Exception {
        int databaseSizeBeforeUpdate = alarmLevelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlarmLevelMockMvc.perform(put("/api/alarm-levels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alarmLevel)))
            .andExpect(status().isBadRequest());

        // Validate the AlarmLevel in the database
        List<AlarmLevel> alarmLevelList = alarmLevelRepository.findAll();
        assertThat(alarmLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlarmLevel() throws Exception {
        // Initialize the database
        alarmLevelRepository.saveAndFlush(alarmLevel);

        int databaseSizeBeforeDelete = alarmLevelRepository.findAll().size();

        // Delete the alarmLevel
        restAlarmLevelMockMvc.perform(delete("/api/alarm-levels/{id}", alarmLevel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlarmLevel> alarmLevelList = alarmLevelRepository.findAll();
        assertThat(alarmLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
