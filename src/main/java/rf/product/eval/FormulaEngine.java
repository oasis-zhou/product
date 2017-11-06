package rf.product.eval;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.product.ds.ProductService;
import rf.product.model.FormulaSpec;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouzheng on 2017/9/13.
 */
@Service
public class FormulaEngine {

    @Autowired
    private ProductService productService;

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    private static Logger logger = LoggerFactory.getLogger(FormulaEngine.class);

    public Map<String,Object> eval(EvalNode node){

        Map<String,Object> values = Maps.newHashMap();

        String purpose = node.getPurpose();

        List<FormulaSpec> formulas = productService.findFormulas(node.getProductCode(),node.getBusinessKey(),node.getPurpose());
        for(FormulaSpec formula : formulas) {
            if(purpose.equals(formula.getPurpose())) {
                Map r = evalWithFormula(formula, node.getFactors());
                values.putAll(r);
            }
        }

        if(values.size() > 0)
            logger.debug("*****Formula Result==" + values + "\n");

        return values;
    }

    private Map evalWithFormula(FormulaSpec formula,Map<String,Object> factors){

        Map result = null;
        try {
            logger.debug("*****Formula All Factors==" + factors + "\n");

            Binding binding = new Binding();
            List<String> factorKeys = formula.getFactors();

            for(String key : factorKeys){
                binding.setVariable(key, factors.get(key));
            }

            binding.setVariable("language", "Groovy");

            String cacheKey = "script" +  Math.abs(formula.getBody().hashCode());

            Script script = null;
            if (scriptCache.containsKey(cacheKey)) {
                script = (Script) scriptCache.get(cacheKey);
            } else {
                script = new GroovyShell().parse(preprocessScript(formula.getBody()));
                scriptCache.put(cacheKey,script);
            }

            Object value = (Object) InvokerHelper.createScript(script.getClass(), binding).run();

            result = (Map)value;

            result = RoundingUtils.round(result, null);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }

    private String preprocessScript(String script){

        String rateTableScript = GroovyScript.rateTableScript();

        return rateTableScript + script;
    }
}
