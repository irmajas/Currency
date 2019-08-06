import models.Alteration;
import models.CurrencyCode;
import models.FxRate;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UIMeniu {

    public static void letsStart(Path where) {
        //*****************
        Path whereData= where.resolve("data.xml"  );
        File filename = new File( String.valueOf( whereData ) );
        String json = Utils.getRatesfromFiles( filename );
        List<FxRate> rates = Utils.parseJson( json );
        Path whereValiut= where.resolve("valiutos.xml" );
        File valiutosname = new File( String.valueOf( whereValiut ) );
        String jsonvaliutos = Utils.getRatesfromFiles( valiutosname );

        List<CurrencyCode> valiutos = Utils.parseJsonValiutos( jsonvaliutos );
        LocalDate startdate = LocalDate.parse( "2019-05-06" );
        LocalDate enddate = LocalDate.parse( "2019-05-10" );

        List<Alteration> alterations = CountingUtils.getAlteration( rates, valiutos, startdate );
        //***********************
        boolean iki = true;
        Scanner sc = new Scanner( System.in );
        while (iki) {
            int pasirinkimas = mainMeniu();
            switch (pasirinkimas) {
                //sprendziam testus
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


                    System.out.println( "ivesk data (mmmm-MM-dd):(periodas nuo 2019-05-06 iki 2019-05-10) arba *(VISKAS)" );
                    while (true) {
                        String ivesta = sc.next();
                        if (ivesta.equals( "*" )) {
                            Utils.printAll( rates, valiutos );
                            break;
                        }
                        String pattern = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))";

                        if (ivesta.matches( pattern )) {
                            LocalDate ivestadata = LocalDate.parse( ivesta );
                            if (ivestadata.isBefore( startdate ) || ivestadata.isAfter( enddate )) {
                                System.out.println( "tokiai datai duomenu nera" );
                            } else {
                                List<FxRate> ratesbydate = rates.stream()
                                        .filter( rat -> rat.getDt().isEqual( ivestadata ) ).collect( Collectors.toList() );
                                Utils.printAll( ratesbydate, valiutos );
                                break;
                            }
                        } else {
                            System.out.println( "klaidingai nurodyta data. Pakartokite" );
                        }
                    }

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
        System.out.println( "3 - visu valiutu kursus datai" );
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
}
