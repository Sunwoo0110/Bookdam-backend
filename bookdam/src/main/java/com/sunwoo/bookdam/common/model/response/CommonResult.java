package com.sunwoo.bookdam.common.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommonResult<T> extends CommonBaseResult {
    private T content;
}
