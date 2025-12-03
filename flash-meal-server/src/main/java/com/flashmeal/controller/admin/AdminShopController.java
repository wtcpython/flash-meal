package com.flashmeal.controller.admin;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashmeal.result.Result;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
public class AdminShopController {

    private final StringRedisTemplate stringRedisTemplate;

    public AdminShopController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PutMapping("/{status}")
    public Result<Object> setStatus(@PathVariable Integer status) {
        log.info("修改商户状态: {}", status);
        stringRedisTemplate.opsForValue().set("SHOP_STATUS", status.toString());
        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = Integer.valueOf(stringRedisTemplate.opsForValue().get("SHOP_STATUS"));
        return Result.success(status);
    }
}
