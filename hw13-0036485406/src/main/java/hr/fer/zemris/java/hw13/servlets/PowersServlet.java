package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import static java.lang.Math.pow;

/**
 * Servlet accepts a three parameters a (integer from [-100,100]) b (integer
 * from [-100,100]) and n (where n>=1 and n<=5). If any parameter is invalid, it
 * forwards request to errorMessage.jsp. Servlet dynamically creates a Microsoft
 * Excel document with n pages. On page i there is table with two columns. The
 * first column contains integer numbers from a to b. The second column contains
 * i-th powers of these numbers.
 * 
 * @author nikola
 *
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int a;
		int b;
		int n;
		try {
			a = loadParameter(req, "a", -100, 100);
			b = loadParameter(req, "b", -100, 100);
			n = loadParameter(req, "n", 1, 5);
		} catch (IllegalArgumentException e) {
			req.setAttribute("errorMessage", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/errorMessage.jsp").forward(req, resp);
			return;
		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		int numberOfCells = b - a + 1;
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = workbook.createSheet("x^" + i);
			for (int j = 0; j < numberOfCells; j++) {
				HSSFRow row = sheet.createRow((short) j);
				int x = a + j;
				int xPowN = (int) pow(x, i);
				row.createCell((short) 0).setCellValue(x);
				row.createCell((short) 1).setCellValue(xPowN);
			}
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=powers.xls");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}

	private int loadParameter(HttpServletRequest req, String paramName, int lowerBound, int upperBound) {
		String paramString = req.getParameter(paramName);
		if (paramString == null) {
			String message = "Missing parameter " + paramName;
			throw new IllegalArgumentException(message);
		}

		int param = Integer.parseInt(paramString);
		if (param < lowerBound || param > upperBound) {
			String message = "Parameter " + paramName + " must be integer from [" + lowerBound + "," + upperBound + "]";
			throw new IllegalArgumentException(message);
		}

		return param;
	}

}
