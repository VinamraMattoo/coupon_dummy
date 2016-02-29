package com.portea.cpnen.dao;


import com.portea.cpnen.domain.Area;
import com.portea.dao.Dao;

import java.util.List;

public interface AreaDao extends Dao<Integer, Area> {

    List<Area> getAreas();

}
