package com.fuxin.alert.web.rest;

import com.fuxin.alert.domain.AlarmInfo;
import com.fuxin.alert.domain.PlatformAlert;
import com.fuxin.alert.repository.AlarmInfoRepository;
import com.fuxin.alert.repository.PlatformAlertRepository;
import com.fuxin.alert.service.AlertService;
import com.fuxin.alert.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fuxin.alert.domain.PlatformAlert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlatformAlertResource {

    private final Logger log = LoggerFactory.getLogger(PlatformAlertResource.class);

    private static final String ENTITY_NAME = "alertPlatformAlert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    private final AlarmInfoRepository alarmInfoRepository;

    @Autowired
    private AlertService alertService;

    private final PlatformAlertRepository platformAlertRepository;

    public PlatformAlertResource(PlatformAlertRepository platformAlertRepository,AlarmInfoRepository alarmInfoRepository) {
        this.platformAlertRepository = platformAlertRepository;
        this.alarmInfoRepository = alarmInfoRepository;
    }

    /**
     * {@code POST  /platform-alerts} : Create a new platformAlert.
     *
     * @param platformAlert the platformAlert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new platformAlert, or with status {@code 400 (Bad Request)} if the platformAlert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/platform-alerts")
    public ResponseEntity<PlatformAlert> createPlatformAlert(@RequestBody PlatformAlert platformAlert) throws URISyntaxException {
        log.debug("REST request to save PlatformAlert : {}", platformAlert);
        if (platformAlert.getId() != null) {
            throw new BadRequestAlertException("A new platformAlert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatformAlert result = platformAlertRepository.save(platformAlert);
        return ResponseEntity.created(new URI("/api/platform-alerts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /platform-alerts} : Updates an existing platformAlert.
     *
     * @param platformAlert the platformAlert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platformAlert,
     * or with status {@code 400 (Bad Request)} if the platformAlert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the platformAlert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/platform-alerts")
    public ResponseEntity<PlatformAlert> updatePlatformAlert(@RequestBody PlatformAlert platformAlert) throws URISyntaxException {
        log.debug("REST request to update PlatformAlert : {}", platformAlert);
        if (platformAlert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlatformAlert result = platformAlertRepository.save(platformAlert);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, platformAlert.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /platform-alerts} : get all the platformAlerts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of platformAlerts in body.
     */
    @GetMapping("/platform-alerts")
    public List<PlatformAlert> getAllPlatformAlerts() {
        log.debug("REST request to get all PlatformAlerts");
        return platformAlertRepository.findAll();
    }

    /**
     * {@code GET  /platform-alerts/:id} : get the "id" platformAlert.
     *
     * @param id the id of the platformAlert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the platformAlert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/platform-alerts/{id}")
    public ResponseEntity<PlatformAlert> getPlatformAlert(@PathVariable Long id) {
        log.debug("REST request to get PlatformAlert : {}", id);
        Optional<PlatformAlert> platformAlert = platformAlertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(platformAlert);
    }

    /**
     * {@code DELETE  /platform-alerts/:id} : delete the "id" platformAlert.
     *
     * @param id the id of the platformAlert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/platform-alerts/{id}")
    public ResponseEntity<Void> deletePlatformAlert(@PathVariable Long id) {
        log.debug("REST request to delete PlatformAlert : {}", id);

        platformAlertRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }



    @Scheduled(cron = "0 0/1 * * * ?")
    @GetMapping("/suanfa/getAlerts")
    public List<PlatformAlert> getPlatformAlerts() throws IOException {
        List<PlatformAlert> suanfaAlert = alertService.getSuanfaAlert();
        return suanfaAlert;
    }


    @Scheduled(cron = "0 0/1 * * * ?")
    @GetMapping("/rizhi/getAlert")
    public List getAlert() throws IOException {
        List alert = alertService.getRizhiAlert();
        return alert;
    }

    @PostMapping("/receive/{id}")
    public String receive(@PathVariable String id,@RequestParam String status) throws URISyntaxException {
        log.debug("参数id:"+id + "    status:"+status);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        String s0 = id.split("-")[0];
        String s1= id.split("-")[1];
        if(s0.equals("01")) {
            AlarmInfo alarmInfo = alarmInfoRepository.findById(Long.valueOf(s1)).get();
            if (status.equals("1") || status.equals("2") || status.equals("3")) {
                try {
                    alarmInfo.setChecked(Integer.valueOf(status));
                    alarmInfoRepository.save(alarmInfo);
                    jsonObject.put("id", id);
                    jsonObject.put("desc", "200");
                } catch (Exception e) {
                    jsonObject.put("id", id);
                    jsonObject.put("desc", "201");
                    e.printStackTrace();
                }
            } else {
                jsonObject.put("id", id);
                jsonObject.put("desc", "202");
            }

        } else if(s0.equals("02")){
            PlatformAlert platformAlert = platformAlertRepository.findById(Long.valueOf(s1)).get();
            if (status.equals("1") || status.equals("2") || status.equals("3")) {
                try {
                    platformAlert.setEventStatus(status);
                    platformAlertRepository.save(platformAlert);
                    jsonObject.put("id", id);
                    jsonObject.put("desc", "200");
                } catch (Exception e) {
                    jsonObject.put("id", id);
                    jsonObject.put("desc", "201");
                    e.printStackTrace();
                }
            } else {
                jsonObject.put("id", id);
                jsonObject.put("desc", "202");
            }

        }

        return jsonObject.toString();
    }

}
