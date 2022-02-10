package es.fpdual.companyCrud.service.exception;

import java.util.Map;

public class ValidateException extends Exception {

	private static final long serialVersionUID = -466496469460153055L;
	
	private Map<Integer, String> errors;

	public ValidateException(Map<Integer, String> errors) {
		this.errors = errors;
	}

	public Map<Integer, String> getErrors() {
		return errors;
	}

}
