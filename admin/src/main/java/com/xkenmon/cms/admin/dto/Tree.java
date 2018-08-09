package com.xkenmon.cms.admin.dto;

import java.util.List;

/**
 * @author bigmeng
 * @date 2018/8/9
 */
public class Tree<T> {
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
