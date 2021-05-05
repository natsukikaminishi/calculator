
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * 電卓クラス
 */
public class Calculator {

	// 正規表現-数字
	public static final Pattern PTN_NM = Pattern.compile("[0-9]");
	// 正規表現-演算子
	public static final Pattern PTN_OP = Pattern.compile("\\+|\\-|\\*|\\/");
	// 正規表現-半角スペース
	public static final Pattern PTN_SP = Pattern.compile("\s");
	// 正規表現-数字半角スペース数字
	public static final Pattern PTN_NMSPNM = Pattern.compile("[0-9]\s[0-9]");
	// 正規表現-演算子半角スペース演算子
	public static final Pattern PTN_OPSPOP = Pattern.compile("(\\+|\\-|\\*|\\/)\s(\\+|\\-|\\*|\\/)");
	// コード-数字
	public static final String CD_NM = "1";
	// コード-演算子
	public static final String CD_OP = "2";
	// コード-半角スペース
	public static final String CD_SP = "3";

	public static void main(String[] args) {

		// 入力値
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		scanner.close();

		// 入力値チェック
		try {
			if (!check(input)) {
				System.out.println(calc(input));
			} else {
				System.out.println("入力フォーマットエラー");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("計算エラー");
		}
	}

	private static boolean check(String input) {
		String target = "";
		String target3 = "";
		String isOneBefore = "";
		/** 制御に使うフラグ */
		for (int i = 0; i < input.length(); i++) {
			target = input.substring(i, i + 1);
			if (PTN_NM.matcher(target).find()) {
				if (isOneBefore.equals(CD_OP)) {
					return true;
				}
				isOneBefore = CD_NM;
			} else if (PTN_OP.matcher(target).find()) {
				if (!isOneBefore.equals(CD_SP)) {
					return true;
				}
				isOneBefore = CD_OP;
			} else if (PTN_SP.matcher(target).find()) {
				if (isOneBefore.equals(CD_SP)) {
					return true;
				}
				if (i >= 3) {
					target3 = input.substring(i - 3, i);
					if (PTN_NMSPNM.matcher(target3).find() || PTN_OPSPOP.matcher(target3).find()) {
						// 数字半角スペース数字,演算子半角スペース演算子エラー
						return true;
					}
				}
				isOneBefore = CD_SP;
			} else {
				// 数値、演算子、半角スペース以外はエラー
				return true;
			}
		}
		return false;
	}

	private static double calc(String input) {
		// 結果
		double result = 0;
		// 計算用の数字（100を処理する時、"1"+"0"+"0"を数値変換する。）
		String tempval = "";
		// 計算用の数字（100を処理する時、"1"+"0"+"0"を数値変換する。）
		double val = 0;
		// 処理中文字
		String target = "";
		// 演算子（1+2を処理する時、2の時点で足すので演算子を退避させる。）
		String op = "";
		// 1つ前の文字
		String isOneBefore = "";
		// 初めての半角スペースの場合1
		int spCnt = 0;
		/** 制御に使うフラグ */
		for (int i = 0; i < input.length(); i++) {
			target = input.substring(i, i + 1);
			if (PTN_NM.matcher(target).find()) {
				tempval += target;
				if(i != input.length()-1) {
					
				}
				isOneBefore = CD_NM;
			} else if (PTN_OP.matcher(target).find()) {
				if (op.equals("+")) {
					op = "+";
				} else if (op.equals("-")) {
					op = "-";
				} else if (op.equals("*")) {
					op = "*";
				} else if (op.equals("/")) {
					op = "/";
				}
				isOneBefore = CD_OP;
			} else if (PTN_SP.matcher(target).find()) {
				spCnt++;
				if (!tempval.equals("")) {
					val = Integer.parseInt(tempval);
				}
				if (isOneBefore.equals(CD_NM)) {
					if (op.equals("+") || spCnt == 1) {
						result += val;
					} else if (op.equals("-")) {
						result -= val;
					} else if (op.equals("*")) {
						result *= val;
					} else if (op.equals("/")) {
						result /= val;
					}
					// 計算が終われば初期化する。
					tempval = "";
				}
				isOneBefore = CD_SP;
			}
		}
		return result;
	}
}
