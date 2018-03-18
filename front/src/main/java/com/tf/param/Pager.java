package com.tf.param;

import java.util.List;

/**
 * 分页信息
 *
 * @param <T>
 */
public class Pager<T> {

    private static final int PAGE_SIZE = 20;

    public List<T> rows;

    public int total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        if (this.total % PAGE_SIZE == 0) {
            return this.total / PAGE_SIZE;
        } else {
            return this.total / PAGE_SIZE + 1;
        }
    }
}
