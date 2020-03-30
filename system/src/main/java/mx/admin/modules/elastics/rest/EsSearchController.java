package mx.admin.modules.elastics.rest;

import io.swagger.annotations.Api;
import mx.admin.modules.elastics.service.EsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EsSearchController
 *
 * @author PengXJ
 * @date 2020-3-30
 **/
@RestController
@RequestMapping("/es")
@Api(value = "Es搜索管理",tags = "EsController")
public class EsSearchController {


    @Autowired
    private EsSearchService esSearchService;
    @GetMapping("/importEsAll")
    public ResponseEntity importEsAll(){
        return new ResponseEntity(esSearchService.importAll(), HttpStatus.OK);
    }

}
