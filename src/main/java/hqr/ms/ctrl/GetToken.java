package hqr.ms.ctrl;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hqr.ms.domian.AppInfo;
import hqr.ms.util.Brower;

/**
 * @author vanyouseea
 * 1st time to get the at & rt
 */


@Controller
public class GetToken {
	
	@RequestMapping(value = "/getToken")
	public ModelAndView grap(@RequestParam(name = "code", required = true) String code) {
		AppInfo app = AppInfo.getInstance();
		String appId = app.getAppId();
		String appPwd = app.getAppPwd();
		String uri = app.getRedirectUri();
		
		String accessToken = null;
		String refreshToken = null; 
		
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
		
		HttpPost post = new HttpPost("https://login.microsoftonline.com/common/oauth2/v2.0/token");
		post.setConfig(Brower.getRequestConfig());
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		//1st time to init the refresh token
		String json = "client_id="+appId+"&redirect_uri="+uri+"&client_secret="+appPwd+"&scope=Files.ReadWrite.All%20offline_access&grant_type=authorization_code&code="+code;
		
		//use refresh token to get new token
		//json = "client_id="+appId+"&redirect_uri="+uri+"&client_secret="+appPwd+"&scope=Files.ReadWrite.All%20offline_access&grant_type=refresh_token&refresh_token="+app.getRefreshToken();
		System.out.println("Json str:"+json);
		post.setEntity(new StringEntity(json, ContentType.APPLICATION_FORM_URLENCODED));

		try {
			CloseableHttpResponse cl = httpclient.execute(post,httpClientContext);
			
			if(cl.getStatusLine().getStatusCode()==200) {
				JSONObject jo = JSON.parseObject(EntityUtils.toString(cl.getEntity()));
				
				accessToken = jo.getString("access_token");
				refreshToken = jo.getString("refresh_token");
				
				System.out.println("Access Token:"+accessToken);
				System.out.println("refresh_token:"+refreshToken);
				
			}
			else {
				System.out.println("failed:" + EntityUtils.toString(cl.getEntity()));
			}
			
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ModelAndView index = new ModelAndView("redirect");
		index.addObject("accessToken", accessToken);
		index.addObject("refreshToken", refreshToken);
		index.addObject("app", app);
		
		return index;
	}
	
}
