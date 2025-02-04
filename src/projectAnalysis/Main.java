/**
*
* @author Sümeyye Karataş && karatasumeyye35@gmail.com
* @since 06.04.2024
* <p>
* Main sınıfında kullanıcıdan URL alınır ve repo klonlama, java dosyalarındaki sınıfları analiz etme metotlarının kullanılarak 
* istenilen analizler yapılır.
* </p>
*/

package projectAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("GitHup deposunun URL'sini girin: ");
		String repoURL=scanner.nextLine();
		scanner.close();
		
		GitHubProcess repoManager= new GitHubProcess(repoURL);
	
		  try {
	            // GitHub deposunu klonla
	            repoManager.cloneRepository();

	            // Klonlanmış depo dizininin yolu
	            String repoDirectoryPath = getRepoName(repoURL);

	            // .java dosyalarını getir
	            List<File> javaFiles = repoManager.getJavaFiles(repoDirectoryPath);

	            for (File javaFile : javaFiles) {
	            	
	               AnalysisCalculations analysis = new AnalysisCalculations(javaFile);	
	               analysis.performAnalysis();
	               System.out.println("-----------------------------------------");
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
        //Verilen repoUrl parçalanır ve ".git" uzantısını kaldırılarak depo adı alınır.
	    private static String getRepoName(String repoUrl) {
	        String[] parts = repoUrl.split("/");
	        String repoName = parts[parts.length - 1];
	        return repoName.substring(0, repoName.length() - 4); // .git kısmını kaldır
	    }
	}


