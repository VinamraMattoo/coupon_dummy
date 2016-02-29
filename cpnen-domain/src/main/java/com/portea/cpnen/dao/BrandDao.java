package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Brand;
import com.portea.dao.Dao;

import java.util.List;

public interface BrandDao extends Dao<Integer, Brand> {

    List<Brand> getBrands();
}
