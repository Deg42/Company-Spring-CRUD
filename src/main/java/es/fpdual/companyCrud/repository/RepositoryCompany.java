package es.fpdual.companyCrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.fpdual.companyCrud.entities.Company;

// @Repository
public interface RepositoryCompany extends JpaRepository<Company, Long> {

	List<Company> findByNameContaining(String name);
}
