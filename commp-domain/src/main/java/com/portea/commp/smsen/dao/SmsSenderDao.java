package com.portea.commp.smsen.dao;

import com.portea.commp.smsen.domain.SmsSender;
import com.portea.dao.Dao;

import javax.persistence.NoResultException;
import java.util.List;

public interface SmsSenderDao extends Dao<Integer, SmsSender> {

    List<SmsSender> getSmsSenders();

    SmsSender find(String name) throws NoResultException;
}
