It is better to use @Serializable than give strange names to clasess

# Lab03

For English below:

Podczas laboratorium należy zbudować "mały system", pozwalający na interakcje z użytkownikami z poziomu konsoli, umożliwiający wykonywanie operacji CRUD (od ang. create, read, update and delete; pol. utwórz, odczytaj, aktualizuj i usuń) na przetwarzanych danych. Dane powinny być w jakiś sposób utrwalane (mogą być zapisywne w plikach).
Wymagane jest, by logika biznesowa systemu była oddzielona od interfejsu użytkownika (by dało się bez problemu podmienić interfejs konsolowy na interfejs graficzny). Ponadto należy zaprojektować odpowiednie struktury danych (do czego można wykorzystać kolekcje i mapy) oraz obsłużyć wyjątki (oprócz wyjątków generowanych przez wykorzystywane metody Java API należy zaproponować obsługę własnych wyjątków).
Budowany system wspierać ma działanie restauracji typu fastfood. Oczywiście system ten będzie jedynie "przybliżeniem" rzeczywistości. Aby dało się go zaimplementować przyjmujemy znaczące uproszczenia.
Zakładamy, że w procesie biorą udział następujący aktorzy: klienci, kucharze, sprzedawcy.
Klient: ma możliwość przeglądania listy oferowanych dań w restauracji, ma możliwość złożenia zamówienia, otrzymuje informację o statusie zamówienia, odbiera zamówienie i za nie płaci.
Kucharz: pobiera zamówienia z listy zamówień, przygotowuje danie, przekazuje gotowe danie do sprzedaży.
Sprzedawca: wydaje gotowe dania klientom po ich opłaceniu.
Wymienieni aktorzy uzyskują dostęp do systemu za pośrednictwem osobnych aplikacji: KlientApp (oferującej interfejs klienta), KucharzApp (oferującej interfejs kucharza), SprzedawcaApp (oferującej interfejs sprzedawcy).
Zakładamy, że:
zamówienie może zawierać kilka takich samych dań,
jedno zamówienie jest obsługiwane przez jednego kucharza,
przy przekroczeniu określonego limitu czasu na przygotowanie dania klientowi naliczany jest rabat,
aby odebrać zamówienie klient udają się do sprzedawcy i tam dokonuje transakcji, zaś sprzedawca obsługuje transakcję za pomocą własnej aplikacji.
Synchronizacja pomiędzy poszczególnymi częściami systemu (uruchomionymi instancjami wymienionych aplikacji) powinna odbywać się poprzez współdzielenie utrwalanych gdzieś danych. W przypadku zapisywania danych w systemie plików może pojawić się kłopot - system operacyjny może zablokować możliwość zapisu do danego pliku, jeśli aktualnie jest on otwarty w innej aplikacji. Wtedy może przydać się właśnie obsługa wyjątków. Generalnie - implementacja wielodostępu to bardzo trudny temat. Na potrzeby laboratorium mocno go upraszczamy (nie ma potrzeby budowania tytaj jakichś bardzo złożonych mechanizmów).
Nawiasem mówiąc niezły tutorial o operacjach na plikach można znaleźć pod adresem: https://www.marcobehler.com/guides/java-files Aby przetestować działanie systemu powinno dać się uruchamiać osobno: przynajmniej dwie instancje KlientApp, przynajmniej dwie instancje KucharzApp, przynajmniej dwie instancje SprzedawcaApp.
Funkcje przypisane użytkownikom (aktorom, patrz wyżej) to funkcje ogólne. W trakcie ich implementacji mogą pojawić się wymagania na funkcje szczegółowe, jak np. wyszukiwanie, filtrowanie, sortowanie danych na odpowiednich interfejsach.
Zakładamy, że system będzie komunikować się z użytkownikami z poziomu konsoli (czyli, że interfejs użytkownika będzie tekstowy - jeśli ktoś chciałby zaimplementować interfejs graficzny, to oczywiście może to zrobić, jednak nie jest to wymagane).
Model danych może być uproszczony. Wystarczy, że będzie on uwzględniał:
dane dotyczące zamówienia: identyfikator zamówienia, szczegóły zamówienia (lista identyfikatorów zamówionych dań), status zamówienia;
dane dotyczące dań: identyfikator dania, opis dania;
dane dotyczące płatności: identyfikator zamówienia, forma płatności, status płatności, kwota należności.
Proszę zastanowić się, jak będą wyglądały struktury do przechowywane w pamięci i jak będzie wyglądał ich zapis w plikach.
Proszę też zastanowić się, jak poradzić sobie symulowaniem osi czasu. W rzeczywistych systemach aplikacje czytają zegar systemowy. Trudno jednak wymagać, by w trakcie testów na laboratorium opierać się na zegarze systemowym. Zamiast tego można przyjąć, że bieżący czas jest zapisywany we współdzielonym pliku, a aplikacje odczytują go na żądanie (na zasadzie "odświeżenia"). Pozostałe szczegóły mają być zgodne z ustaleniami poczynionymi na początku zajęć.


