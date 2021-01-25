package com.fuxin.alert.web.rest;

import com.fuxin.alert.domain.AlarmRule;
import com.fuxin.alert.repository.AlarmRuleRepository;
import com.fuxin.alert.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing {@link com.fuxin.alert.domain.AlarmRule}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@CrossOrigin
public class AlarmRuleResource {

    private final Logger log = LoggerFactory.getLogger(AlarmRuleResource.class);

    private static final String ENTITY_NAME = "alertAlarmRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlarmRuleRepository alarmRuleRepository;

    public AlarmRuleResource(AlarmRuleRepository alarmRuleRepository) {
        this.alarmRuleRepository = alarmRuleRepository;
    }

    /**
     * {@code POST  /alarm-rules} : Create a new alarmRule.
     *
     * @param alarmRule the alarmRule to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alarmRule, or with status {@code 400 (Bad Request)} if the alarmRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alarm-rules")
    public ResponseEntity<AlarmRule> createAlarmRule(@RequestBody AlarmRule alarmRule) throws URISyntaxException {
        log.debug("REST request to save AlarmRule : {}", alarmRule);
        if (alarmRule.getId() != null) {
            throw new BadRequestAlertException("A new alarmRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlarmRule result = alarmRuleRepository.save(alarmRule);
        return ResponseEntity.created(new URI("/api/alarm-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alarm-rules} : Updates an existing alarmRule.
     *
     * @param alarmRule the alarmRule to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alarmRule,
     * or with status {@code 400 (Bad Request)} if the alarmRule is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alarmRule couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alarm-rules")
    public ResponseEntity<AlarmRule> updateAlarmRule(@RequestBody AlarmRule alarmRule) throws URISyntaxException {
        log.debug("REST request to update AlarmRule : {}", alarmRule);
        if (alarmRule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlarmRule result = alarmRuleRepository.save(alarmRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, alarmRule.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alarm-rules} : get all the alarmRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alarmRules in body.
     */
    @GetMapping("/alarm-rules")
    public ResponseEntity<List<AlarmRule>> getAllAlarmRules(Pageable pageable) {
        log.debug("REST request to get a page of AlarmRules");
        Page<AlarmRule> page = alarmRuleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alarm-rules/:id} : get the "id" alarmRule.
     *
     * @param id the id of the alarmRule to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alarmRule, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alarm-rules/{id}")
    public ResponseEntity<AlarmRule> getAlarmRule(@PathVariable Long id) {
        log.debug("REST request to get AlarmRule : {}", id);
        Optional<AlarmRule> alarmRule = alarmRuleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(alarmRule);
    }

    /**
     * {@code DELETE  /alarm-rules/:id} : delete the "id" alarmRule.
     *
     * @param id the id of the alarmRule to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alarm-rules/{id}")
    public ResponseEntity<Void> deleteAlarmRule(@PathVariable Long id) {
        log.debug("REST request to delete AlarmRule : {}", id);

        alarmRuleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
