package hr.fer.zemris.java.hw06;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);
		System.out.println(broj);
		System.out.println();
		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(broj5);
		System.out.println();
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		odlikasi.stream().forEach(r -> System.out.println(r));
		System.out.println();
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		odlikasiSortirano.stream().forEach(r -> System.out.println(r));
		System.out.println();
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		nepolozeniJMBAGovi.stream().forEach(s -> System.out.println(s));
		System.out.println();
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		for(Map.Entry<Integer, List<StudentRecord>> e : mapaPoOcjenama.entrySet()) {
			System.out.println(e.getKey() + " : ");
			e.getValue().stream().forEach(r -> System.out.println("\t" + r));
		}
		System.out.println();
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		for(Map.Entry<Integer, Integer> e : mapaPoOcjenama2.entrySet()) {
			System.out.println(e.getKey() + " : " + e.getValue());
		}
		System.out.println();
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		for(Map.Entry<Boolean, List<StudentRecord>> e : prolazNeprolaz.entrySet()) {
			System.out.println(e.getKey() + " : ");
			e.getValue().stream().forEach(r -> System.out.println("\t" + r));
		}
	}
	
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getUkupnoBodova() > 25).count();
	}
	
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getOcjena() == 5).count();
	}
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getOcjena() == 5).collect(Collectors.toList());
	}
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.sorted((r1, r2) -> Double.compare(r2.getUkupnoBodova(), r1.getUkupnoBodova()))
				.collect(Collectors.toList());
	}
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(r -> r.getOcjena() == 1)
				.sorted((r1, r2) -> Double.compare(r1.getUkupnoBodova(), r2.getUkupnoBodova())).map(r -> r.getJmbag())
				.collect(Collectors.toList());
	}
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(r -> r.getOcjena(), Collectors.toList()));
	}
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(r -> r.getOcjena(), r -> 1, (v1, v2) -> v1 + v2));
	}
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(r -> r.getOcjena() > 1));
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String line : lines) {
			line = line.trim();
			if (line.length() == 0) {
				continue;
			}
			String[] ss = line.split("\\s+");
			if (ss.length != 7) {
				throw new IllegalArgumentException();
			}

			try {
				String jmbag = ss[0];
				String prezime = ss[1];
				String ime = ss[2];
				double bodoviMI = Double.parseDouble(ss[3]);
				double bodoviZI = Double.parseDouble(ss[4]);
				double bodoviLV = Double.parseDouble(ss[5]);
				int ocjena = Integer.parseInt(ss[6]);

				records.add(new StudentRecord(jmbag, prezime, ime, bodoviMI, bodoviZI, bodoviLV, ocjena));
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}
		}

		return records;
	}

}
