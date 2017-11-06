package rf.product.model;

import com.google.common.collect.Lists;
import rf.product.model.enums.RulePurpose;


import java.util.List;


public class RuleSpec extends ModelComponent {
    private String code;
    private String name;
    private RulePurpose purpose;
    private String description;
    private List<String> factors = Lists.newArrayList();
    private String body;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RulePurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(RulePurpose purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFactors() {
        return factors;
    }

    public void setFactors(List<String> factors) {
        this.factors = factors;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
