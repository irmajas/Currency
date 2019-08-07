import models.Alteration;
import models.CurrencyCode;
import models.FxRate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UIMeniu {
    final static LocalDate STARTDATE = LocalDate.parse( "2014-09-30" );
    final static LocalDate DATEEU = LocalDate.parse( "2015-01-01" );

    public static void letsStart() {

        String valiutosurl = "http://www.lb.lt/webservices/fxrates/FxRates.asmx/getCurrencyList";
        String xmlvaliutos = GetResponse.getResponse( valiutosurl );
        String jsonvaliutos = Utils.xmlToJson( xmlvaliutos );
        List<CurrencyCode> valiutos = Utils.parseJsonValiutos( jsonvaliutos );

        LocalDate startdate = getDate( "periodo pradzios data" );
        LocalDate enddate = getDate( "periodo pabaigos data" );
        if (startdate.isAfter( enddate )) {
            LocalDate tarp = startdate;
            startdate = enddate;
            enddate = tarp;
        }
        String ratesurl = "http://www.lb.lt/webservices/fxrates/FxRates.asmx/getFxRates";
        if (startdate.isAfter( STARTDATE )) {
            ratesurl = ratesurl + "?tp=EU";
        } else {
            ratesurl = ratesurl + "?tp=LT";
        }
        String ratesStartUrl = ratesurl + "&dt=" + startdate;
        String ratesEndUrl = ratesurl + "&dt=" + enddate;
        String ratesperiodUrl = "http://www.lb.lt/webservices/fxrates/FxRates.asmx/getFxRatesForCurrency";
        if (startdate.isAfter( STARTDATE )) {
            ratesperiodUrl = ratesperiodUrl + "?tp=EU&ccy=&";
        } else {
            ratesperiodUrl = ratesperiodUrl + "?tp=LT&ccy=&";
        }
        ratesperiodUrl = ratesperiodUrl + "dtFrom=" + startdate;
        ratesperiodUrl = ratesperiodUrl + "&dtTo=" + enddate;
        System.out.println(ratesperiodUrl);
//        String xmlrates = GetResponse.getResponse( ratesStartUrl );
//        String jsonrates = Utils.xmlToJson( xmlrates );
//        List<FxRate> rates = Utils.parseJson( jsonrates );
//        xmlrates = GetResponse.getResponse( ratesEndUrl );
//        jsonrates = Utils.xmlToJson( xmlrates );
//        rates.addAll( Utils.parseJson( jsonrates ) );
        String xmlrates = GetResponse.getResponse( ratesperiodUrl );
        String jsonrates = Utils.xmlToJson( xmlrates );
        List<FxRate> rates = Utils.parseJson( jsonrates );
        List<Alteration> alterations = CountingUtils.getAlteration( rates, valiutos, startdate );
        //***********************
        boolean iki = true;
        Scanner sc = new Scanner( System.in );
        while (iki) {
            int pasirinkimas = mainMeniu();
            switch (pasirinkimas) {

                case 1:
                    Utils.printAlterations( alterations );
                    break;
                case 2:


                    System.out.println( "valiutos kodas" );

                    while (true) {

                        String ivesta = sc.next().toUpperCase();
                        if (ivesta.equals( "*" )) {
                            break;
                        }
                        String patternletter = "[A-Z]{3}";
                        if (ivesta.matches( patternletter )) {
                            long count = valiutos.stream().filter( val -> val.getCode().equals( ivesta ) ).count();
                            if (count > 0) {
                                List<Alteration> codeAlter = alterations.stream().filter( val -> val.getCurrency().equals( ivesta ) ).collect( Collectors.toList() );
                                List<FxRate> codeRate = rates.stream().filter( (val -> val.getCurrency().equals( ivesta )) ).collect( Collectors.toList() );
                                Utils.printAll( codeRate, valiutos );
                                Utils.printAlterations( codeAlter );
                                break;
                            } else {
                                System.out.println( "Tokia valiuta neegzistuoja. pakartokite arba *" );
                            }
                        } else {
                            System.out.println( "valiutos kodas yra 3 raides. pakartokite arba *" );
                        }
                    }
                    break;
                case 3:

                    LocalDate nuo = startdate;
                    LocalDate ikikada = enddate;
                    List<FxRate> ratesbydate = rates.stream()
                            .filter( rat -> rat.getDt().isEqual( nuo ) || rat.getDt().isEqual( ikikada ) ).collect( Collectors.toList() );
                    ratesbydate.sort( Comparator.comparing( FxRate::getCurrency ) );
                    Utils.printAll( ratesbydate, valiutos );


                    break;
                default:
                    iki = false;

            }
        }
    }

    public static int mainMeniu() {

        System.out.println( "Ką norite matyti:" );
        System.out.println( "1 - visu valiutu pokycius" );
        System.out.println( "2 - vienos valiutus kursus ir pokycius" );
        System.out.println( "3 - visu valiutu kursus nurodytoms datoms" );
        System.out.println( "bent koks kitas skaičius, jei norite baigti darbą" );
        Scanner sc = new Scanner( System.in );
        String ivesta = sc.next();
        switch (ivesta) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            default:
                return 0;
        }

    }

    static LocalDate getDate(String message) {
        Scanner sc = new Scanner( System.in );

        System.out.println( "ivesk data (mmmm-MM-dd):(periodas nuo 2014-09-30 iki dabar)" );
        while (true) {
            String ivesta = sc.next();
            String pattern = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

            if (ivesta.matches( pattern )) {
                LocalDate date = LocalDate.parse( ivesta );
                if (date.isBefore( STARTDATE ) || date.isAfter( LocalDate.now() )) {
                    System.out.println( " Lietuvos bankas duomenis teikia nuo 2014-09-30 iki šiandien. pakartokite" );
                } else {
                    return date;
                }

            } else {
                System.out.println( "klaidingas datos formatas. pakartokite" );
            }

        }

    }
}
