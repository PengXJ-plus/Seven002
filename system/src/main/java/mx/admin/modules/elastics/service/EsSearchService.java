package mx.admin.modules.elastics.service;

/**
 * EsSearchService
 *
 * @author PengXJ
 * @date 2020/3/30
 **/
public interface EsSearchService {

    /**
     * 从数据库导入所有日志到ES
     * @return 总条数
     */
    int importAll();
}
