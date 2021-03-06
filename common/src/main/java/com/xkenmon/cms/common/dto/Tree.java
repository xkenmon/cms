package com.xkenmon.cms.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
public class Tree<T> implements Serializable {

    private T data;

    private List<Tree<T>> child;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Tree<T>> getChild() {
        return child;
    }

    public void setChild(List<Tree<T>> child) {
        this.child = child;
    }
}
