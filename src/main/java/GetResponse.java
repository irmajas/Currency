

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetResponse {

    public static void get_response(){
        try {

       // String url = "http://www.lb.lt/WebServices/FxRates/getFxRatesForCurrency/webservices/fxrates/FxRates.asmx/getFxRatesForCurrency?tp=EU&ccy=USD&dtFrom=2019-05-06&dtTo=2019-05-10";
        String url="https://api.chucknorris.io/jokes/random?category=animal";
          System.out.println(url);
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
          con.setRequestMethod( "GET" );
//            con.setInstanceFollowRedirects(false);
//             HttpURLConnection.setFollowRedirects(false);
            con.setRequestProperty("", "");
           // con.setRequestProperty( "Content-Type","application/soap+xml; charset=utf-8" );
            //String xml="<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?> <soap12:Envelope xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\" xmlns:xsd=\\\"http://www.w3.org/2001/XMLSchema\\\" xmlns:soap12=\\\"http://www.w3.org/2003/05/soap-envelope\\\"> <soap12:Body> <getFxRatesForCurrency xmlns=\\\"http://www.lb.lt/WebServices/FxRates\\\"> <tp>EU</tp> <ccy>LTU</ccy> <dtFrom>2019-05-01</dtFrom> <dtTo>2019-05-20</dtTo> </getFxRatesForCurrency> </soap12:Body> </soap12:Envelope>";
//            con.setDoOutput( true );
//            DataOutputStream wr =new DataOutputStream( con.getOutputStream() );
//           // wr.writeBytes( xml );
//            wr.flush();
//            wr.close();
            int responseCode= con.getResponseCode();
            String responseStatus =con.getResponseMessage();
            System.out.println("response status"+responseStatus);
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
 } else
 {
     System.out.println(responseCode);
 }
        }
 catch (Exception e) {
            System.out.println(e);
        }
    }
}

