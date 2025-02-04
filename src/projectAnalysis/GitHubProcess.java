/**
*
* @author Sümeyye Karataş && karatasumeyye35@gmail.com
* @since 06.04.2024
* <p>
* GitHubProcess sınıfı,repo klonlama metotu ve klonlanan dosyadaki java dosyalarından classları getiren metotları içerir.
* </p>
*/

package projectAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.ProcessBuilder;

public class GitHubProcess {
	
	private String repoUrl;
	
	public GitHubProcess(String repoURL) {
		this.repoUrl = repoURL;
	}
	
	 public void cloneRepository() throws IOException, InterruptedException {
	        // Git deposunu klonlama işlemini gerçekleştiren ProcessBuilder kullanılır
	        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoUrl);
	        Process process = processBuilder.start();
	        int exitCode = process.waitFor();
	        System.out.println("depo klonlandı.");
	      
	        if (exitCode != 0) {
	            throw new IOException("Depo klonlanamadı. Lütfen geçerli bir GitHub URL'si sağlayın.");
	        }
	    }

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	
	 public List<File> getJavaFiles(String repoDirectoryPath) {
	        List<File> javaFiles = new ArrayList<>();
	        File repoDir = new File(repoDirectoryPath);

	        // Klonlanmış depo dizinindeki tüm dosyaları gez
	        for (File file : repoDir.listFiles()) {
	            // Eğer dosya bir dizin ise, içeriğini kontrol et
	            if (file.isDirectory()) {
	                javaFiles.addAll(getJavaFiles(file.getAbsolutePath()));
	            } else {
	                // Eğer dosya *.java uzantılı ise, listeye ekle
	                if (file.getName().endsWith(".java")) {
	                	
	                	try(Scanner scanner= new Scanner(file)){
	                		while (scanner.hasNextLine()) {
	                			String line = scanner.nextLine().trim();
	                			//Eğer satırda "class" kelimesi varsa, dosya sınıf dosyasıdır.
	                			if(line.contains("class")) {
	                				 javaFiles.add(file);
	                				 break;
	                			}
	                		}
	                		
	                		
	                	}catch(FileNotFoundException e) {
	                		e.printStackTrace();
	                	}	                		                	     	                   
	                }
	            }
	        }
	        return javaFiles;
	    }
	

	
	
	
	
}	