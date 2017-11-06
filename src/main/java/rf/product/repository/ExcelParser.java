package rf.product.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.joda.time.DateTime;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import rf.product.conf.AppContext;
import rf.product.dt.*;
import rf.product.exception.GenericException;
import rf.product.model.FactorSpec;
import rf.product.model.enums.DataType;
import rf.product.model.enums.FactorCategory;
import rf.product.utils.ExcelReader;

import java.io.File;
import java.util.List;
import java.util.Map;


@Component
public class ExcelParser {

    public List<FactorSpec> parseFactors(){
        List<FactorSpec> factorSpecs = Lists.newArrayList();

        try {
            String FACTOR_FILE_PATH = "classpath*:Factor.xlsx";
            Resource[] resources = AppContext.getApplicationContext().getResources(FACTOR_FILE_PATH);

            File file = resources[0].getFile();

            ExcelReader reader = new ExcelReader(file, "FACTORS");

            List<Map<String,String>> mapData = reader.getAllMapData();
            mapData.forEach(map -> {
                if(map.get("FACTOR_CODE") != null && !map.get("FACTOR_CODE").equals("")) {
                    FactorSpec spec = new FactorSpec();
                    spec.setCode(map.get("FACTOR_CODE"));
                    spec.setName(map.get("FACTOR_NAME"));
                    spec.setCategory(FactorCategory.valueOf(map.get("CATEGORY")));
                    spec.setDataType(DataType.valueOf(map.get("DATA_TYPE")));
                    String optionValues = map.get("OPTION_VALUES");
                    if(optionValues != null && !optionValues.equals("")){
                        Map<String,String> values = Maps.newHashMap();
                        for (String value : optionValues.split(",")) {
                            String[] temps = value.split(":");
                            values.put(temps[0],temps[1]);
                        }
                        spec.setOptionValues(values);
                    }

                    spec.setValidationExpress(map.get("VALIDATION"));
                    spec.setDescription(map.get("DESCRIPTION"));

                    factorSpecs.add(spec);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new GenericException(e);
        }

        return factorSpecs;
    }

    public List<DecisionTableSpec> parseRateTables(){
        List<DecisionTableSpec> rateTableSpecs = Lists.newArrayList();

        try {
            String RATE_FILE_PATH = "classpath*:RATE_*.xlsx";
            Resource[] resources = AppContext.getApplicationContext().getResources(RATE_FILE_PATH);

            for(Resource r : resources){
                File file = r.getFile();

                ExcelReader specReader = new ExcelReader(file, "RATE_SPEC");

                DecisionTableSpec tableSpec = buildDecisionTableSpec(specReader);
                rateTableSpecs.add(tableSpec);

                ExcelReader rateReader = new ExcelReader(file, "RATES");
                List<Map<String,String>> mapData = rateReader.getAllMapData();
                mapData.forEach(map -> {
                    TableItem item = new TableItem();
                    tableSpec.getSubComponents().add(item);
                    for (Map.Entry<String, String> me : map.entrySet()) {
                        TableColumn column = new TableColumn();
                        item.getColumns().add(column);
                        column.setName(me.getKey());
                        if (me.getValue().matches("^(\\[|\\()(\\d+,\\d+)(\\]|\\))$")) {
                            String temp = me.getValue().replaceAll("\\[|\\]|\\(|\\)", "");
                            String[] values = temp.split(",");
                            column.setMinValue(values[0]);
                            column.setMaxValue(values[1]);

                        } else {
                            column.setValue(me.getValue());
                        }
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GenericException(e);
        }

        return rateTableSpecs;
    }

    private DecisionTableSpec buildDecisionTableSpec(ExcelReader specReader) {
        DecisionTableSpec table = new DecisionTableSpec();
        table.setStatus(DTStatus.EFFECTIVE);
        List<Map<String,String>> mapData = specReader.getAllMapData();
        for(int index = 0;index < mapData.size();index ++ ){
            if(index == 0){
                Map<String,String> tableName = mapData.get(index);
                table.setName(tableName.get("ITEM_VALUE"));

            }else if(index == 1){
                Map<String,String> tableCode = mapData.get(index);
                table.setCode(tableCode.get("ITEM_VALUE"));

            }else if(index == 2){
                Map<String,String> effDate = mapData.get(index);
                table.setEffectiveDate(new DateTime(effDate.get("ITEM_VALUE")).toDate());

            }else if(index == 3){
                Map<String,String> expDate = mapData.get(index);
                table.setExpiredDate(new DateTime(expDate.get("ITEM_VALUE")).toDate());

            }else{
                Map<String,String> columMap = mapData.get(index);

                ColumnSpec column = new ColumnSpec();

                column.setName(columMap.get("ITEM_VALUE"));
                if(columMap.get("CONDITION") != null &&  !columMap.get("CONDITION").equals(""))
                column.setCondition(Boolean.valueOf(columMap.get("CONDITION")));
                if(columMap.get("SCOPE_PATTERN") != null && !columMap.get("SCOPE_PATTERN").equals(""))
                column.setScopePattern(ScopePattern.valueOf(columMap.get("SCOPE_PATTERN")));
                if(columMap.get("DATA_TYPE") != null && !columMap.get("DATA_TYPE").equals(""))
                column.setDataType(DataType.valueOf(columMap.get("DATA_TYPE")));

                table.getColumnSpecs().put(column.getName(),column);
            }
        }
        return table;
    }

}
