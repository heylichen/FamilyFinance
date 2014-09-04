package lab.business.finance.common.config;

import javax.annotation.PostConstruct;

import lab.core.commons.resource.service.ResourceService;
import lab.core.commons.resource.vo.ResourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoader {
	@Autowired
	private ResourceConfig resourceConfig;
	@Autowired
	private ResourceService resourceService;
	
	@PostConstruct 
	public void init() { 
		// 加载代码字典
		resourceService.loadAllCodeDict();
		resourceService.loadAllCodeDictFull();
		 
	}
}
