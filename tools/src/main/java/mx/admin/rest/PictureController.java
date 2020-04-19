package mx.admin.rest;

import mx.admin.aop.log.Log;
import mx.admin.domain.Picture;
import mx.admin.service.PictureService;
import mx.admin.service.query.PictureQueryService;
import mx.admin.utils.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PengXJ
 * @date 2018/09/20 14:13:32
 */
@RestController
@RequestMapping("/api")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PictureQueryService pictureQueryService;

    @Log("查询图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_SELECT')")
    @GetMapping(value = "/pictures")
    public ResponseEntity getRoles(Picture resources, Pageable pageable) {
        return new ResponseEntity(pictureQueryService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log("上传图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_UPLOAD')")
    @PostMapping(value = "/pictures")
    public ResponseEntity upload(@RequestParam MultipartFile file) {
        UserDetails userDetails = SecurityContextHolder.getUserDetails();
        String userName = userDetails.getUsername();
        Picture picture = pictureService.upload(file, userName);
        Map map = new HashMap();
        map.put("errno", 0);
        map.put("id", picture.getId());
        map.put("data", new String[]{picture.getUrl()});
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 删除图片
     *
     * @param id
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/pictures/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        pictureService.delete(pictureService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
