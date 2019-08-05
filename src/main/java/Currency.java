import models.Alteration;
import models.CurrencyCode;
import models.FxRate;

import java.io.File;
import java.time.LocalDate;
import java.util.List;


public class Currency {


    public static void main(String[] args) {

        File filename = new File( "C:\\Users\\irmaj\\Desktop\\Currency\\src\\main\\resources\\data.xml" );
        String json = Utils.getRatesfromFiles( filename );
        List<FxRate> rates = Utils.parseJson( json );
        File valiutosname = new File( "C:\\Users\\irmaj\\Desktop\\Currency\\src\\main\\resources\\valiutos.xml" );
        String jsonvaliutos = Utils.getRatesfromFiles( valiutosname );

        List<CurrencyCode> valiutos = Utils.parseJsonValiutos( jsonvaliutos );
        LocalDate startdate = LocalDate.parse( "2019-05-06" );

        List<Alteration> alterations = CountingUtils.getAlteration( rates, valiutos, startdate );
        Utils.printAlterations( alterations );
        GetResponse.get_response();
    }
}

