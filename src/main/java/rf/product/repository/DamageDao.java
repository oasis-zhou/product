package rf.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.product.repository.pojo.TDamage;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public interface DamageDao extends CrudRepository<TDamage, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TDamage findByCode(@Param("code") String code);
}
