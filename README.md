# Lab03

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
