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
import rf.product.model.RuleSpec;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouzheng on 2017/9/13.
 */
@Service
public class RuleEngine {

    @Autowired
    private ProductService productService;

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    private static Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    public Map<String,Object> eval(EvalNode node){

        Map<String,Object> values = Maps.newHashMap();

        List<RuleSpec> rules = productService.findRules(node.getProductCode(),node.getPurpose());
        for(RuleSpec rule : rules) {
            boolean r = evalWithRule(rule, node.getFactors());
            if(r){
                values.put(rule.getCode() + "_MSG",rule.getMessage());
            }
        }

        if(values.size() > 0)
            logger.debug("*****Formula Result==" + values + "\n");

        return values;
    }

    private Boolean evalWithRule(RuleSpec rule,Map<String,Object> factors){

        Boolean result = null;
        try {
            logger.debug("*****Rule All Factors==" + factors + "\n");

            Binding binding = new Binding();
            List<String> factorKeys = rule.getFactors();

            for(String key : factorKeys){
                binding.setVariable(key, factors.get(key));
            }

            binding.setVariable("language", "Groovy");

            String cacheKey = "script" +  Math.abs(rule.getBody().hashCode());

            Script script = null;
            if (scriptCache.containsKey(cacheKey)) {
                script = (Script) scriptCache.get(cacheKey);
            } else {
                script = new GroovyShell().parse(rule.getBody());
                scriptCache.put(cacheKey,script);
            }

            Object value = (Object) InvokerHelper.createScript(script.getClass(), binding).run();


            result = (Boolean)value;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }
}
