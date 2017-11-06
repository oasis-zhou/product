package rf.product.ds.impl;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rf.product.ds.ProductService;
import rf.product.model.CoverageSpec;
import rf.product.model.FormulaSpec;
import rf.product.model.ProductSpec;
import rf.product.model.RuleSpec;
import rf.product.repository.ProductDao;
import rf.product.repository.pojo.TProduct;
import rf.product.utils.JsonHelper;

import java.util.List;

/**
 * Created by zhouzheng on 2017/9/13.
 */
@Service
public class ProductServiceImpl implements ProductService{

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JsonHelper jsonHelper;

    @Override
    @Cacheable(value="product" , key = "#productCode")
    public ProductSpec findProduct(String productCode) {
        logger.debug("Load product " + productCode + " first.\n");
        TProduct po = productDao.findByCode(productCode);

        ProductSpec product = null;
        if(po != null && po.getContent() != null){
            product = jsonHelper.fromJSON(po.getContent(), ProductSpec.class);
        }

        return product;
    }

    @Override
    public void saveProduct(ProductSpec product){

        TProduct po = productDao.findByCode(product.getCode());
        if(po == null) {
            po = new TProduct();
            po.setUuid(product.getUuid());
        }else{
            product.setUuid(po.getUuid());
        }

        String content = jsonHelper.toJSON(product);
        po.setCode(product.getCode());
        po.setName(product.getName());
        po.setEffectiveDate(product.getEffectiveDate());
        po.setExpiredDate(product.getExpiredDate());
        po.setStatus(product.getStatus().name());
        po.setVersion(product.getVersion());
        po.setContent(content);

        productDao.save(po);

    }

    @Cacheable(value="productFormula" , key = "#productCode" + "-" + "#businessKey" + "-" + "#purpose")
    public List<FormulaSpec> findFormulas(String productCode, String businessKey, String purpose){

        List<FormulaSpec> result = Lists.newArrayList();
        ProductSpec productSpec = findProduct(productCode);

        List<FormulaSpec> formulaSpecs = productSpec.getSubComponentsByType(FormulaSpec.class);
        formulaSpecs.forEach(formulaSpec -> {
            if(purpose.equals(formulaSpec.getPurpose().name()) && productCode.equals(businessKey)){
                result.add(formulaSpec);
            }
        });

        List<CoverageSpec> coverageSpecs = productSpec.getAllSubComponentsByType(CoverageSpec.class);
        for(CoverageSpec coverageSpec : coverageSpecs){
            List<FormulaSpec> cformulaSpecs = coverageSpec.getSubComponentsByType(FormulaSpec.class);
            cformulaSpecs.forEach(formulaSpec -> {
                if(purpose.equals(formulaSpec.getPurpose().name()) && businessKey.equals(coverageSpec.getCode())){
                    result.add(formulaSpec);
                }
            });
        }

        return result;
    }

    @Cacheable(value="productRule" , key = "#productCode" + "-" + "#purpose")
    public List<RuleSpec> findRules(String productCode, String purpose){

        List<RuleSpec> result = Lists.newArrayList();
        ProductSpec productSpec = findProduct(productCode);

        List<RuleSpec> ruleSpecs = productSpec.getAllSubComponentsByType(RuleSpec.class);
        ruleSpecs.forEach(ruleSpec -> {
            if(purpose.equals(ruleSpec.getPurpose().name())){
                result.add(ruleSpec);
            }
        });

        return result;
    }

}
