package hqr.ms.ctrl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hqr.ms.domian.AppInfo;
import hqr.ms.util.Brower;

@RestController
public class Search {
	
	@RequestMapping(value = "/search")
	public String search(@RequestParam(name="keyword", required = false) String keyword) {
		AppInfo app = AppInfo.getInstance();
		
		//open browser
		CloseableHttpClient httpclient = Brower.getCloseableHttpClient();
		//html context
		HttpClientContext httpClientContext = Brower.getHttpClientContext();
		
		HttpGet get = new HttpGet("https://graph.microsoft.com/v1.0/me/drive/root/search(q='"+keyword+"')?$select=id,name,file");
		get.setConfig(Brower.getRequestConfig());
		get.setHeader("Authorization", app.getAccessToken());

		try {
			CloseableHttpResponse cl = httpclient.execute(get, httpClientContext);
			return EntityUtils.toString(cl.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
}
