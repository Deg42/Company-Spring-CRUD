package es.fpdual.companyCrud.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.fpdual.companyCrud.entities.Company;
import es.fpdual.companyCrud.entities.Employee;
import es.fpdual.companyCrud.repository.RepositoryCompany;
import es.fpdual.companyCrud.service.ServiceCompany;
import es.fpdual.companyCrud.service.exception.ValidateException;

@Service
public class ServiceCompanyImpl implements ServiceCompany {

	RepositoryCompany companyRepository = null;

	@Autowired
	public ServiceCompanyImpl(RepositoryCompany companyRepository) {
		this.companyRepository = companyRepository;
	}

	@Override
	public void saveCompany(Company company, List<Employee> employees) throws ValidateException {
		Map<Integer, String> errors = this.validate(company);

		Company companyToSave = new Company();

		companyToSave.setName(company.getName());
		
		if(!employees.isEmpty()) {
			employees.forEach(e -> {
				companyToSave.addEmployee(e);
			});
		}
		
		if (!errors.isEmpty()) {
			throw new ValidateException(errors);
		}

		this.companyRepository.saveAndFlush(companyToSave);
	}

	@Override
	public List<Company> readAllCompanies() {
		return this.companyRepository.findAll();
	}

	@Override
	public Company readCompanyById(Long id) {
		return this.companyRepository.findById(id).orElse(null);
		
	}

	@Override
	public void updateCompany(Long id, Company newCompany) {
		Map<Integer, String> errors = this.validate(newCompany);
					
		Optional<Company> oldCompany = this.companyRepository.findById(id);

		if (oldCompany.isPresent() && errors.isEmpty()) {
			Company oldPresentCompany = oldCompany.get();
			oldPresentCompany.setName(newCompany.getName());
		
			this.companyRepository.saveAndFlush(oldPresentCompany);
		}

	}

	@Override
	public void deleteCompany(Long id) {
		this.companyRepository.deleteById(id);
	}

	@Override
	public void deleteAllCompanies() {
		this.companyRepository.deleteAll();
	}

	@Override
	public List<Company> readCompaniesByName(String name) {
		return this.companyRepository.findByNameContaining(name);
	}

	public Map<Integer, String> validate(Company company) {
		Map<Integer, String> result = new HashMap<Integer, String>();

		if (company.getName() == null || company.getName().isEmpty()) {
			result.put(1, "Company name is empty");
		}

		return result;
	}

}
