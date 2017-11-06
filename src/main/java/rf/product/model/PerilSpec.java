package rf.product.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public class PerilSpec extends ModelComponent {

    private String code;
    private String name;
    private List<String> perilFactors = Lists.newArrayList();
    private String description;

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

    public List<String> getPerilFactors() {
        return perilFactors;
    }

    public void setPerilFactors(List<String> perilFactors) {
        this.perilFactors = perilFactors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
