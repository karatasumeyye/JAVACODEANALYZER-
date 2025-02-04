/**
*
* @author Sümeyye Karataş && karatasumeyye35@gmail.com
* @since 06.04.2024
* <p>
* AnalysisCalculations sınıfında program için gerekli analizler gerçekleyen metotlar vardır.
* </p>
*/

package projectAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

public class AnalysisCalculations {

	private File javaFile;

	public AnalysisCalculations(File javaFile) {
		this.javaFile = javaFile;
	}

	// Satır sayısı hesaplanır
	private int countLines() throws FileNotFoundException {
		int lines = 0;
		Scanner scanner = new Scanner(javaFile);
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			lines++;
		}
		scanner.close();
		return lines;

	}

	// Javadoc yorum satırı sayısı hesaplanır
	private int countJavadocLines() throws FileNotFoundException {
		int javadocLines = 0;
		Scanner scanner = new Scanner(javaFile);
		boolean inJavadoc = false; // javadoc yorum bloğu içinde mi kontrolünü sağlar
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.startsWith("/**")) {
				inJavadoc = true;
			} else if (line.startsWith("*") && inJavadoc && !line.startsWith("*/")) {
				javadocLines++;
			} else if (line.startsWith("*/")) {
				inJavadoc = false;
			}
		}
		scanner.close();
		return javadocLines;

	}

	// Diğer yorumların satır sayısını hesaplar
	private int countOtherCommentLines() throws FileNotFoundException {
		int otherCommentLines = 0;
		Scanner scanner = new Scanner(javaFile);

		boolean inComment = false; // yorum bloğu içinde mi kontrolü sağlar

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.startsWith("//")) {
				otherCommentLines++;
			} else if (line.startsWith("/*") && !line.startsWith("/**")) {
				inComment = true;
			} else if (line.startsWith("*/")) {
				inComment = false;
			} else if (line.startsWith("*") && inComment) {
				otherCommentLines++;
			} else if (line.contains("//") || (line.contains("/*") && line.contains("*/"))) { // yorumların satır
																								// başında olmama durumu
																								// ve /* */ yorum
																								// bloğunun tek satırda
																								// olma durumu ele
																								// alınır.
				otherCommentLines++;
			}
		}
		scanner.close();
		return otherCommentLines;

	}

	// Kod satır sayısı hesaplanır
	private int countCodeLines() throws FileNotFoundException {
		int codeLines = 0;
		Scanner scanner = new Scanner(javaFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*") && !line.startsWith("*")) {
				codeLines++;
			}
		}
		scanner.close();
		return codeLines;
	}

	// LOC-Her şey dahil sayır sayısı hesaplanır
	private int countLOC() throws FileNotFoundException {
		return countLines();
	}

	// Satırda generic tip olup olmadığı kontrol edilir,fonksiton sayısı hesaplamada
	// kullanılır
	private boolean hasGenericTypes() throws FileNotFoundException {
		boolean hasGenericTypes = false;
		Scanner scanner = new Scanner(javaFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (line.contains("<") && line.contains(">"))
				;
		}
		scanner.close();
		return hasGenericTypes;
	}

	// Sınıfın içindeki fonksiyon sayısı hesaplanır,keywordler ve dönüş tipleri
	// dikkate alınmıştır, ek olarak "(" var mı diye kontrol edilir.
	private int countFunctionCount() throws FileNotFoundException {
		int functionCount = 0;
		Scanner scanner = new Scanner(javaFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if ((line.startsWith("public") || line.startsWith("private") || line.startsWith("protected")
					|| line.startsWith("static") || line.startsWith("abstract") || line.startsWith("final")
					|| line.startsWith("synchronized") || line.startsWith("int") || line.startsWith("float")
					|| line.startsWith("double") || line.startsWith("String") || line.startsWith("void")
					|| line.startsWith("boolean") || line.startsWith("char") || hasGenericTypes())
					&& line.contains("(")) {
				functionCount++;
			}
		}
		scanner.close();
		return functionCount;
	}

	// Yorum yapma yüzde hesabı
	private double calculateCommentDeviation() throws FileNotFoundException {
		int javadocLines = countJavadocLines();
		int otherCommentLines = countOtherCommentLines();
		int functionCount = countFunctionCount();
		int codeLines = countCodeLines();
		float yg = (float) (((javadocLines + otherCommentLines) * 0.8) / functionCount);
		float yh = (float) ((float) codeLines / functionCount * 0.3);
		double commentDeviation = (((100 * yg) / yh) - 100);

		DecimalFormat df = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US)); // virgülden sonraki iki
																							// basamak ve virgül yerine
																							// nokta kullanarak formatla

		df.setRoundingMode(RoundingMode.HALF_UP); // Yarım yukarı yuvarlama kuralı

		String formattedCommentDeviation = df.format(commentDeviation);
		double roundedCommentDeviation = Double.parseDouble(formattedCommentDeviation); // Dönüşüm
		return roundedCommentDeviation;
	}

	// Hesaplanan değerler yazdırılır
	public void performAnalysis() {
		try {
			System.out.println("Sınıf: " + javaFile.getName());
			System.out.println("Javadoc Satır Sayısı: " + countJavadocLines());
			System.out.println("Yorum Satır Sayısı: " + countOtherCommentLines());
			System.out.println("Kod Satır Sayısı: " + countCodeLines());
			System.out.println("LOC: " + countLOC());
			System.out.println("Fonksiyon Sayısı: " + countFunctionCount());
			System.out.println("Yorum Sapma Yüzdesi: %" + calculateCommentDeviation());
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
