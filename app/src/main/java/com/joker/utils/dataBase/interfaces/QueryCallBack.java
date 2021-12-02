package com.joker.utils.dataBase.interfaces;

import java.util.List;

/**
 * Created by cui.yan on 2018/7/11.
 */
public interface QueryCallBack<T> {
    void onSuccess(List<T> list);
    void onFail();
}
