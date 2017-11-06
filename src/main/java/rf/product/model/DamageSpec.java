package rf.product.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public class DamageSpec extends ModelComponent {

    private String code;
    private String name;
    private List<String> damageFactors = Lists.newArrayList();
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

    public List<String> getDamageFactors() {
        return damageFactors;
    }

    public void setDamageFactors(List<String> damageFactors) {
        this.damageFactors = damageFactors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
