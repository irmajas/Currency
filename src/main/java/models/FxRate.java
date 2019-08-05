package models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FxRate {
    LocalDate dt;
    String currency;
    float rate;

    public void setDt(String date) {
        this.dt = LocalDate.parse( date );
    }
}
