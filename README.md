# JAVACODEANALYZER-
# **Sistem Tanımı**  
Projenin temel amacı, kullanıcıların belirledikleri GitHub depolarının içindeki Java sınıflarını analiz etmelerine yardımcı olmaktır.  
Sistem, kullanıcıların belirledikleri GitHub depo linklerini alacak, depoları klonlayacak ve içindeki `*.java` uzantılı dosyaları getirecektir.  
Ardından sistem belirli analiz teknikleri kullanarak bu dosyaları işleyecek ve her bir sınıf için çeşitli analizler yapacaktır.  

Sistem, **yazılım geliştiricileri** ve/veya **yazılım analistleri** gibi teknik kullanıcılar tarafından kullanılacaktır.  
Özellikle büyük ve karmaşık projelerde **kod kalitesini değerlendirmek** için kullanışlı olacaktır.  

---

# **Gereksinimler**  

## **2.1 Kullanıcı Girişi**  
- Kullanıcıdan **GitHub depo linki (Repository URL)** alınmalıdır.  

## **2.2 Depo İşlemleri**  
- Girilen **GitHub depo linkini klonlama** işlemi gerçekleştirilmelidir.  
- Klonlanan depodan **`*.java` uzantılı dosyalar** getirilmelidir.  

## **2.3 Sınıf Ayıklama ve Analiz**  
- Getirilen dosyalardan sadece içinde **sınıf bulunanlar** ayıklanmalıdır.  
- Her sınıf için aşağıdaki analizler yapılmalıdır:  
  - **Javadoc yorum satırı sayısı**  
  - **Diğer yorum satırı sayısı**  
  - **Kod satırı sayısı** (tüm yorum ve boşluk satırları hariç)  
  - **LOC (Line of Code)** (Bir dosyadaki her şey dahil satır sayısı)  
  - **Fonksiyon sayısı** (Sınıfın içinde bulunan tüm fonksiyonların toplam sayısı)  
  - **Yorum sapma yüzdesi**  

---

# **UML Sınıf Diyagramı**  
![image](https://github.com/user-attachments/assets/0da494ff-86c1-40b4-83cb-f45a32520d39)


---

# **Sınıflar ve Açıklamaları**  

## **4.1 Main Sınıfı**  
Bu sınıf, projenin ana çalıştırılabilir noktasını sağlar. Kullanıcıdan bir **GitHub deposunun URL'sini** alır, depoyu klonlar, klonlanmış depodaki **Java dosyalarının içinde class olanları** işler ve her dosya için analiz yapar.  

- **`main(String[] args)`**  
  - Depo klonlama, Java dosyalarını işleme ve analiz etme metotlarını kullanarak **projenin çalıştırılabilir noktasıdır**.  

- **`getRepoName(String repoUrl)`**  
  - Verilen `repoUrl` parçalanır ve **“.git” uzantısı kaldırılarak** depo adı döndürülür.  

---

## **4.2 GitHubProcess Sınıfı**  
`GitHubProcess` sınıfı, GitHub deposunu **klonlama işini** gerçekleştirir ve klonlanan dosyadan **Java dosyalarını** getirir.  

- **`cloneRepository()`**  
  - Git deposunu **klonlamak için kullanılır**. `git clone` komutunu çağırır ve işlemin başarılı olup olmadığını kontrol eder.  

- **`getJavaFiles(String repoDirectoryPath)`**  
  - Belirtilen dizindeki **tüm Java dosyalarını içeren bir `List<File>` döndürür**.  
  - Dizinin içeriği incelenir, alt dizinler de dahil olmak üzere tüm dosyalar taranır ve **Java dosyaları `javaFiles` listesine eklenir**.  
  - Bu işlem sırasında, **dosyanın içeriği taranarak** `"class"` kelimesinin geçtiği dosyalar **sınıf dosyası** olarak kabul edilir ve listeye eklenir.  

---

## **4.3 AnalysisProject Sınıfı**  
Bu sınıf, **verilen bir Java dosyasının analizini yapar**.  

Aşağıdaki analizleri gerçekleştirir:  
- **Dosyadaki toplam satır sayısı**  
- **Javadoc yorum satırı sayısı**  
- **Diğer yorum satırı sayısı**  
- **Kod satırı sayısı**  
- **LOC (Line of Code)**  
- **Dosyadaki fonksiyon sayısı**  
- **Yorum sapma yüzdesi**  

### **Metotlar:**  
- **`AnalysisCalculations(File javaFile)`**  
  - Sınıfın **yapıcı metodudur**. Analiz yapılacak olan Java dosyasını alır.  

- **`countLines() throws FileNotFoundException`**  
  - **Dosyadaki toplam satır sayısını hesaplar**.  

- **`countJavadocLines() throws FileNotFoundException`**  
  - **Dosyadaki Javadoc yorum satırı sayısını hesaplar**.  

- **`countOtherCommentLines() throws FileNotFoundException`**  
  - **Dosyadaki diğer yorum satırı sayısını hesaplar**.  

- **`countCodeLines() throws FileNotFoundException`**  
  - **Dosyadaki kod satırı sayısını hesaplar**.  

- **`countLOC() throws FileNotFoundException`**  
  - **Her türlü satırı (yorum satırları dahil) içeren toplam satır sayısını hesaplar**.  

- **`hasGenericTypes() throws FileNotFoundException`**  
  - **Dosyadaki satırlarda generic tiplerin olup olmadığını kontrol eder**.  

- **`countFunctionCount() throws FileNotFoundException`**  
  - **Dosyadaki fonksiyon sayısını hesaplar**.  
  - **Java dilindeki anahtar kelimeler** (`public`, `private`, `protected`, `static`, `abstract`, `final`, `synchronized`) ve **dönüş tipleri** (`int`, `float`, `double`, `String`, `void`, `boolean`, `char`) dikkate alınır.  
  - **Generic tipler içeren fonksiyonlar da sayılır**.  

- **`calculateCommentDeviation() throws FileNotFoundException`**  
  - **Yorum sapma yüzdesini hesaplar**.  
  - **Javadoc ve diğer yorum satırları arasındaki oran**, kod satırları ile karşılaştırılarak yüzde olarak ifade edilir.  

- **`performAnalysis()`**  
  - **Hesaplanan değerleri yazdırır**.  
  - **Sınıfın adı**, **Javadoc satır sayısı**, **diğer yorum satırı sayısı**, **kod satırı sayısı**, **LOC**, **fonksiyon sayısı** ve **yorum sapma yüzdesi** ekrana bastırılır.  


