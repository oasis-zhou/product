package rf.product.dt.repository.pojo;


import rf.product.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "T_DECISION_TABLE")
public class TDecisionTable extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private Date effectiveDate;
    @Column
    private Date expiredDate;
    @Column
    private String status;
    @Lob
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
