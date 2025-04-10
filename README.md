Aceasta componenta React reprezinta pagina principala a unei aplicatii de retete, unde utilizatorii pot cauta, filtra si vizualiza retete. Functionalitatile includ:

Cautare dinamica: Permite utilizatorilor sa caute retete dupa nume sau descriere.

Filtrare dupa categorie: Utilizatorii pot filtra retetele in functie de categorie (de exemplu, "Toate retetele", "ciorbe", "deserturi", "salate").

Incarcare asincrona a datelor: Retetele si categoriile sunt incarcate dintr-o sursa API externa folosind axios, iar utilizatorul este notificat in cazul unei erori.

Gestionare stare: Utilizeaza useState si useEffect pentru gestionarea starii aplicatiei si pentru a asigura actualizarea corecta a componentelor la modificarile de stare.

Adaugare si stergere reteta: Utilizatorii pot adauga o reteta noua, pot edita retetele existente si le pot sterge.

Este inclusa si logica de afisare a unui mesaj de eroare sau a unui loader in functie de starea incarcarii datelor. Aceasta aplicatie este construita cu un design responsive, pentru a se adapta pe diferite dispozitive.

![image](https://github.com/user-attachments/assets/fef50038-148e-4a49-b180-cb68f174b951)
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

Aceasta componenta React permite utilizatorilor sa editeze o reteta existenta. Pagina incarca automat informatiile retetei selectate si afiseaza un formular precompletat, care poate fi modificat si trimis catre server. Functionalitatile principale includ:

Incarcare asincrona a datelor: La montarea componentei, sunt incarcate datele retetei (inclusiv imaginea), categoriile disponibile si lista de ingrediente, folosind axios si endpointuri API dedicate.

Formular complet editabil: Utilizatorii pot edita toate campurile retetei: denumire, descriere, timp de preparare, categorie, ingrediente si imagine. Imaginea poate fi inlocuita sau pastrata.

Editare dinamica a ingredientelor: Fiecare ingredient din reteta poate fi modificat sau sters. De asemenea, se pot adauga ingrediente noi. Sistemul previne selectarea aceluiasi ingredient de mai multe ori.

Validare formular: Se verifica completarea tuturor campurilor obligatorii inainte de trimitere, inclusiv pentru fiecare ingredient.

Upload imagine: Daca utilizatorul selecteaza o imagine noua, aceasta este incarcata folosind FormData. Daca nu, se trimite un placeholder pentru a pastra imaginea existenta.

Trimitere actualizari catre server: Dupa completarea formularului, modificarile sunt trimise printr-un request PUT catre API (/api/recipes/:id). Daca operatiunea reuseste, utilizatorul este redirectionat catre pagina retetei.

Gestionare stare si erori: Componenta utilizeaza useState si useEffect pentru controlul starii. Utilizatorul este informat in caz de eroare, iar butoanele de salvare sunt dezactivate in timpul trimiterii.
![image](https://github.com/user-attachments/assets/7c83b7bc-7666-4764-a542-ff4535ad062f)
![image](https://github.com/user-attachments/assets/c18cf1fc-2529-4321-abb3-ef7ce85d3595)

Aceasta componenta React afiseaza pagina detaliata a unei retete individuale. Utilizatorii pot vizualiza informatiile complete ale retetei, inclusiv imaginea, descrierea, timpul de preparare si ingredientele. De asemenea, au optiuni pentru editarea sau stergerea retetei. Functionalitatile principale includ:

Incarcare asincrona a datelor retetei: La montarea componentei, este trimisa o cerere catre API (/api/recipes/:id) pentru a obtine detaliile retetei selectate. Se gestioneaza starea de incarcare si eventualele erori de retea.

Afisare detalii reteta: Sunt afisate toate informatiile retetei:

Numele retetei

Imaginea asociata

Categoria din care face parte

Timpul de preparare (daca exista)

Descrierea retetei

Lista ingredientelor cu denumire si cantitate

Stergere reteta: Utilizatorii pot sterge reteta curenta. Inainte de stergere, se afiseaza un mesaj de confirmare. Dupa stergere, utilizatorul este redirectionat catre pagina principala.

Navigare si editare: Pagina include butoane pentru intoarcere la homepage si pentru editarea retetei curente, utilizand navigarea cu react-router-dom.

Gestionare erori si fallback-uri: In cazul in care reteta nu poate fi incarcata sau nu exista, sunt afisate mesaje clare de eroare si optiuni pentru reincarcare sau revenire.
![image](https://github.com/user-attachments/assets/8ff6fbcb-a205-4623-a391-540e4cb64a2e)






