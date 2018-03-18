package com.tf.utils;
import com.tf.entity.TAdmin;
/**
 * Created by wugq on 2018/3/18.
 */
public class CurrentUser {
    private static ThreadLocal<TAdmin> local = new ThreadLocal<TAdmin>();

    public static void set(TAdmin session) {
        local.set(session);
    }

    public static TAdmin get() {
        return local.get();
    }

    public static void remove(){
        local.remove();
    }
}
