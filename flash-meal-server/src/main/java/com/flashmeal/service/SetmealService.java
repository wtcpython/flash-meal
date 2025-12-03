package com.flashmeal.service;

import java.util.List;

import com.flashmeal.dto.SetmealDTO;
import com.flashmeal.dto.SetmealPageQueryDTO;
import com.flashmeal.entity.Setmeal;
import com.flashmeal.result.PageResult;
import com.flashmeal.vo.DishItemVO;
import com.flashmeal.vo.SetmealVO;

public interface SetmealService {

    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * 
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * 
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询套餐和关联的菜品数据
     * 
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 修改套餐
     * 
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * 
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * 
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * 
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}