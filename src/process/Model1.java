package process;
//import java.io.File;
import java.util.ArrayList;
//import java.util.HashSet;

//import input.*;
import java.util.List;
//import java.util.Random;
//import java.util.Set;


import pre_process.*;
/**
 * Chứa các luật để mô hình câu
 * @author Ngốc_Học_OOP
 *
 */
public class Model1 extends Sentences  implements Modeling{

	@Override
	public String modeling(String st) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		StringBuffer buffer = new StringBuffer();
		String[] arr = st.split(" ");
		for (String s:arr) {
			list.add(s);
		}
		StringBuffer myBuffer = new StringBuffer();
		for (int i = 0;i<list.size();i++) {
			String s = list.get(i);
			if (s.indexOf("Vn-Index")>=0||s.indexOf("VN-Index")>=0) {
				list.set(i, NAME_INDEX);
			}
			else if (s.indexOf('/')>=0) {
				int l = s.indexOf('/')-1;
				int r = l+2;
				while (Character.isDigit(s.charAt(l))==true) {
					l--;
					if (l==-1) break;
				}
				while (Character.isDigit(s.charAt(r))==true) {
					r++;
					if (r==s.length()) break;
				}
				StringBuffer buf = new StringBuffer();
				buf.append(s.substring(0,l+1));
				buf.append(DAY);
				buf.append(s.substring(r));
				list.set(i, buf.toString());
			}
			else if (s.indexOf('%')>=0) {
				int l = 0;
				while (Character.isDigit(s.charAt(l))==false) l++;
				int r = s.indexOf('%');
				StringBuffer buf = new StringBuffer();
				buf.append(s.substring(0, l));
				buf.append(STATE);
				buf.append(s.substring(r));
				list.set(i, buf.toString());
			}
			else if (Character.isDigit(s.charAt(0))==true && list.get(i+1).indexOf("điểm") >= 0) {
				String pre = list.get(i-1);
				if (pre.equals("tăng")||pre.equals("giảm")) {
					list.set(i, CHANGE);
				}
				else {
					myBuffer.append(list.get(i-2));
					myBuffer.append(pre);
					String str = myBuffer.toString();
					if (str.indexOf("tăngnhẹ") >=0 || str.indexOf("giảmnhẹ")>=0 || str.indexOf("tăngmạnh") >=0 || str.indexOf("giảmmạnh")>=0)
						list.set(i, CHANGE);
					else
						list.set(i, PRICE);
					myBuffer.delete(0, myBuffer.length()-1);
				}
			}
			
		}
		
		for (int i = 0; i<list.size() - 3; i++ ) {
			myBuffer.append(list.get(i+1));
			myBuffer.append(list.get(i+2));
			myBuffer.append(list.get(i+3));
			String str = myBuffer.toString();
			if (str.indexOf("triệuchứngkhoán")>=0 || str.indexOf("triệucổphiếu")>=0 || str.indexOf("triệuđơnvị") >= 0) {
				if (st.indexOf("khớp lệnh") >=0 )
					list.set(i, MATCHING_TRADE_WEIGHT);
				else
					list.set(i, TRANSACTION_WEIGHT);
				list.remove(i+1);
			}
			myBuffer.delete(0, myBuffer.length()-1);	
		}
		for (int i = 0; i<list.size()-2;i++) {
			myBuffer.append(list.get(i+1));
			myBuffer.append(list.get(i+2));
			String str = myBuffer.toString();
			if (str.indexOf("tỷđồng") >=0) {
				if (st.indexOf("khớp lệnh")>=0)
					list.set(i, MATCHING_TRADE_VALUE);
				else
					list.set(i, TRANSACTION_VALUE);
				list.remove(i+1);
			}
			myBuffer.delete(0, myBuffer.length());
		}
		for (String s:list) {
			buffer.append(s);
			buffer.append(' ');
		}
		return buffer.toString();
	}	
}
