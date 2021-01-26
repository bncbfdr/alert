package com.fuxin.alert.web.rest;

import com.fuxin.alert.AlertApp;
import com.fuxin.alert.domain.PlatformAlert;
import com.fuxin.alert.repository.PlatformAlertRepository;

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
 * Integration tests for the {@link PlatformAlertResource} REST controller.
 */
@SpringBootTest(classes = AlertApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlatformAlertResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_HAPPEN_TIME = "AAAAAAAAAA";
    private static final String UPDATED_HAPPEN_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CLOSE_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CLOSE_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_STATUS = "BBBBBBBBBB";

    @Autowired
    private PlatformAlertRepository platformAlertRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlatformAlertMockMvc;

    private PlatformAlert platformAlert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlatformAlert createEntity(EntityManager em) {
        PlatformAlert platformAlert = new PlatformAlert()
            .title(DEFAULT_TITLE)
            .desc(DEFAULT_DESC)
            .happenTime(DEFAULT_HAPPEN_TIME)
            .closeTime(DEFAULT_CLOSE_TIME)
            .eventStatus(DEFAULT_EVENT_STATUS);
        return platformAlert;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlatformAlert createUpdatedEntity(EntityManager em) {
        PlatformAlert platformAlert = new PlatformAlert()
            .title(UPDATED_TITLE)
            .desc(UPDATED_DESC)
            .happenTime(UPDATED_HAPPEN_TIME)
            .closeTime(UPDATED_CLOSE_TIME)
            .eventStatus(UPDATED_EVENT_STATUS);
        return platformAlert;
    }

    @BeforeEach
    public void initTest() {
        platformAlert = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlatformAlert() throws Exception {
        int databaseSizeBeforeCreate = platformAlertRepository.findAll().size();
        // Create the PlatformAlert
        restPlatformAlertMockMvc.perform(post("/api/platform-alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(platformAlert)))
            .andExpect(status().isCreated());

        // Validate the PlatformAlert in the database
        List<PlatformAlert> platformAlertList = platformAlertRepository.findAll();
        assertThat(platformAlertList).hasSize(databaseSizeBeforeCreate + 1);
        PlatformAlert testPlatformAlert = platformAlertList.get(platformAlertList.size() - 1);
        assertThat(testPlatformAlert.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPlatformAlert.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPlatformAlert.getHappenTime()).isEqualTo(DEFAULT_HAPPEN_TIME);
        assertThat(testPlatformAlert.getCloseTime()).isEqualTo(DEFAULT_CLOSE_TIME);
        assertThat(testPlatformAlert.getEventStatus()).isEqualTo(DEFAULT_EVENT_STATUS);
    }

    @Test
    @Transactional
    public void createPlatformAlertWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = platformAlertRepository.findAll().size();

        // Create the PlatformAlert with an existing ID
        platformAlert.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatformAlertMockMvc.perform(post("/api/platform-alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(platformAlert)))
            .andExpect(status().isBadRequest());

        // Validate the PlatformAlert in the database
        List<PlatformAlert> platformAlertList = platformAlertRepository.findAll();
        assertThat(platformAlertList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlatformAlerts() throws Exception {
        // Initialize the database
        platformAlertRepository.saveAndFlush(platformAlert);

        // Get all the platformAlertList
        restPlatformAlertMockMvc.perform(get("/api/platform-alerts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(platformAlert.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].happenTime").value(hasItem(DEFAULT_HAPPEN_TIME)))
            .andExpect(jsonPath("$.[*].closeTime").value(hasItem(DEFAULT_CLOSE_TIME)))
            .andExpect(jsonPath("$.[*].eventStatus").value(hasItem(DEFAULT_EVENT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getPlatformAlert() throws Exception {
        // Initialize the database
        platformAlertRepository.saveAndFlush(platformAlert);

        // Get the platformAlert
        restPlatformAlertMockMvc.perform(get("/api/platform-alerts/{id}", platformAlert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(platformAlert.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.happenTime").value(DEFAULT_HAPPEN_TIME))
            .andExpect(jsonPath("$.closeTime").value(DEFAULT_CLOSE_TIME))
            .andExpect(jsonPath("$.eventStatus").value(DEFAULT_EVENT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingPlatformAlert() throws Exception {
        // Get the platformAlert
        restPlatformAlertMockMvc.perform(get("/api/platform-alerts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlatformAlert() throws Exception {
        // Initialize the database
        platformAlertRepository.saveAndFlush(platformAlert);

        int databaseSizeBeforeUpdate = platformAlertRepository.findAll().size();

        // Update the platformAlert
        PlatformAlert updatedPlatformAlert = platformAlertRepository.findById(platformAlert.getId()).get();
        // Disconnect from session so that the updates on updatedPlatformAlert are not directly saved in db
        em.detach(updatedPlatformAlert);
        updatedPlatformAlert
            .title(UPDATED_TITLE)
            .desc(UPDATED_DESC)
            .happenTime(UPDATED_HAPPEN_TIME)
            .closeTime(UPDATED_CLOSE_TIME)
            .eventStatus(UPDATED_EVENT_STATUS);

        restPlatformAlertMockMvc.perform(put("/api/platform-alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlatformAlert)))
            .andExpect(status().isOk());

        // Validate the PlatformAlert in the database
        List<PlatformAlert> platformAlertList = platformAlertRepository.findAll();
        assertThat(platformAlertList).hasSize(databaseSizeBeforeUpdate);
        PlatformAlert testPlatformAlert = platformAlertList.get(platformAlertList.size() - 1);
        assertThat(testPlatformAlert.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPlatformAlert.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPlatformAlert.getHappenTime()).isEqualTo(UPDATED_HAPPEN_TIME);
        assertThat(testPlatformAlert.getCloseTime()).isEqualTo(UPDATED_CLOSE_TIME);
        assertThat(testPlatformAlert.getEventStatus()).isEqualTo(UPDATED_EVENT_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPlatformAlert() throws Exception {
        int databaseSizeBeforeUpdate = platformAlertRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatformAlertMockMvc.perform(put("/api/platform-alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(platformAlert)))
            .andExpect(status().isBadRequest());

        // Validate the PlatformAlert in the database
        List<PlatformAlert> platformAlertList = platformAlertRepository.findAll();
        assertThat(platformAlertList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlatformAlert() throws Exception {
        // Initialize the database
        platformAlertRepository.saveAndFlush(platformAlert);

        int databaseSizeBeforeDelete = platformAlertRepository.findAll().size();

        // Delete the platformAlert
        restPlatformAlertMockMvc.perform(delete("/api/platform-alerts/{id}", platformAlert.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlatformAlert> platformAlertList = platformAlertRepository.findAll();
        assertThat(platformAlertList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
