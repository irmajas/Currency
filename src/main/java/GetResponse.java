

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetResponse {

    public static void getResponse() {
        try {

            String url = "http://www.lb.lt/WebServices/FxRates/getFxRatesForCurrency/webservices/fxrates/FxRates.asmx/getFxRatesForCurrency?tp=EU&ccy=USD&dtFrom=2019-05-06&dtTo=2019-05-10";
            //String url = "https://api.chucknorris.io/jokes/random?category=animal";

            URL obj = new URL( url );
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod( "GET" );
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader( con.getInputStream() ) );
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append( inputLine );
                }
                in.close();
//            print in String
                System.out.println( response.toString() );
            } else {
                System.out.println( "Klaidos kodas nuskaitant is  http://www.lb.lt/WebServices/FxRates...--->" + responseCode );
            }
        } catch (Exception e) {
            System.out.println( e );
        }
    }
}

