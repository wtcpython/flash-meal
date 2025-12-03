package com.flashmeal.controller.user;

import com.flashmeal.constant.StatusConstant;
import com.flashmeal.entity.Dish;
import com.flashmeal.result.Result;
import com.flashmeal.service.DishService;
import com.flashmeal.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    private final RedisTemplate<String, Object> redisTemplate;
    private final DishService dishService;

    public DishController(RedisTemplate<String, Object> redisTemplate, DishService dishService) {
        this.redisTemplate = redisTemplate;
        this.dishService = dishService;
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        // 查询 Redis 中是否存在菜品数据
        String key = "dish_" + categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);

        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);// 查询起售中的菜品

        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
