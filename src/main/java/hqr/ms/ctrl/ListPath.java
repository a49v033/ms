package hqr.ms.ctrl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.text.UnicodeUtil;
import hqr.ms.util.Brower;

@RestController
public class ListPath {
	
	@RequestMapping(value = "/listPath")
	public String listPath(@RequestParam(name = "path", required = false) String path, @RequestParam(name="accessToken", required = true)  String accessToken) {
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
		
		String url = ""	;
		
		if(path==null||"".equals(path)) {
			url = "https://graph.microsoft.com/v1.0/me/drive/root/children?$select=id,name,size";
		}
		else {
			url = "https://graph.microsoft.com/v1.0/me/drive/root:/"+path+":/children?$select=id,name,size";
		}
		System.out.println("listPath URL is "+url);
		HttpGet get = new HttpGet(url);
		get.setConfig(Brower.getRequestConfig());
		get.setHeader("Authorization", accessToken);
		
		try(CloseableHttpResponse cl = httpclient.execute(get, httpClientContext);) {
			String res = EntityUtils.toString(cl.getEntity());
			httpclient.close();
			return UnicodeUtil.toString(res);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
