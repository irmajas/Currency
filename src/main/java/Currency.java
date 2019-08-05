import models.Alteration;
import models.CurrencyCode;
import models.FxRate;

import java.io.File;
import java.time.LocalDate;
import java.util.List;


public class Currency {


    public static void main(String[] args) {

        File filename = new File( "C:\\Users\\irmaj\\Desktop\\Currency\\src\\main\\resources\\data.xml" );
        String json = utils.getRatesfromFiles( filename );
        List<FxRate> rates = utils.parseJson( json );
        File valiutosname = new File( "C:\\Users\\irmaj\\Desktop\\Currency\\src\\main\\resources\\valiutos.xml" );
        String jsonvaliutos = utils.getRatesfromFiles( valiutosname );

        List<CurrencyCode> valiutos = utils.parseJsonValiutos( jsonvaliutos );
        LocalDate startdate = LocalDate.parse( "2019-05-06" );

        List<Alteration> alterations = CountingUtils.getAlteration( rates, valiutos, startdate );
        utils.printAlterations( alterations );
        GetResponse.get_response();
    }
}

