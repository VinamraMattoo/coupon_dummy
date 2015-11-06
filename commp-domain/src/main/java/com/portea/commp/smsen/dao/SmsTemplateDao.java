package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsTemplate;
import com.portea.dao.Dao;

public interface SmsTemplateDao extends Dao<Integer, SmsTemplate> {

    SmsTemplate findByName(String name);

}
