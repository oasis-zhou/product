package rf.product.ds;

import java.math.BigDecimal;
import java.util.Map;

public interface RateTableService {

    public Map<String,Object> findRateValues(String tableCode, Map conditions);

    public BigDecimal findRateValue(String tableCode, Map conditions);
}
