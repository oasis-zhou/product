package rf.product.ds;

import rf.product.model.FactorSpec;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public interface FactorService {

    public FactorSpec findFactor(String code);

    public void saveFactor(FactorSpec spec);
}
