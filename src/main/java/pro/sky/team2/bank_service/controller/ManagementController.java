package pro.sky.team2.bank_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.team2.bank_service.dto.InfoDTO;

import java.util.Objects;

@RestController
@RequestMapping("/management")
public class ManagementController {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private BuildProperties buildProperties;

    @PostMapping("/clear-caches")
    public void clearCache() {
        cacheManager.getCacheNames().forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
    }

    @GetMapping("/info")
    public InfoDTO getBuildInfo() {
        InfoDTO buildInfo = new InfoDTO();
        buildInfo.setName(buildProperties.getName());
        buildInfo.setVersion(buildProperties.getVersion());
        return buildInfo;
    }
}
