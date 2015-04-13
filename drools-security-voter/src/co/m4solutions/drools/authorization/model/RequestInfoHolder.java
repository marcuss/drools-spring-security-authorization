package co.m4solutions.drools.authorization.model;

import java.util.Map;

public class RequestInfoHolder {

	private String flowExecutedAction;

	private String flowName;

	private Map<String, Object> flowScopeVariables;

	private String flowViewName;

	private String message;

	private String personRole;

	private String requestUrl;

	private boolean resultado;

	public String getFlowExecutedAction() {
		return flowExecutedAction;
	}

	public void setFlowExecutedAction(String flowExecutedAction) {
		this.flowExecutedAction = flowExecutedAction;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Map<String, Object> getFlowScopeVariables() {
		return flowScopeVariables;
	}

	public void setFlowScopeVariables(Map<String, Object> flowScopeVariables) {
		this.flowScopeVariables = flowScopeVariables;
	}

	public String getFlowViewName() {
		return flowViewName;
	}

	public void setFlowViewName(String flowViewName) {
		this.flowViewName = flowViewName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPersonRole() {
		return personRole;
	}

	public void setPersonRole(String personRole) {
		this.personRole = personRole;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

}
