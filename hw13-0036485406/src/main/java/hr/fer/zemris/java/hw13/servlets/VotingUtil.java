package hr.fer.zemris.java.hw13.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * Class contains single static method - getMusicalBands.
 * 
 * @author nikola
 *
 */
public class VotingUtil {

	/**
	 * Loads list of {@link MusicalBand} objects for all available bands.
	 * 
	 * @param context
	 *            application context
	 * @return list of bands
	 * @throws IOException
	 */
	public static MusicalBand[] getMusicalBands(ServletContext context) throws IOException {
		String fileName = context.getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<Integer, MusicalBand> bandsIDtoBandsObj = new HashMap<>();
		final int numberOfBands = lines.size();
		for (int i = 0; i < numberOfBands; i++) {
			String[] bandAttributes = lines.get(i).split("\t");
			int ID = Integer.parseInt(bandAttributes[0]);
			String name = bandAttributes[1];
			String linkToSong = bandAttributes[2];
			MusicalBand band = new MusicalBand(ID, name, linkToSong);
			bandsIDtoBandsObj.put(ID, band);
		}

		fileName = context.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path filePath = Paths.get(fileName);
		lines = Files.readAllLines(filePath);
		if (lines.isEmpty()) {
			String fileName2 = context.getRealPath("/WEB-INF/glasanje-definicija.txt");
			lines = Files.readAllLines(Paths.get(fileName2));

			BufferedWriter writer = Files.newBufferedWriter(filePath);
			for (int i = 0; i < numberOfBands; i++) {
				int bandID = Integer.parseInt(lines.get(i).split("\t")[0]);
				int numberOfVotes = 0;
				bandsIDtoBandsObj.get(bandID).setNumberOfVotes(numberOfVotes);

				writer.write(bandID + "\t" + numberOfVotes);
				writer.newLine();
			}
			writer.close();

		} else {
			lines = Files.readAllLines(filePath);
			for (int i = 0; i < numberOfBands; i++) {
				String[] resultAttributes = lines.get(i).split("\t");
				int bandID = Integer.parseInt(resultAttributes[0]);
				int numberOfVotes = Integer.parseInt(resultAttributes[1]);
				bandsIDtoBandsObj.get(bandID).setNumberOfVotes(numberOfVotes);
			}
		}

		MusicalBand[] bands = bandsIDtoBandsObj.values().toArray(new MusicalBand[bandsIDtoBandsObj.size()]);
		Arrays.sort(bands, new Comparator<MusicalBand>() {
			@Override
			public int compare(MusicalBand band1, MusicalBand band2) {
				return Integer.compare(band2.getNumberOfVotes(), band1.getNumberOfVotes());
			}
		});

		return bands;
	}

}
