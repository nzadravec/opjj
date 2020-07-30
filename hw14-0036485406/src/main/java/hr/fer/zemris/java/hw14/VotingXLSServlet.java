package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll.PollOption;

/**
 * Servlet creates XLS file and stores list of {@link PollOption} with
 * <code>pollID</code> equal to given parameter sent through URL by key
 * <code>"pollID"</code>.
 * 
 * @author nikola
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class VotingXLSServlet extends HttpServlet {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long pollID;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (Exception e) {
			resp.sendError(400, "Bad request");
			return;
		}

		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Rezultati");
		HSSFRow row = sheet.createRow((short) 0);
		row.createCell((short) 0).setCellValue("Bend");
		row.createCell((short) 1).setCellValue("Broj glasova");
		for (int i = 0; i < options.size(); i++) {
			row = sheet.createRow((short) (i + 1));
			row.createCell((short) 0).setCellValue(options.get(i).getOptionTitle());
			row.createCell((short) 1).setCellValue(options.get(i).getVotesCount());
		}

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=rezultati.xls");
		workbook.write(resp.getOutputStream());
		workbook.close();
	}

}
