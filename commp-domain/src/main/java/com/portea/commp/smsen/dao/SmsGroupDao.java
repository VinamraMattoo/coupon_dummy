package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsGroup;
import com.portea.dao.Dao;

import java.util.List;

public interface SmsGroupDao extends Dao<Integer, SmsGroup> {

    SmsGroup findByName(String name);

    List<SmsGroup> getSmsGroups();

    List<SmsGroup> getSmsGroupForNameMatch(String name, Integer limit);
}
