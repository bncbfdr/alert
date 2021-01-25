package com.fuxin.alert.web.rest;

import com.fuxin.alert.domain.AlarmInfo;
import com.fuxin.alert.repository.AlarmInfoRepository;
import com.fuxin.alert.service.AlertService;
import com.fuxin.alert.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fuxin.alert.domain.AlarmInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@CrossOrigin
public class AlarmInfoResource {

    private final Logger log = LoggerFactory.getLogger(AlarmInfoResource.class);

    private static final String ENTITY_NAME = "alertAlarmInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmInfoRepository alarmInfoRepository;

    @Autowired
    private AlertService alertService;

    public AlarmInfoResource(AlarmInfoRepository alarmInfoRepository) {
        this.alarmInfoRepository = alarmInfoRepository;
    }

    /**
     * {@code POST  /alarm-infos} : Create a new alarmInfo.
     *
     * @param alarmInfo the alarmInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmInfo, or with status {@code 400 (Bad Request)} if the alarmInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-infos")
    public ResponseEntity<AlarmInfo> createAlarmInfo(@RequestBody AlarmInfo alarmInfo) throws URISyntaxException {
        log.debug("REST request to save AlarmInfo : {}", alarmInfo);
        if (alarmInfo.getId() != null) {
            throw new BadRequestAlertException("A new alarmInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmInfo result = alarmInfoRepository.save(alarmInfo);
        return ResponseEntity.created(new URI("/api/alarm-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-infos} : Updates an existing alarmInfo.
     *
     * @param alarmInfo the alarmInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmInfo,
     * or with status {@code 400 (Bad Request)} if the alarmInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-infos")
    public ResponseEntity<AlarmInfo> updateAlarmInfo(@RequestBody AlarmInfo alarmInfo) throws URISyntaxException {
        log.debug("REST request to update AlarmInfo : {}", alarmInfo);
        if (alarmInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmInfo result = alarmInfoRepository.save(alarmInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alarmInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-infos} : get all the alarmInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmInfos in body.
     */
    @GetMapping("/alarm-infos")
    public ResponseEntity<List<AlarmInfo>> getAllAlarmInfos(Pageable pageable) {
        log.debug("REST request to get a page of AlarmInfos");
        Page<AlarmInfo> page = alarmInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alarm-infos/:id} : get the "id" alarmInfo.
     *
     * @param id the id of the alarmInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-infos/{id}")
    public ResponseEntity<AlarmInfo> getAlarmInfo(@PathVariable Long id) {
        log.debug("REST request to get AlarmInfo : {}", id);
        Optional<AlarmInfo> alarmInfo = alarmInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alarmInfo);
    }

    /**
     * {@code DELETE  /alarm-infos/:id} : delete the "id" alarmInfo.
     *
     * @param id the id of the alarmInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-infos/{id}")
    public ResponseEntity<Void> deleteAlarmInfo(@PathVariable Long id) {
        log.debug("REST request to delete AlarmInfo : {}", id);

        alarmInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }





}
