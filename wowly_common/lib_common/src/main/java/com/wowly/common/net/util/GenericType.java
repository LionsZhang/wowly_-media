package com.wowly.common.net.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ClassName:   GenericType
 * Description: 反射获取泛型类型
 * <p>
 * Author:      lionszhang
 * Date:        2017/5/4 11:33
 */
public class GenericType<T> {

    private final Type type;

    protected GenericType() {
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
