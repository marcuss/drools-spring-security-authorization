package co.m4solutions.drools.authorization.model;


public class CerberoBusinessInfoHolder {
	
	private static ThreadLocal<RequestInfoHolder> tlData = new ThreadLocal<RequestInfoHolder>();
	
	public static void setData(RequestInfoHolder cbo){
		 tlData.set(cbo);
	}
	
	public static RequestInfoHolder getData(){
		return tlData.get();
	}

}