During the lab, a 'small system' should be built, allowing user interaction from the console, to perform CRUD (create, read, update and delete) operations on the data being processed. The data should be persistent in some way (can be saved in files).
It is required that the business logic of the system is separated from the user interface (so that the console interface can be easily replaced by a graphical interface). In addition, appropriate data structures must be designed (for which collections and maps can be used) and exceptions must be handled (in addition to exceptions generated by the Java API methods used, custom exception handling must be proposed).
The system under construction is to support the operation of a fast food restaurant. Of course, the system will only be an "approximation" of reality. In order to be able to implement it, we assume significant simplifications.
We assume that the following actors are involved in the process: customers, cooks, sellers.
Customer: can browse the list of dishes on offer in the restaurant, has the option to place an order, receives information on the status of the order, receives the order and pays for it.
Cook: takes orders from the order list, prepares the dish, hands over the finished dish for sale.
Vendor: distributes the finished dish to customers after they have paid for it.
These actors access the system through separate applications: CustomerApp (offering a customer interface), CookApp (offering a cook interface), VendorApp (offering a vendor interface).
We assume that:
an order can contain several of the same dishes,
one order is served by one cook,
a discount is charged to the customer when the time limit for preparing a dish is exceeded,
to pick up the order, the customer goes to the vendor and makes the transaction there, and the vendor handles the transaction with its own application.
Synchronisation between the different parts of the system (running instances of the listed applications) should take place by sharing data stored somewhere. In the case of saving data in the file system, a problem may arise - the operating system may block writing to a particular file if it is currently open in another application. This is when exception handling can come in handy. In general - the implementation of multi-access is a very difficult topic. For the purposes of the lab, we simplify it a lot (there is no need to build any very complex mechanisms).
Incidentally, a nice tutorial on file operations can be found at: https://www.marcobehler.com/guides/java-files In order to test the operation of the system, it should be possible to run separately: at least two instances of CustomerApp, at least two instances of CookApp, at least two instances of SellerApp.
The functions assigned to users (actors, see above) are generic functions. In the course of their implementation, there may be requirements for specific functions, such as searching, filtering, sorting data on the corresponding interfaces.
We assume that the system will communicate with users from the console (i.e. that the user interface will be textual - if someone would like to implement a graphical interface, they can of course do so, but it is not required).
The data model can be simplified. It is sufficient that it includes:
order data: order identifier, order details (list of identifiers of ordered dishes), order status;
dish data: dish identifier, dish description;
payment data: order identifier, form of payment, payment status, amount due.
Please consider how the structures to be stored in memory will look and how they will be stored in files.
Please also consider how to deal with simulating the timeline. In real systems, applications read the system clock. However, it is difficult to require that the system clock is relied upon during lab testing. Instead, it can be assumed that the current time is stored in a shared file and the applications read it on demand (on a 'refresh' basis). The remaining details are to be as agreed at the beginning of the class.

Translated with www.DeepL.com/Translator (free version)
