package consignprice.service;

import consignprice.domain.GetPriceDomain;
import consignprice.domain.Information;
import consignprice.domain.PriceConfig;

public interface ConsignPriceService {
    double getPriceByWeightAndRegion(GetPriceDomain domain);
    String queryPriceInformation();
    boolean createAndModifyPrice(PriceConfig config);
    PriceConfig getPriceConfig();
    double getDrawbackPercent(Information information);
}
