package dd_utils;

import dd_core.TestCore;

public class CommonUtil extends TestCore {

	public static Object[][] getData(String sheetName) {
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows-1][cols];
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum-2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}

	public static boolean isExcutable(String tcid) {
		// TODO Auto-generated method stub
		for (int rowNum = 2; rowNum <= excel.getRowCount("test_suite"); rowNum++) {
			if (excel.getCellData("test_suite", "TCID", rowNum).equals(tcid)) {
				if (excel.getCellData("test_suite", "Runmode", rowNum).equalsIgnoreCase("Y")) {
					return true;
				}
				return false;
			}

		}
		return false;
	}
}
