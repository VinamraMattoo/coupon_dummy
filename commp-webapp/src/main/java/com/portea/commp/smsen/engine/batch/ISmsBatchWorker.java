package com.portea.commp.smsen.engine.batch;

import com.portea.commp.smsen.vo.SmsInAssembly;

import java.util.List;

public interface ISmsBatchWorker extends Runnable {

    void setSmsInAssembly(List<SmsInAssembly> smsInAssemblyList);

    String getName();

    void setName(String name);
}
