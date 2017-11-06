package rf.product.ds;

import rf.product.model.FormulaSpec;
import rf.product.model.ProductSpec;
import rf.product.model.RuleSpec;

import java.util.List;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public interface ProductService {

    public ProductSpec findProduct(String productCode);

    public void saveProduct(ProductSpec product);

    public List<FormulaSpec> findFormulas(String productCode,String businessKey,String purpose);

    public List<RuleSpec> findRules(String productCode,String purpose);
}
