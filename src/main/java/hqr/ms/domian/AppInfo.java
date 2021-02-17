package hqr.ms.domian;

public class AppInfo {
	private String appId;
	private String appPwd;
	private String redirectUri;
	
	private static AppInfo appInfo;
	public static AppInfo getInstance() {
		if(appInfo==null) {
			appInfo = new AppInfo();
		}
		return appInfo;
	}
	
	private AppInfo() {
		
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppPwd() {
		return appPwd;
	}
	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
}
