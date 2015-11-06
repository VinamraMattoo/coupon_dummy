package com.portea.commp.smsen.dao;

import com.portea.dao.Dao;
import com.portea.commp.smsen.domain.SmsGroup;

public interface SmsGroupDao extends Dao<Integer, SmsGroup> {

    SmsGroup findByName(String name);

}
