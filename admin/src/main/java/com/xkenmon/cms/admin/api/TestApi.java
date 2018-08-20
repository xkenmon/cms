package com.xkenmon.cms.admin.api;

import com.xkenmon.cms.admin.annotation.Auth;
import com.xkenmon.cms.admin.log.AdminLogRepository;
import com.xkenmon.cms.admin.service.IUserService;
import com.xkenmon.cms.admin.log.MongoAdminLog;
import com.xkenmon.cms.common.utils.SequenceGenerator;
import com.xkenmon.cms.dao.mapper.FileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ***此API仅做测试用***
 *
 * @author bigmeng
 * @date 2018/8/12
 */
@RestController
@RequestMapping("/test")
public class TestApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestApi.class);

    private final AdminLogRepository adminLogRepository;

    private final
    IUserService userService;

    private final FileMapper fileMapper;

    public TestApi(AdminLogRepository adminLogRepository, IUserService userService, FileMapper fileMapper) {
        this.adminLogRepository = adminLogRepository;
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @GetMapping("selectFileNameList")
    public List testSelectFileName() {
        return fileMapper.selectFileKeyByArticleId(163);
    }

    @GetMapping("authTest")
    @Auth(siteId = "#sid", modules = "test-api")
    public Integer authTest(@RequestParam Integer sid) {
        userService.queryPermission(1).forEach(p -> LOGGER.info(p.toString()));
        userService.queryModules(1, 56).forEach(LOGGER::info);
        userService.querySite(1).forEach(s -> LOGGER.info(s.toString()));
        return sid;
    }

    @GetMapping("mongo_test")
    public void mongoTest() {
        MongoAdminLog log = new MongoAdminLog();
        log.setMsg("wtfasfdsfasdf");
        log.setLevel("Info");
        log.setTimestamp(System.currentTimeMillis());
        log.setId(SequenceGenerator.nextId());
        adminLogRepository.insert(log);
    }
}
