package co.insight.starter.firebase.web.rest;


import co.insight.starter.firebase.security.AuthoritiesConstants;
import co.insight.starter.firebase.service.AuditEventServiceFireBase;
import co.insight.starter.firebase.web.propertyeditors.LocaleDateTimeEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/app")
public class AuditResourceFirebaseEngine {

    @Autowired
    private AuditEventServiceFireBase auditEventServiceFireBase;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new LocaleDateTimeEditor("yyyy-MM-dd", false));
    }

    @RequestMapping(value = "/rest/audits/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll(Pageable page) {
        return auditEventServiceFireBase.findAll(page).getContent();
    }

    @RequestMapping(value = "/rest/audits/byDates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") LocalDate fromDate, @RequestParam(value = "toDate") LocalDate toDate , Pageable page) {
        return auditEventServiceFireBase.findByDates(fromDate, toDate , page).getContent();
    }

}
