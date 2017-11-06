package rf.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.product.repository.pojo.TPeril;

/**
 * Created by zhouzheng on 2017/9/13.
 */
public interface PerilDao extends CrudRepository<TPeril, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TPeril findByCode(@Param("code") String code);
}
