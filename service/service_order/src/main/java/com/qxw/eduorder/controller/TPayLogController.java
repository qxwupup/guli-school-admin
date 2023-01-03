package com.qxw.eduorder.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxw.common.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.qxw.eduorder.entity.TPayLog;
import com.qxw.eduorder.service.TPayLogService;

import java.util.List;

/**
 * 支付日志表 控制器
 *
 * @author qxw
 * @since 2023-01-03
 */
@RestController
@RequestMapping("/")
public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService;


    /**
     * 详情
     */

    @GetMapping("/detail")
    public Result<?> detail(TPayLog tPayLog) {
        TPayLog detail = tPayLogService.getOne(new QueryWrapper<>(tPayLog));
        return Result.data(detail);
    }

    /**
     * 列表 支付日志表
     */

    @GetMapping("/list")
    public Result<?> list(TPayLog tPayLog) {
        List<TPayLog> list = tPayLogService.list(new QueryWrapper<>(tPayLog));
        return Result.data(list);
    }

    /**
     * 分页 支付日志表
     */

    @GetMapping("/page/{current}/{size}")
    public Result<?> page(@PathVariable Long current, @PathVariable Long size, @RequestBody TPayLog tPayLog) {
        Page<TPayLog> page = new Page<>(current, size);
        QueryWrapper<TPayLog> wrapper = new QueryWrapper<>();
        tPayLogService.page(page, wrapper);
        return Result.data(page);
    }


    /**
     * 新增 支付日志表
     */

    @PostMapping
    public Result<?> save(@RequestBody TPayLog tPayLog) {
        return Result.status(tPayLogService.save(tPayLog));
    }

    /**
     * 修改 支付日志表
     */
    @PutMapping
    public Result<?> update(@RequestBody TPayLog tPayLog) {
        return Result.status(tPayLogService.updateById(tPayLog));
    }

    /**
     * 新增或修改 支付日志表
     */

    @PostMapping("/submit")
    public Result<?> submit(@RequestBody TPayLog tPayLog) {
        return Result.status(tPayLogService.saveOrUpdate(tPayLog));
    }


    /**
     * 删除 支付日志表
     */
    @DeleteMapping
    public Result<?> remove(@RequestParam String ids) {
        return Result.status(tPayLogService.removeByIds(StrUtil.splitTrim(ids, StrUtil.C_COMMA)));
    }

}
