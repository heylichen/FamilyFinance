package lab.core.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CardHelper {

	private String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3",
			"7", "9", "10", "5", "8", "4", "2" };
	private String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5",
			"4", "3", "2" };

	public String getCheckCode(String card17) {
		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(card17.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		return strVerifyCode;
	}
	
	public String toFullCard(String card17){
		String strVerifyCode = getCheckCode(  card17);
		return StringUtils.join(card17,strVerifyCode);
	}
}
