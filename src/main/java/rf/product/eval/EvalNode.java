package rf.product.eval;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public class EvalNode {
    private String productCode;
    private Map<String,Object> factors = Maps.newHashMap();
    private String purpose;
    private String businessKey;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Map<String, Object> getFactors() {
        return factors;
    }

    public void setFactors(Map<String, Object> factors) {
        this.factors = factors;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
