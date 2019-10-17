package com.linwl.springcloudalibabademo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:06
 * @description ：
 * @modified By：
 */
@Slf4j
public abstract class BaseListener<T extends BaseModule> extends AnalysisEventListener<T> {
    /**
     * 3000条开始插入数据库
     */
    protected static final int BATCH_COUNT = 3000;

    /**
     * 数据容器
     */
    protected ThreadLocal<List<T>> dataContainer = new ThreadLocal<>();


    @Override
    public void invoke(T data, AnalysisContext context) {
        List<T> datas = getDataContainer();
        datas.add(data);
        //更新数据容器
        dataContainer.set(datas);
        if (datas.size() >= BATCH_COUNT) {
            handleData();
        }
    }

    /**
     * 获取数据容器
     */
    protected List<T> getDataContainer() {
        List<T> datas = dataContainer.get();
        if (datas == null) {
            datas = new ArrayList<>();
            dataContainer.set(datas);
        }
        return datas;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        handleData();
        log.info("所有数据信息已解析完毕！");
    }

    /**
     * 处理数据
     */
    private void handleData() {
        List<T> datas = getDataContainer();
        try {
            saveData(datas);
        } finally {
            dataContainer.remove();
        }
    }

    /**
     * 执行保存抽象方法数据
     *
     * @param datas
     */
    protected abstract void saveData(List<T> datas);
}
