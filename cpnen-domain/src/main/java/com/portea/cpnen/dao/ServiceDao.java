package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Service;
import com.portea.dao.Dao;

import java.util.List;

public interface ServiceDao extends Dao<Integer, Service> {

    List<Service> getServices(List<Integer> ids);
}
