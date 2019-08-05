import models.Alteration;
import models.CurrencyCode;
import models.FxRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CountingUtils {
    //************************ skaiciuoti valiutos kurso pokyciu duotam periode
    public static List<Alteration> getAlteration(List<FxRate> rateList, List<CurrencyCode> valiutos, LocalDate notbefor) {
        List<Alteration> alterations = new ArrayList<>();
        for (CurrencyCode val : valiutos) {

            List<FxRate> forSomeCurrency = rateList.stream()
                    .filter( rate -> rate.getCurrency().equals(val.getCode() )  ).collect( Collectors.toList() );
            if (forSomeCurrency.size() > 0) {
                forSomeCurrency.sort( (FxRate r1, FxRate r2) -> r1.getDt().isAfter( r2.getDt() ) ? 0 : 1 );
                if (!forSomeCurrency.get( forSomeCurrency.size() - 1 ).getDt().isBefore( notbefor )) {
                    float last = forSomeCurrency.get( 0 ).getRate();
                    float first = forSomeCurrency.get( forSomeCurrency.size() - 1 ).getRate();
                    alterations.add( new Alteration( val.getCode(), last - first, val.getNameLTU(), forSomeCurrency.get( 0 ).getDt(), forSomeCurrency.get( forSomeCurrency.size() - 1 ).getDt() ) );
                }
            }
        }
        return alterations;


    }
}
