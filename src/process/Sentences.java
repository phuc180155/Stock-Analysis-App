package process;

import java.util.ArrayList;
import java.util.List;
/**
 * Chứa các câu kết quả
 * @author Ngốc_Học_OOP
 *
 */
public abstract class Sentences {
	/**
	 * list dùng chung, các câu sau khi xử lý sẽ được cho hết vào list này
	 */
	protected static List<String> listSentences = new ArrayList<>();
	/**
	 * <h1> Hàm thêm các câu trong list các câu output vào listSentences</h1>
	 * <p> Đầu vào: List các câu output
	 * @param list
	 */
	public void add(List<String> list) {
		for (String s:list) {
			listSentences.add(s);
		}
	}
	
	
}
