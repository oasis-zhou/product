package rf.product.ds.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.product.ds.RateTableService;
import rf.product.dt.DecisionTableService;
import rf.product.dt.DecisionTableSpec;

import java.math.BigDecimal;
import java.util.Map;


@Service
public class RateTableServiceImpl implements RateTableService {

    private static Logger logger = LoggerFactory.getLogger(RateTableServiceImpl.class);

    @Autowired
    private DecisionTableService decisionService;


    @Override
    public Map<String,Object> findRateValues(String tableCode,Map conditions){
        DecisionTableSpec table = decisionService.findRateTable(tableCode);
        Map<String,Object> values = decisionService.findMutlpleItemsValue(table,conditions);

        return values;
    }

    @Override
    public BigDecimal findRateValue(String tableCode,Map conditions){
        BigDecimal value = BigDecimal.ZERO;

        DecisionTableSpec table = decisionService.findRateTable(tableCode);
        Map<String,Object> values = decisionService.findSingleItemValue(table,conditions);
        for(String key : values.keySet()){
            value = new BigDecimal(values.get(key).toString());
        }
        return value;
    }

}
