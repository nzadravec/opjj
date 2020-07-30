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

/**
 * Servlet creates XLS file and stores bands voting results.
 * 
 * @author nikola
 *
 */
@WebServlet(name="glasanje-xls", urlPatterns={"/glasanje-xls"})
public class VotingXLSServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MusicalBand[] bands = VotingUtil.getMusicalBands(getServletContext());
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Rezultati");
		HSSFRow row = sheet.createRow((short) 0);
		row.createCell((short) 0).setCellValue("Bend");
		row.createCell((short) 1).setCellValue("Broj glasova");
		for(int i = 0; i < bands.length; i++) {
			row = sheet.createRow((short) (i+1));
			row.createCell((short) 0).setCellValue(bands[i].getBandName());
			row.createCell((short) 1).setCellValue(bands[i].getNumberOfVotes());
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}

}
