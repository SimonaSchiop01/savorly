Aceasta componenta React reprezinta pagina principala a unei aplicatii de retete, unde utilizatorii pot cauta, filtra si vizualiza retete. Functionalitatile includ:

Cautare dinamica: Permite utilizatorilor sa caute retete dupa nume sau descriere.

Filtrare dupa categorie: Utilizatorii pot filtra retetele in functie de categorie (de exemplu, "Toate retetele", "ciorbe", "deserturi", "salate").

Incarcare asincrona a datelor: Retetele si categoriile sunt incarcate dintr-o sursa API externa folosind axios, iar utilizatorul este notificat in cazul unei erori.

Gestionare stare: Utilizeaza useState si useEffect pentru gestionarea starii aplicatiei si pentru a asigura actualizarea corecta a componentelor la modificarile de stare.

Adaugare si stergere reteta: Utilizatorii pot adauga o reteta noua, pot edita retetele existente si le pot sterge.

Este inclusa si logica de afisare a unui mesaj de eroare sau a unui loader in functie de starea incarcarii datelor. Aceasta aplicatie este construita cu un design responsive, pentru a se adapta pe diferite dispozitive.

![image](https://github.com/user-attachments/assets/fef50038-148e-4a49-b180-cb68f174b951)
![image](https://github.com/user-attachments/assets/8c3e58c3-52fe-47a0-95a1-faf9051e2b9c)
![image](https://github.com/user-attachments/assets/75b6961d-90a6-49e5-b12f-74119514ce96)


In componenta React pentru adaugare reteta in aplicatie, utilizatorii pot introduce detalii despre reteta, pot incarca o imagine, pot selecta ingrediente si o categorie, apoi pot salva reteta. Functionalitatile principale includ:

Formular pentru adaugare reteta: Utilizatorii completeaza un formular cu urmatoarele informatii: nume, descriere, timp de preparare, categorie, imagine si lista de ingrediente.

Incarcare asincrona a categoriilor si ingredientelor: La montarea componentei, sunt trimise cereri catre API pentru a prelua categoriile si ingredientele disponibile.

Adaugare dinamica a ingredientelor: Utilizatorii pot adauga mai multe randuri pentru ingrediente si pot seta cantitatea pentru fiecare. De asemenea, pot sterge ingrediente individuale.

Validare formular: Sunt verificate campurile esentiale (nume, categorie, ingrediente si imagine) inainte de trimiterea formularului.

Upload imagine: Se permite selectarea si afisarea unei imagini de previzualizare pentru reteta, imaginea fiind trimisa ca FormData impreuna cu celelalte informatii.

Trimitere date catre server: Dupa completarea formularului, reteta este trimisa catre backend printr-un request POST catre /api/recipes.

Gestionare stare si erori: Se utilizeaza useState si useEffect pentru gestionarea starii aplicatiei. Utilizatorul este notificat in caz de eroare la incarcarea datelor sau salvarea retetei.
![image](https://github.com/user-attachments/assets/20faf47a-8808-4bd9-9a8d-455c1234dbb7)
![image](https://github.com/user-attachments/assets/a2347697-1241-4c91-945c-40bee6cc1f8e)



