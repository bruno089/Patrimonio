package com.gabru.Patrimonio.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FechaConverter {
    public static LocalDate stringtoLocalDate(String fecha , String patronDeEntrada ){
        if (fecha == null) return null;

        LocalDate localDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern(patronDeEntrada));
        return localDate;
    }

    public static LocalDateTime stringToLocalDateTimeSinHora(String fecha, String patronDeEntrada){
        LocalDate fechaLocalDate = stringtoLocalDate(fecha,patronDeEntrada);
        LocalDateTime fechaResultado = LocalDateTime.of(fechaLocalDate, LocalDateTime.now().toLocalTime());
        return fechaResultado;
    }

    public static LocalDateTime stringToLocalDateTime(String fecha){ // "2020-07-02T08:32:12"
        LocalDateTime dateTime = LocalDateTime.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return dateTime;
    }

    public static LocalDateTime getInicioDia(String fecha){
        return fecha != null ? stringtoLocalDate(fecha,"yyyy-MM-dd").atStartOfDay() : null;
    }

    public static LocalDateTime getFinalDia(String fecha){
        return fecha != null ? stringtoLocalDate(fecha,"yyyy-MM-dd").atTime(LocalTime.MAX).minusMinutes(30) : null;
    }

}