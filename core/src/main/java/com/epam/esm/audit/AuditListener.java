package com.epam.esm.audit;

import lombok.extern.slf4j.Slf4j;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Obidjon Eshmamatov
 * @project rest_api_advanced_2
 * @created 31/05/2022 - 4:46 PM
 */

@Slf4j
public class AuditListener {
    @PostRemove
    public void afterDelete(Object obj) {
        log.info("USER AUDIT -> deleting process, TIME: " + getCurrentTime());
    }

    @PrePersist
    public void beforePersist(Object obj) {
        log.info("USER AUDIT -> persisting process, TIME: " + getCurrentTime());
    }

    @PreUpdate
    public void beforeUpdate(Object obj) {
        log.info("USER AUDIT -> updating process, TIME: " + getCurrentTime());
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
