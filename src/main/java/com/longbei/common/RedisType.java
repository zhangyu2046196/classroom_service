package com.longbei.common;

/**
 * @author zhangy
 * @version 1.0
 * @description  Redis类型
 * @date 2020/3/11 13:29
 */
public enum RedisType {
    SAVE(1),
    DELETE(2);

    private int type;

    RedisType(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
