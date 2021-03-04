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
 * 2nd time to get the at & rt by rt
 */

@Controller
public class GetNewToken {
	@RequestMapping(value = "/getNewToken")
	public ModelAndView grap(@RequestParam(name = "refreshToken", required = true) String refreshToken) {
		AppInfo app = AppInfo.getInstance();
		String appId = app.getAppId();
		String appPwd = app.getAppPwd();
		String uri = app.getRedirectUri();
		
		System.out.println("go to get getNewToken");
		String newAccessToken = null;
		String newRefreshToken = null; 
		
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
		
		HttpPost post = new HttpPost("https://login.microsoftonline.com/common/oauth2/v2.0/token");
		post.setConfig(Brower.getRequestConfig());
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		//use refresh token to get new token
		String json = "client_id="+appId+"&redirect_uri="+uri+"&client_secret="+appPwd+"&scope=Files.ReadWrite.All%20offline_access&grant_type=refresh_token&refresh_token="+refreshToken;
		
		System.out.println("Json str:"+json);
		post.setEntity(new StringEntity(json, ContentType.APPLICATION_FORM_URLENCODED));

		try {
			CloseableHttpResponse cl = httpclient.execute(post,httpClientContext);
			
			if(cl.getStatusLine().getStatusCode()==200) {
				JSONObject jo = JSON.parseObject(EntityUtils.toString(cl.getEntity()));
				
				newAccessToken = jo.getString("access_token");
				newRefreshToken = jo.getString("refresh_token");
				
				System.out.println("New Access Token:"+newAccessToken);
				System.out.println("New refresh_token:"+newRefreshToken);
				
			}
			else {
				System.out.println("failed:" + EntityUtils.toString(cl.getEntity()));
			}
			
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ModelAndView index = new ModelAndView("api");
		index.addObject("accessToken", newAccessToken);
		index.addObject("refreshToken", newRefreshToken);
		
		return index;
	}
}
