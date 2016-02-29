package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsType;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsTypeDao extends Dao<Integer, SmsType> {

    List<SmsType> getSmsTypes();

    SmsType find(String name);
}
