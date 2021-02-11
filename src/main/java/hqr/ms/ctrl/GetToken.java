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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import hqr.ms.util.Brower;

@Controller
public class GetToken {
	
	@RequestMapping(value = "/getToken")
	public ModelAndView grap(@RequestParam(value="code", required = true) String code,@RequestParam(value="uri", required = false, defaultValue = "https://vanyouseea.github.io/hlh/") String uri,@RequestParam(value="clientId", required = false, defaultValue = "9b354133-9271-4398-8797-c2c31ab6329f") String clientId) {
		System.out.println("code:"+code);
		System.out.println("uri:"+uri);
		System.out.println("clientId:"+clientId);
		
		String accessToken = "null";
		String refreshToken = "null";
		
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
		
		HttpPost post = new HttpPost("https://login.microsoftonline.com/common/oauth2/v2.0/token");
		post.setConfig(Brower.getRequestConfig());
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		String json = "client_id=9b354133-9271-4398-8797-c2c31ab6329f&redirect_uri="+uri+"&client_secret=7TS9.CiIRLJj~6__8G4wG3Sy1cpctyV.Aa&scope=Files.ReadWrite.All%20offline_access&grant_type=authorization_code&code="+code;
		
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
		
		
		ModelAndView index = new ModelAndView("index");
		index.addObject("access_token", accessToken);
		index.addObject("refresh_token", refreshToken);
		
		return index;
	}
	
}
