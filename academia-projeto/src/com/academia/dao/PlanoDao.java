package com.academia.dao;

import java.util.List;

import com.academia.entities.Plano;

public interface PlanoDao {

	List<Plano> findAll();

	Plano findById(int id);

	Plano insert(Plano plano);
}
