# Newsletter & Birthday Email Scheduler (Spring Boot)

Applicazione Spring Boot progettata secondo il pattern architetturale a 3 livelli (Controller, Service, Repository) per la gestione dei dipendenti e l'invio automatico di e-mail di auguri di compleanno tramite task schedulato.

---

## 1. Istruzioni di Avvio

### Prerequisiti
* **Java**: JDK 17 (o superiore)
* **Database**: PostgreSQL
* **Email Testing**: Account Mailtrap (SMTP Sandbox)
* **API Testing**: Postman

### Configurazione del Database e Mailtrap
Prima di avviare l'applicazione, verificare il file `src/main/resources/application.properties` e configurare le proprie credenziali per **PostgreSQL** e **Mailtrap**:

```properties
# Configurazione PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/newsletter_db
spring.datasource.username=tuo_username_postgres
spring.datasource.password=tua_password_postgres
spring.jpa.hibernate.ddl-auto=update

# Configurazione Mailtrap SMTP
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=tuo_username_mailtrap
spring.mail.password=tua_password_mailtrap
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```
### Avvio
Cliccare con il tasto destro **Application -> RUN AS -> Spring Boot App**. All'avvio del server, Spring Boot crea automaticamente la struttura delle tabelle su PostgreSQL.
Tramite il componente DataInitializer, vengono popolati automaticamente alcuni record di test per consentire la verifica immediata delle funzionalità.
All'interno del progetto si può trovare la collezione postman per facilitare le chiamate api.

---

## 2. Architettura
### Modellazione del Dominio
Per garantire la normalizzazione del database e la manutenibilità del codice, il modello include tre entità principali:
* **`Employee`**: Entità centrale che rappresenta il dipendente.
* **`Team`** e **`WorkLocation`**: Modellate come entità dedicate (anziché semplici stringhe) per evitare ridondanza dei dati, garantire la consistenza referenziale e consentire una gestione dinamica delle sedi e dei team aziendali.

---

### Architettura a 3 Livelli (3-Tier Architecture)
L'applicazione rispetta il principio di separazione delle responsabilità (*Separation of Concerns*):
* **Controller Layer (`@RestController`)**: Espone le API REST e gestisce il mapping delle richieste e risposte HTTP. Garantisce il disaccoppiamento tra i client esterni e la logica applicativa.
* **Service Layer (`@Service`)**: Custodisce la logica di business. Intercetta i dati dei repository, filtra esclusivamente i dipendenti attivi (`active = true`) ed effettua il controllo sulla corrispondenza della data di nascita con la data corrente.
* **Repository Layer (`@Repository`)**: Interfaccia Spring Data JPA per l'accesso diretto e l'astrazione delle operazioni CRUD sul database PostgreSQL.

---

## 3. Automation & Scheduling
* **`BirthdayScheduler` (`@EnableScheduling`)**: Componente schedulato tramite l'annotazione `@Scheduled`, configurato per eseguirsi automaticamente ogni mattina alle **09:00**. 
Il task identifica i festeggiati del giorno dal database ed evoca il servizio di invio delle e-mail di auguri tramite Mailtrap.

--
## 4. 🔮 Sviluppi Futuri e Possibili Miglioramenti

Sebbene il core dell'applicazione sia completamente funzionale, sono stati individuati i seguenti punti di estensione per la validazione e la consistenza dei dati:

1. **Validazione dei Dati d'Ingresso (Bean Validation)**:
   * Integrazione delle annotazioni di Jakarta Validation (`@Email`, `@NotBlank`, `@NotNull`) sulle entità e sui DTO per verificare la correttezza formale dei campi (es. sintassi dell'indirizzo e-mail e date non future).

2. **Gestione dei Duplicati e Normalizzazione (Case Insensitivity)**:
   * Controllo di unicità e normalizzazione dei testi per entità come `WorkLocation` e `Team`.
   * Introduzione di controlli di validazione nel Service Layer (o vincoli `UNIQUE` case-insensitive a livello PostgreSQL) per prevenire
 la creazione di record duplicati dovuti alla diversa spaziatura o combinazione di maiuscole/minuscole (es. evitare l'inserimento separato di *"Milano"*, *"milano"* e *"MILANO"*).
