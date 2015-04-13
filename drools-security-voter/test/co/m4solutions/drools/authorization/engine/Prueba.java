package co.m4solutions.drools.authorization.engine;

import java.util.HashMap;

import co.m4solutions.drools.authorization.model.RequestInfoHolder;

public class Prueba {
	/**
	 * 
	 * @param args
	 */
	static public void main(String[] args) {

		RequestInfoHolder obj = new RequestInfoHolder();
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("hotelId", 1);
		obj.setFlowScopeVariables(mapa);
		obj.setPersonRole("ROLE_USER");

		DroolsEngine.getInstance().validate(obj);
		System.err.println(obj.isResultado());
	}
}
