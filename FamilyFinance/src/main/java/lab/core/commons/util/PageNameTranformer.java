package lab.core.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PageNameTranformer {
	public String getPageName(String urlPiece){
		String[] arr = StringUtils.split(urlPiece, "-");
		List<String> list = new ArrayList<String>();
		for(String piece: arr){
			list.add (piece.substring(0,1).toUpperCase()+piece.substring(1)); 
		}
		String result = StringUtils.join(list, "");
		return result;
	}
}
