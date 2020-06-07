package pre_process;

/**
 * Chứa các phương thức để xuất câu đặc tả dữ liệu dựa vào câu đã được mô hình
 * 
 * @author Ngốc_Học_OOP
 *
 */
public interface Export extends Convention{
	
	/**
	 * <h1> Phương thức xuất câu đặc tả dũ liệu từ câu đã được mô hình </h1>
	 * <p> Đầu vào: Câu đã được mô hình hóa </p>
	 * <p> Đầu ra: Câu đặc tả dữ liệu </p>
	 * <p> Ví dụ: </p>
	 * <p> Đầu vào: Chốt phiên giao dịch ngày hôm nay (Day), NameIndex giảm Change điểm (tương đương State%) còn Price điểm.</p>
	 * <p> Đầu ra: Chốt phiên giao dịch ngày hôm nay (21/05/2020), VN-INDEX tăng 9.82 điểm (tương đương 1.15%) lên 862.73 điểm</p>
	 * @param st
	 * @return String kq
	 */
	public String replace(String st);
	/**
	 * <h1> Add các câu kết quả vào một list dùng chung </h1>
	 * <p> Đầu vào: File mẫu câu và đối tượng chứa luật dùng để mô hình câu </p>
	 * 
	 * @param 
	 * @return
	 */
	public void export1();
	
}

