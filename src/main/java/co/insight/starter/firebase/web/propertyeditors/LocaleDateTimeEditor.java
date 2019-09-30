package co.insight.starter.firebase.web.propertyeditors;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

public class LocaleDateTimeEditor extends PropertyEditorSupport {

    private final DateTimeFormatter formatter;
    private final boolean allowEmpty;

    public LocaleDateTimeEditor(String dateFormat, boolean allowEmpty) {
        this.formatter = DateTimeFormat.forPattern(dateFormat);
        this.allowEmpty = allowEmpty;
    }

    public String getAsText() {
        Date value = (Date) getValue();
        return value != null ? new LocalDateTime(value).toString(formatter) : "";
    }

    public void setAsText( String text ) throws IllegalArgumentException {
        if ( allowEmpty && !StringUtils.hasText(text) ) {
            setValue(null);
        } else {
            setValue(new LocalDateTime(formatter.parseDateTime(text)));
        }
    }
}
