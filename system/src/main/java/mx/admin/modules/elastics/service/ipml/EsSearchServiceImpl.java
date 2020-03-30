package mx.admin.modules.elastics.service.ipml;

import com.google.common.collect.Lists;

import mx.admin.domain.Log;
import mx.admin.modules.elastics.domain.EsLog;
import mx.admin.modules.elastics.repository.EsLogElasticsearchRepository;
import mx.admin.modules.elastics.service.EsSearchService;
import mx.admin.repository.LogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EsSearchServiceImpl
 *
 * @author PengXJ
 * @date 2020/3/30
 **/
@Service
public class EsSearchServiceImpl implements EsSearchService {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private EsLogElasticsearchRepository esLogElasticsearchRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public int importAll() {
        List<Log> all = logRepository.findAll();
        ArrayList<EsLog> esLogs = Lists.newArrayList();
        all.forEach(x-> {
            EsLog esLog = new EsLog();
            BeanUtils.copyProperties(x,esLog);
            esLogs.add(esLog);
        });
        Iterable<EsLog> esLogs1 = esLogElasticsearchRepository.saveAll(esLogs);
        Iterator<EsLog> iterator = esLogs1.iterator();
        int result = 0 ;
        while (iterator.hasNext()){
            result++;
            iterator.next();
        }
        return result;
    }
}
