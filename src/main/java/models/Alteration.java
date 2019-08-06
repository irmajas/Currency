package models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Alteration {
    String currency;
    float atteration;
    String name;
    LocalDate firstdate;
    LocalDate lastdate;

    public Alteration(String currency, float atteration, String name, LocalDate lastdate, LocalDate firstdate) {
        this.currency = currency;
        this.atteration = atteration;
        this.name = name;
        this.firstdate = firstdate;
        this.lastdate = lastdate;
    }
}
