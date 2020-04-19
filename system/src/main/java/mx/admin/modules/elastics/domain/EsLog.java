package mx.admin.modules.elastics.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * EsLog
 *
 * @author PengXJ
 * @version 2.0
 * @date 2020/3/30 14:13
 **/
@Data
@Document(indexName = "ess", type = "search", shards = 1, replicas = 0)
public class EsLog implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    private Date createTime;

    private String description;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String exceptionDetail;
    @Field(type = FieldType.Keyword)
    private String logType;

    private String method;
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String params;
    @Field(type = FieldType.Keyword)
    private String requestIp;

    private Long time;

    private String username;

}
