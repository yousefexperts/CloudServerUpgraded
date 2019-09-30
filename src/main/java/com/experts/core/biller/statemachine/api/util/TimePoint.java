package com.experts.core.biller.statemachine.api.util;

import com.experts.core.biller.statemachine.api.constraints.ISODate;
import com.experts.core.biller.statemachine.api.constraints.ISODateTime;
import lombok.Value;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class TimePoint implements Serializable {
    private static final long serialVersionUID = 1L;

    @ISODate
    private LocalDate day;

    @ISODateTime
    private LocalDateTime date;

    public LocalDate day() {
        return getDay();
    }

    public LocalDateTime date() {
        return getDate();
    }


    public boolean equalsDay(LocalDate targetDay) {
        return day.compareTo(targetDay) == 0;
    }


    public boolean beforeDay(LocalDate targetDay) {
        return day.compareTo(targetDay) < 0;
    }


    public boolean beforeEqualsDay(LocalDate targetDay) {
        return day.compareTo(targetDay) <= 0;
    }

    public boolean afterDay(LocalDate targetDay) {
        return 0 < day.compareTo(targetDay);
    }

    public boolean afterEqualsDay(LocalDate targetDay) {
        return 0 <= day.compareTo(targetDay);
    }

    public static TimePoint of(LocalDate day, LocalDateTime date) {
        return new TimePoint(day, date);
    }

    public static TimePoint of(LocalDate day) {
        return of(day, day.atStartOfDay());
    }

    public static TimePoint now() {
        LocalDateTime now = LocalDateTime.now();
        return of(now.toLocalDate(), now);
    }

    public static TimePoint now(Clock clock) {
        LocalDateTime now = LocalDateTime.now(clock);
        return of(now.toLocalDate(), now);
    }

}
