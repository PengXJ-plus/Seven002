package mx.admin.modules.elastics.repository;

import mx.admin.modules.elastics.domain.EsLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * EsLogElasticsearchRepository
 *
 * @author PengXJ
 * @version 2.0
 * @date 2020/3/30 14:52
 **/
public interface EsLogElasticsearchRepository extends ElasticsearchRepository<EsLog,Long> {
}
