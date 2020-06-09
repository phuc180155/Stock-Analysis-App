package process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import input.*;
import pre_process.*;
/**
 * Chứa các luật để xuất câu dựa vào câu mô hình
 * @author Ngốc_Học_OOP
 *
 */
public class Export1 extends Sentences implements Export {
	private String process(String st, String s,String replace) {		// st: 1 từ đã được chia, s: keyword, replace: thông tin lấy từ 1 session
																		// Hàm process: Replace 1 st, lý do: vì st không chỉ là mỗi cái keyword, vd: State% => (data)%, chứ không chỉ mỗi dâta
		StringBuffer buf = new StringBuffer();
		if (st.indexOf(s)<0) {											
			return null;
		}
		else {
			int l = st.indexOf(s);										
			buf.append(st.substring(0,l));
			buf.append(replace);
			buf.append(st.substring(l+s.length()));
			return buf.toString();
		}
	}
	private void replaceState(List<String> list, int i, float thisState) {
		int index = i-2;
		if (!list.get(index).equals("tăng") && !list.get(index).equals("giảm"))	
			index = i-1;
		chooseState(list, index, thisState);
		if (index == i-2)
			list.remove(i-1);
	}
	private void chooseState(List<String> list, int index, float thisState) {
		if (thisState < 0) {
			if (thisState < Session.GIAMMANH)
				list.set(index, "giảm mạnh");
			else if (thisState > Session.GIAMNHE)
				list.set(index, "giảm nhẹ");
			else 
				list.set(index, "giảm");
		}
		else {
			if (thisState > Session.TANGMANH)
				list.set(index, "tăng mạnh");
			else if (thisState < Session.TANGNHE)
				list.set(index, "tăng nhẹ");
			else 
				list.set(index, "tăng");
		}
	}
	@Override
	public String replace(String st)  {
		//VD: Chốt phiên giao dịch ngày hôm nay (Day), NameIndex giảm Change điểm (tương đương State%) còn Price điểm
		//Replace: Chốt phiên giao dịch ngày hôm nay (21/05/2020), VN-INDEX tăng 9.82 điểm (tương đương 1.15%) lên 862.73 điểm
		Random rd = new Random();
		try {
			Session session = Information.getRow(setNameIndex[rd.nextInt(5)], 3+rd.nextInt(20));
			Float price = session.getPrice();		// Lấy giá
			Float change = session.getChange();		// Lấy giá thay đổi
			Float state =session.getState();		// Lấy trạng thái (%)
			// Tất cả các thông tin của phiên được string
			String[] repl = {session.getNameIndex(),session.getDay(),price.toString(),change.toString(),state.toString(),session.getMatchingTradeWeight(),session.getMatchingTradeValue(),session.getTransactionWeight(),session.getTransactionValue()};
			// Tất cả các key đã được mã hoá trong xâu st
			String[] conv = {NAME_INDEX,DAY,PRICE,CHANGE,STATE,MATCHING_TRADE_WEIGHT,MATCHING_TRADE_VALUE,TRANSACTION_WEIGHT,TRANSACTION_VALUE};
			StringBuffer buffer = new StringBuffer();
			List<String> list = new ArrayList<String>();
			for (String s:st.split(" ")) {
				list.add(s);					// Chia xâu đã được model => Các từ
			}
			for (int i=0;i<list.size();i++) {
				String str = list.get(i);			// Với mỗi từ đã được chia, tìm kiếm xem có các key nào tương ứng không
				for (int j=0;j<9;j++) {
					if (str.indexOf(conv[j])>=0) {
						if (j!=3 && j!=4 && j!=2) list.set(i, process(str, conv[j], repl[j]));			// Nếu không phải là PRICE, CHANGE, STATE => Replace luôn mà không cần thắc mắc
						else {
							float thisState = session.getState();
							if (thisState < 0) {													
								if (j==2) {																// Nếu đây là PRICE:
									list.set(i, process(str, conv[j], repl[j]));
									if (list.get(i-1).equals("lên")) {
										list.set(i-1, "xuống");											// replace: lên => xuống
									}
									if (list.get(i-1).equals("đạt")) {
										list.set(i-1, "còn");											// replace: đạt => còn.
									}
									
								}
								else {																	// Nếu là CHANGE hoặc STATE: => replace
									list.set(i,process(str, conv[j], repl[j].substring(1)));			
									replaceState(list, i, thisState);
								}
							}
							else {																	
								if (j==2) {
									list.set(i, process(str, conv[j], repl[j]));
									if (list.get(i-1).equals("xuống") || list.get(i-1).equals("về")) {
										list.set(i-1, "lên");
									}if (list.get(i-1).equals("còn")) {
										list.set(i-1, "đạt");
									}
								}
								else {
									list.set(i,process(str, conv[j], repl[j]));			
									replaceState(list, i, thisState);
								}
							}
						}
					}
					
				}
				
			}
			for (String s:list) {
				buffer.append(s);
				buffer.append(' ');
			}
			return buffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public void export1() {
		// TODO Auto-generated method stub
		Modeling model = new Model1();
		try {
			List<String> list = Information.getList(new File("D:\\tang.txt"));
			for (String st:list) {
				listSentences.add(replace(model.modeling(st)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Export1 exp = new Export1();
		exp.export1();
		for (String st:Sentences.listSentences) {
			System.out.println(st);
		}
	}
}
