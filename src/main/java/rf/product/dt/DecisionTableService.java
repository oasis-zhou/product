package rf.product.dt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


import rf.product.model.enums.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import rf.product.dt.repository.DecisionTableDao;
import rf.product.dt.repository.pojo.TDecisionTable;
import rf.product.exception.GenericException;
import rf.product.utils.JsonHelper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;




@Component
public class DecisionTableService {

    private static Logger logger = LoggerFactory.getLogger(DecisionTableService.class);

    @Autowired
    private DecisionTableDao decisionTableDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Cacheable(value="ratetable" , key = "#tableCode")
    public DecisionTableSpec findRateTable(String tableCode) {
        logger.debug("Load rate table " + tableCode + " first.\n");

        TDecisionTable po = decisionTableDao.findByCode(tableCode);

        DecisionTableSpec table = null;
        if(po != null && po.getContent() != null){
            table = jsonHelper.fromJSON(po.getContent(), DecisionTableSpec.class);
        }

        return table;

    }


    public void saveRateTable(DecisionTableSpec table){

        TDecisionTable po = decisionTableDao.findByCode(table.getCode());
        if(po == null) {
            po = new TDecisionTable();
            po.setUuid(table.getUuid());
        }

        String content = jsonHelper.toJSON(table);

        po.setCode(table.getCode());
        po.setName(table.getName());
        po.setEffectiveDate(table.getEffectiveDate());
        po.setExpiredDate(table.getExpiredDate());
        po.setStatus(table.getStatus().name());

        po.setContent(content);

        decisionTableDao.save(po);

    }

    public Map<String,Object> findSingleItemValue(DecisionTableSpec table, Map<String,Object> conditions){

        Map<String,Object> value = Maps.newHashMap();

        List<TableItem> items = findMatchItems(conditions,table);

        if(items.size() > 0){
            TableItem item = items.get(0);

            for(TableColumn column : item.getColumns()) {
                ColumnSpec spec = table.getColumnSpecs().get(column.getName());

                if(spec.getCondition() == null || !spec.getCondition()){
                    value.put(column.getName(),column.getValue());
                }
            }
        }

        return value;
    }

    public Map<String,Object> findMutlpleItemsValue(DecisionTableSpec table, Map<String,Object> conditions){

        Map<String,Object> values = Maps.newHashMap();

        List<TableItem> items = findMatchItems(conditions, table);

        for(TableItem item : items){
            Map<String,Object> value = Maps.newHashMap();
            for(TableColumn column : item.getColumns()) {
                ColumnSpec spec = table.getColumnSpecs().get(column.getName());
                if(!spec.getCondition()){
                    value.put(column.getName(),column.getValue());
                }

            }
            values.putAll(value);
        }

        return values;
    }



    private List<TableItem> findMatchItems(Map<String,Object> conditions,DecisionTableSpec table){

        List<TableItem> its = Lists.newArrayList();

        List<TableItem> items = table.getSubComponentsByType(TableItem.class);

        if(items.size() == 0)
            throw new GenericException("No data match");


            for(TableItem item : items){
                boolean isMatch = true;
                for(TableColumn column : item.getColumns()){
                    ColumnSpec spec = table.getColumnSpecs().get(column.getName());
                    Object condition = conditions.get(column.getName());
                    if(spec.getCondition() != null && spec.getCondition() && spec.getScopePattern() != null && condition != null){
                        isMatch = compareForScope(column.getMaxValue(),column.getMinValue(),condition,spec.getDataType().name(),spec.getScopePattern().name());
                    }else if(spec.getCondition() != null && spec.getCondition() && spec.getScopePattern() == null && condition != null){
                        isMatch = compare(column.getValue(),condition,spec.getDataType().name());
                    }
                    if(!isMatch)
                        break;
                }

                if(isMatch){
                    its.add(item);
                }
            }


        return its;
    }

    private boolean compare(Object value,Object condition,String dataType){
        boolean isMatch = true;
        int r = 0;

        if(dataType.equals(DataType.DATE.name())) {
            Date c = (Date)condition;
            Date v = (Date)value;

            r = c.compareTo(v);
        }

        if(dataType.equals(DataType.STRING.name())) {
            String c = (String)condition;
            String v = (String)value;

            r = c.compareTo(v);
        }

        if(dataType.equals(DataType.NUMBER.name())) {
            BigDecimal c = new BigDecimal(condition.toString());
            BigDecimal v = new BigDecimal(value.toString());

            r = c.compareTo(v);
        }

        if(r != 0)
            isMatch = false;


        return isMatch;
    }

    private boolean compareForScope(Object max,Object min,Object condition,String dataType,String scopePattern){
        boolean isMatch = true;
        int up = 0;
        int down = 0;

        if(dataType.equals(DataType.DATE.name())) {
            Date c = (Date)condition;
            Date upData = (Date)max;
            Date downData = (Date)min;

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(dataType.equals(DataType.STRING.name())) {
            String c = (String)condition;
            String upData = (String)max;
            String downData = (String)min;

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(dataType.equals(DataType.NUMBER.name())) {
            BigDecimal c = new BigDecimal(condition.toString());
            BigDecimal upData = new BigDecimal(max.toString());
            BigDecimal downData = new BigDecimal(min.toString());

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(scopePattern.equals(ScopePattern.CLOSE_TO_CLOSE.name())){
            if(!(up <= 0 && down >= 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.CLOSE_TO_OPEN.name())){
            if(!(up < 0 && down >= 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.OPEN_TO_CLOSE.name())){
            if(!(up <= 0 && down > 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.OPEN_TO_OPEN.name())){
            if(!(up < 0 && down > 0)){
                isMatch = false;
            }
        }

        return isMatch;
    }




}
