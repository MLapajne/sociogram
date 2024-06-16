package si.zitnik.sociogram.config;

import java.util.ArrayList;

public class PropertiesQuestions extends ArrayList<String> {
	private static final long serialVersionUID = -2164624828324332598L;

	public PropertiesQuestions(String[] split) {
		for (String question : split) {
			this.add(question);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String question : this) {
			sb.append(question);
			sb.append(";");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
