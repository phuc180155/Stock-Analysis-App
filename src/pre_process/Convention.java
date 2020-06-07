package pre_process;
/**
 * Chứa các từ quy ước dùng để mô hình câu
 * 
 * @author Ngốc_Học_OOP
 *
 */
public interface Convention {
	public static final String NAME_INDEX = "NameIndex";
	public static final String STATE = "State";
	public static final String PRICE = "Price";
	public static final String DAY = "Day";
	public static final String MATCHING_TRADE_WEIGHT = "MatchingTradeWeight";
	public static final String MATCHING_TRADE_VALUE = "MatchingTradeValue";
	public static final String TRANSACTION_WEIGHT = "TransactionWeight";
	public static final String TRANSACTION_VALUE = "TransactionValue";
	public static final String CHANGE = "Change";
	public static String[] setNameIndex = {"VN-INDEX","VN30-INDEX","HNX-INDEX","HNX30-INDEX","UPCOM-INDEX"};
}
