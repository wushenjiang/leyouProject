package com.leyou.order.enums;

/**
 * @ClassName: PayState
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  21:19
 */
public enum PayState {
    NOT_PAY(0),SUCCESS(1),FAIL(2);
    PayState(int value){
        this.value = value;
    }
    int value;
    public int getValue(){
        return value;
    }
}
