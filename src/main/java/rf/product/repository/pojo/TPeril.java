package rf.product.repository.pojo;

import com.google.common.collect.Lists;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by zhouzheng on 2017/9/13.
 */
@Entity
@Table(name = "T_PERIL")
public class TPeril {

    @Id
    private String uuid;
    private String code;
    private String name;
    @Lob
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
