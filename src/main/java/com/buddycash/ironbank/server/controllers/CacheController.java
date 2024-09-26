package com.buddycash.ironbank.server.controllers;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {
  private final CacheManager cacheManager;

  @Autowired
  public CacheController(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  @GetMapping("/{cacheName}")
  private Map<Object, Object> listCache(@PathVariable("cacheName") String cacheName) {
    CaffeineCache caffeineCache = (CaffeineCache) cacheManager.getCache(cacheName);
    Cache<Object, Object> nativeCache = caffeineCache.getNativeCache();
    var objects = nativeCache.asMap();
    return objects;
  }
}
