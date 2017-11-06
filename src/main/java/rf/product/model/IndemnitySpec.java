package rf.product.model;

import com.google.common.collect.Lists;
import rf.product.model.enums.IndemnityType;


import java.util.List;


public class IndemnitySpec extends ModelComponent {
    private IndemnityType indemnityType;
    private String defaultValue;
    private Boolean isFixed;
    private List<String> valueOptions = Lists.newArrayList();


    public IndemnityType getIndemnityType() {
        return indemnityType;
    }

    public void setIndemnityType(IndemnityType indemnityType) {
        this.indemnityType = indemnityType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getFixed() {
        return isFixed;
    }

    public void setFixed(Boolean fixed) {
        isFixed = fixed;
    }

    public List<String> getValueOptions() {
        return valueOptions;
    }

    public void setValueOptions(List<String> valueOptions) {
        this.valueOptions = valueOptions;
    }

}
