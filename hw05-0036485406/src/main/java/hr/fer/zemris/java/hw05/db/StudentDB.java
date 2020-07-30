package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class StudentDB {
	
	public static void main(String[] args) {

		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StudentDatabase db = new StudentDatabase(lines);

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("> ");
			String queryString = sc.nextLine();

			if (queryString.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			if(queryString.startsWith("query")) {
				queryString = queryString.substring("query".length());
			} else {
				System.out.println("Fali ključna riječ 'query'");
			}

			QueryParser parser;
			try {
				parser = new QueryParser(queryString);
			} catch (ParserException e) {
				System.out.println(e.getMessage());
				continue;
			}

			if (parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
				StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
				if(r != null) {
					System.out.print("+" + equalsOf(10 + 2) + "+" + equalsOf(r.getLastName().length() + 2) + "+"
							+ equalsOf(r.getLastName().length() + 2) + "+");
					System.out.println();
					System.out.print("| " + r.getJmbag() + " | " + r.getLastName() + " | " + r.getFinalGrade() + " | "
							+ r.getFinalGrade() + " |");
					System.out.println();
					System.out.print("+" + equalsOf(10 + 2) + "+" + equalsOf(r.getLastName().length() + 2) + "+"
							+ equalsOf(r.getLastName().length() + 2) + "+");
					System.out.println();
					System.out.println("Records selected: 1");
				} else {
					System.out.println("Records selected: 0");
				}

			} else {
				List<StudentRecord> rs = db.filter(new QueryFilter(parser.getQuery()));
				if(rs.size() > 0) {
					int maxLengthLastName = 0;
					int maxLengthFirstName = 0;
					for (StudentRecord r : rs) {
						if (maxLengthLastName < r.getLastName().length()) {
							maxLengthLastName = r.getLastName().length();
						}
						if (maxLengthFirstName < r.getFirstName().length()) {
							maxLengthFirstName = r.getFirstName().length();
						}
					}
					
					System.out.print("+" + equalsOf(10 + 2) + "+" + equalsOf(maxLengthLastName + 2) + "+"
							+ equalsOf(maxLengthFirstName + 2) + "+" + equalsOf(1 + 2) + "+");
					System.out.println();
					for (StudentRecord r : rs) {
						System.out.print("| " + r.getJmbag() + " | " + padToSize(r.getLastName(), maxLengthLastName) + " | "
								+ padToSize(r.getFirstName(), maxLengthFirstName) + " | " + r.getFinalGrade() + " |");
						System.out.println();
					}
					System.out.print("+" + equalsOf(10 + 2) + "+" + equalsOf(maxLengthLastName + 2) + "+"
							+ equalsOf(maxLengthFirstName + 2) + "+" + equalsOf(1 + 2) + "+");
					System.out.println();
				}
				
				System.out.println("Records selected: " + rs.size());
			}
		}

		sc.close();
	}

	private static String equalsOf(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append("=");
		}

		return sb.toString();
	}

	private static String padToSize(String s, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		for (int i = s.length(); i < size; i++) {
			sb.append(" ");
		}

		return sb.toString();
	}

}
