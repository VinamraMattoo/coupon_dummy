package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Package;
import com.portea.dao.Dao;

import java.util.List;

public interface PackageDao extends Dao<Integer, Package> {

    List<Package> getPackages(List<Integer> ids);
}
