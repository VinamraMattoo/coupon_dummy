package com.portea.cpnen.dao;

import com.portea.cpnen.domain.Category;
import com.portea.dao.Dao;

public interface CategoryDao extends Dao<Integer, Category> {

    int getCountOfCategories();

    Integer getParentCategoryId(int categoryId);

}
