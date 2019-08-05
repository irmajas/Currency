
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Alteration;
import models.CurrencyCode;
import models.FxRate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    static String toJson(File file) throws IOException {
        Reader fileReader = new FileReader( file );
        BufferedReader bufReader = new BufferedReader( fileReader );
        StringBuilder sb = new StringBuilder();
        String line = bufReader.readLine();
        while (line != null) {
            sb.append( line ).append( "\n" );
            line = bufReader.readLine();
        }
        String xml2String = sb.toString();

        return xml2String;
    }

    static String getRatesfromFiles(File filename) {

        try {
            Path file = filename.toPath();

            String xmlstring = Utils.toJson( filename );
            JSONObject xmlJSONObj = XML.toJSONObject( xmlstring );
            String jsonPrettyPrintString = xmlJSONObj.toString( PRETTY_PRINT_INDENT_FACTOR );

            return jsonPrettyPrintString ;
        } catch (
                JSONException je) {
            System.out.println( je.toString() );
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    static List<FxRate> parseJson(String json) {
        List<FxRate> rates = new ArrayList<>();
        JSONObject obj = new JSONObject( json );
        String url = obj.getJSONObject( "FxRates" ).getString( "xmlns" );

        JSONArray arr = obj.getJSONObject( "FxRates" ).getJSONArray( "FxRate" );
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject classObj=arr.getJSONObject( i );
            FxRate fxRate= new FxRate();
            fxRate.setDt( classObj.getString( "Dt" ) );

            JSONArray cc = classObj.getJSONArray( "CcyAmt" );

            JSONObject rate1obj=cc.getJSONObject( 0 );
            JSONObject rate2obj=cc.getJSONObject( 1 );

            fxRate.setCurrency( rate2obj.getString( "Ccy" )   );
            fxRate.setRate(rate2obj.optFloat( "Amt" )  );

            rates.add( fxRate );

        }
        return rates;
    }
    static List<CurrencyCode> parseJsonValiutos(String json) {
        List<CurrencyCode> valiutos = new ArrayList<>();
        JSONObject obj = new JSONObject( json );
        String url = obj.getJSONObject( "CcyTbl" ).getString( "xmlns" );

        JSONArray arr = obj.getJSONObject( "CcyTbl" ).getJSONArray( "CcyNtry" );


        for (int i = 0; i < arr.length(); i++) {
            JSONObject classObj=arr.getJSONObject( i );
           CurrencyCode currencyCode= new CurrencyCode();
            currencyCode.setCode( classObj.getString( "Ccy" ) );

            JSONArray cc = classObj.getJSONArray( "CcyNm" );

            JSONObject rate1obj=cc.getJSONObject( 0 );
            JSONObject rate2obj=cc.getJSONObject( 1 );

            currencyCode.setNameLTU( rate1obj.getString( "content" )   );
            currencyCode.setNameEN( rate2obj.getString( "content" ) );

            valiutos.add( currencyCode );

        }
        return valiutos;
    }
    static void printAlterations (List<Alteration> alter){
        for (Alteration alt :alter ) {
            System.out.println(" "+alt.getName()+" kurso pokytis nuo " +alt.getFirstdate()+" iki " +alt.getLastdate()+" yra"+alt.getAtteration());
        }
    }
}