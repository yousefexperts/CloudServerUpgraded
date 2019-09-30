package co.insight.starter.firebase.config.audit;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import co.insight.starter.firebase.domain.PersistentAuditEvent;

import java.util.*;

@Configuration
public class AuditEventConverterFireBase {

    public List<AuditEvent> convertToAuditEvent(Iterable<PersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<AuditEvent> auditEvents = new ArrayList<>();
        for (PersistentAuditEvent persistentAuditEvent : persistentAuditEvents) {
            AuditEvent auditEvent = new AuditEvent(persistentAuditEvent.getAuditEventDate().toString(), persistentAuditEvent.getPrincipal(), persistentAuditEvent.getAuditEventType());
            auditEvents.add(auditEvent);
        }
        return auditEvents;
    }

    public Map<String, Object> convertDataToObjects(Map<String, String> data) {
        Map<String, Object> results = new HashMap<>();
        if (data != null) {
            for (String key : data.keySet()) {
                results.put(key, data.get(key));
            }
        }
        return results;
    }

    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>();

        if (data != null) {
            for (String key : data.keySet()) {
                Object object = data.get(key);
                if (object instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) object;
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else {
                    results.put(key, object.toString());
                }
            }
        }
        return results;
    }
}
