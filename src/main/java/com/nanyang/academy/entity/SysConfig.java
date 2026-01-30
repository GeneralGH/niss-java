package com.nanyang.academy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author pt
 * @date 2022-06-02
 */
@Data
@ApiModel(value="{设置}", description="设置")
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @TableId
    private Long configId;

    private String configName;

    private String configKey;

    private String configValue;

    private String configType;

}
