package hqr.ms.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hqr.ms.domian.AppInfo;

@Controller
public class SaveClientInfo {
	
	@RequestMapping(value = "/save")
	public ModelAndView saveInfo(@RequestParam(value="clientId", required = true) String clientId, @RequestParam(value="clientPwd", required = true) String clientPwd, @RequestParam(value="uri", required = true) String uri) {
		ModelAndView view = new ModelAndView("redirect");

		AppInfo app = AppInfo.getInstance();
		app.setAppId(clientId);
		app.setAppPwd(clientPwd);
		app.setRedirectUri(uri);
		
		view.addObject("app", app);
		
		return view;
	}
	
}
