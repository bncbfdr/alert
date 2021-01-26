package com.fuxin.alert.web.rest;

import com.fuxin.alert.domain.AlarmLevel;
import com.fuxin.alert.repository.AlarmLevelRepository;
import com.fuxin.alert.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fuxin.alert.domain.AlarmLevel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@CrossOrigin
public class AlarmLevelResource {

    private final Logger log = LoggerFactory.getLogger(AlarmLevelResource.class);

    private static final String ENTITY_NAME = "alertAlarmLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmLevelRepository alarmLevelRepository;

    public AlarmLevelResource(AlarmLevelRepository alarmLevelRepository) {
        this.alarmLevelRepository = alarmLevelRepository;
    }

    /**
     * {@code POST  /alarm-levels} : Create a new alarmLevel.
     *
     * @param alarmLevel the alarmLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmLevel, or with status {@code 400 (Bad Request)} if the alarmLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-levels")
    public ResponseEntity<AlarmLevel> createAlarmLevel(@RequestBody AlarmLevel alarmLevel) throws URISyntaxException {
        log.debug("REST request to save AlarmLevel : {}", alarmLevel);
        if (alarmLevel.getId() != null) {
            throw new BadRequestAlertException("A new alarmLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmLevel result = alarmLevelRepository.save(alarmLevel);
        return ResponseEntity.created(new URI("/api/alarm-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-levels} : Updates an existing alarmLevel.
     *
     * @param alarmLevel the alarmLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmLevel,
     * or with status {@code 400 (Bad Request)} if the alarmLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-levels")
    public ResponseEntity<AlarmLevel> updateAlarmLevel(@RequestBody AlarmLevel alarmLevel) throws URISyntaxException {
        log.debug("REST request to update AlarmLevel : {}", alarmLevel);
        if (alarmLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmLevel result = alarmLevelRepository.save(alarmLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alarmLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-levels} : get all the alarmLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmLevels in body.
     */
    @GetMapping("/alarm-levels")
    public List<AlarmLevel> getAllAlarmLevels() {
        log.debug("REST request to get all AlarmLevels");
        return alarmLevelRepository.findAll();
    }

    /**
     * {@code GET  /alarm-levels/:id} : get the "id" alarmLevel.
     *
     * @param id the id of the alarmLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-levels/{id}")
    public ResponseEntity<AlarmLevel> getAlarmLevel(@PathVariable Long id) {
        log.debug("REST request to get AlarmLevel : {}", id);
        Optional<AlarmLevel> alarmLevel = alarmLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alarmLevel);
    }

    /**
     * {@code DELETE  /alarm-levels/:id} : delete the "id" alarmLevel.
     *
     * @param id the id of the alarmLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-levels/{id}")
    public ResponseEntity<Void> deleteAlarmLevel(@PathVariable Long id) {
        log.debug("REST request to delete AlarmLevel : {}", id);

        alarmLevelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
