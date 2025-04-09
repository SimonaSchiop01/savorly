package savorly.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
//     Aici folosim adnotarea @Value pentru a injecta valoarea din fișierul de configurare a
//     aplicației (de obicei, application.properties sau application.yml). Căutăm o proprietate
//     numită file.upload-dir. Dacă aceasta există în fișierul de configurare, îi vom atribui
//     valoarea variabilei uploadDir. Dacă nu există, se va folosi valoarea implicită "uploads",
//     adică directorul în care vor fi stocate fișierele.
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

//    MultipartFile: Este o interfață din Spring care reprezintă un fișier încărcat printr-un formular
//    web. Aceasta ne permite să manipulăm fișierele în cadrul aplicației Spring.
//    Definirea metodei storeFile, care primește un fișier (MultipartFile) și returnează un String
//    (numele fișierului stocat pe server). Această metodă se ocupă de procesul de salvare a fișierului
//    pe disc.
    public String storeFile(MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
//            Creăm un obiect Path care reprezintă calea către directorul unde vrem să salvăm fișierul.
//            Aceasta va fi calea directorului de upload, pe care am obținut-o mai devreme din fișierul
//            de configurare.
            Path uploadPath = Paths.get(uploadDir);
//             Verificăm dacă directorul uploadDir există deja. Dacă nu există, îl creăm folosind
//             Files.createDirectories(). Această metodă creează directorul, inclusiv orice subdirectoare
//             necesare.
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename to prevent conflicts
//            Obținem numele original al fișierului încărcat. Acesta este numele fișierului exact așa cum
//            a fost trimis de utilizator din frontend
            String originalFilename = file.getOriginalFilename();
//            Extragem extensia fișierului (de exemplu .jpg, .png) din numele original. Verificăm dacă numele
//            fișierului conține un punct (.) și, dacă da, extragem extensia folosind substring.
            String fileExtension = "";
            if (originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
//            Generăm un nume unic pentru fișier, folosind UUID.randomUUID(). Acest lucru asigură că fișierul
//            nu va avea un nume conflictual dacă un alt fișier cu același nume a fost deja încărcat pe server.
//            Adăugăm extensia fișierului pentru a păstra tipul de fișier corect.
            String filename = UUID.randomUUID() + fileExtension;

            // Copy file to the target location
//             Creăm calea completă pentru fișierul care va fi stocat pe server, combinând directorul de
//             upload (uploadPath) și numele fișierului (filename).
//            Metoda resolve este folosită pentru a combina o cale relativă (în acest caz, numele
//            fișierului) cu o cale absolută deja existentă (în acest caz, calea directorului unde salvăm
//            fișierele).
            Path targetLocation = uploadPath.resolve(filename);
//            Copiem fișierul de la locația sa inițială (obținută prin file.getInputStream()) în locația de
//            destinație (targetLocation). Folosim StandardCopyOption.REPLACE_EXISTING, ceea ce înseamnă că,
//            în cazul în care fișierul există deja, va fi înlocuit.
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

//             Returnăm numele fișierului salvat. Acest nume va fi folosit pentru a face referință la fișierul
//             respectiv în aplicație.
            return filename;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

//             Această metodă returnează calea completă a fișierului salvat pe server, în funcție de numele
//             său (filename). Acesta este folosit atunci când vrei să obții calea fișierului pentru a-l
//             trimite mai departe (de exemplu, pentru a-l citi sau pentru a-l trimite înapoi ca răspuns la
//             o cerere HTTP).
    public Path getFilePath(String filename) {
        return Paths.get(uploadDir).resolve(filename);
    }
}

