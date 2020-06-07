package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p> Chứa các phương thức để đọc dữ liệu đầu vào </p>
 * <p> Bao gồm 2 hàm: 1 hàm đọc file dữ liệu và 1 hàm đọc file mẫu câu</p>
 * 
 * 
 * @author Ngốc_Học_OOP
 *
 */
public class Information {
	private static File file = new File("du_lieu_btl.xlsx");
	/**
	 * <h1> Hàm đọc dữ liệu từ file excel </h1>
	 * <p> Đầu vào: Tên của chỉ số và hàng trong sheet dữ liêu của chỉ số đó </p>
	 * <p> Đầu ra: Một đối tượng Session chứa các thông tin về phiên giao dịch </p>
	 * @param nameIndex
	 * @param rowIndex
	 * @return Session session
	 * @throws Exception
	 */
	public static Session getRow(String nameIndex,int rowIndex) throws Exception {
		Session session = new Session();
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheet(nameIndex);
		XSSFRow row = sheet.getRow(rowIndex);
		XSSFCell day = row.getCell(0);
		XSSFCell price = row.getCell(1);
		XSSFCell state = row.getCell(2);
		String str = state.getStringCellValue();
		int i = str.indexOf("(");
		int j = str.indexOf("%");
		session.setState(Float.parseFloat(str.substring(i+1, j)));
		session.setChange(Float.parseFloat(str.substring(0,i)));
		XSSFCell matchingTradeWeight = row.getCell(4);
		XSSFCell matchingTradeValue = row.getCell(5);
		XSSFCell transactionWeight = row.getCell(6);
		XSSFCell transactionValue = row.getCell(7);
		session.setDay(day.getStringCellValue());
		session.setPrice(Float.parseFloat(price.getStringCellValue()));
		session.setNameIndex(nameIndex);
		session.setMatchingTradeWeight(matchingTradeWeight.getStringCellValue());
		session.setMatchingTradeValue(matchingTradeValue.getStringCellValue());
		session.setTransactionWeight(transactionWeight.getStringCellValue());
		session.setTransactionValue(transactionValue.getStringCellValue());
		wb.close();
		return session;
	}
	/**
	 * <h1> Hàm đọc file mẫu câu đưa vào </h1>
	 * <p> Đầu vào: 1 file mẫu câu </p>
	 * <p> Đầu ra: 1 list chứa các câu trong file mẫu câu đó </p>
	 * 
	 * @param file
	 * @return List<String> list
	 * @throws Exception
	 */
	
	public static List<String> getList(File file) throws Exception{
		List<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		int flag = 1;
		String st ;
		while (flag==1) {
			st = reader.readLine();
			if (st==null) {
				flag=0;
			}
			else {
				if (Character.isDigit(st.charAt(0))) {
					int i = 0;
					while (Character.isUpperCase(st.charAt(i))==false) {
						i++;
					}
					list.add(st.substring(i));
				}
			}
		}
		reader.close();
		return list;
		
	}

	
	
	
}
