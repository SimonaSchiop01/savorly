Această componentă React reprezintă pagina principală a unei aplicații de rețete, unde utilizatorii pot căuta, filtra și vizualiza rețete. Funcționalitățile includ:

Căutare dinamică: Permite utilizatorilor să caute rețete după nume sau descriere.

Filtrare după categorie: Utilizatorii pot filtra rețetele în funcție de categorie (de exemplu, „Toate rețetele” sau categorii specifice).

Încărcare asincronă a datelor: Rețetele și categoriile sunt încărcate dintr-o sursă API externă folosind axios, iar utilizatorul este notificat în cazul unei erori.

Gestionare stare: Utilizează useState și useEffect pentru gestionarea stării aplicației și pentru a asigura actualizarea corectă a componentelor la modificările de stare.

Adăugare și ștergere rețetă: Utilizatorii pot adăuga o rețetă nouă și pot șterge rețetele existente.

Este inclusă și logica de afișare a unui mesaj de eroare sau a unui loader în funcție de starea încărcării datelor. Această aplicație este construită cu un design responsive, pentru a se adapta pe diferite dispozitive.

![image](https://github.com/user-attachments/assets/69d9be1a-163b-4ccc-8177-33bf34b8ad92)
