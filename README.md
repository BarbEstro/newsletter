# Newsletter & Birthday Email Scheduler (Spring Boot)

Applicazione Spring Boot progettata secondo il pattern architetturale a 3 livelli (Controller, Service, Repository) per la gestione dei dipendenti e l'invio automatico di e-mail di auguri di compleanno tramite task schedulato.

---

## 1. Istruzioni di Avvio

### Prerequisiti
* **Java**: JDK 17 (o superiore)
* **Database**: PostgreSQL (fornito via Docker, vedi sotto)
* **Docker** e **Docker Compose**

### Avvio del Database con Docker
Il progetto include un file `docker-compose.yml` che avvia un container PostgreSQL già configurato con le credenziali di default

Dalla root del progetto:

```bash
docker-compose up -d
```

### Avvio
Assicurarsi che il container PostgreSQL sia attivo (`docker-compose up -d`), 
Tramite il componente DataInitializer, vengono popolati automaticamente alcuni record di test per consentire la verifica immediata delle funzionalità.
Attraverso un nuovo terminale è possibile eseguire i seguenti comandi:

#### WorkLocation curl
```bash
# Crea una sede
curl -X POST http://localhost:8080/api/work-locations \
  -H "Content-Type: application/json" \
  -d '{"city":"Milano","address":"Via Roma 1"}'

# Lista sedi
curl http://localhost:8080/api/work-locations
```
#### Teams
```bash
# Crea un team
curl -X POST http://localhost:8080/api/teams \
  -H "Content-Type: application/json" \
  -d '{"name":"Engineering"}'

# Lista team
curl http://localhost:8080/api/teams

# Singolo team
curl http://localhost:8080/api/teams/1
```
#### Employes
```bash
# Crea un dipendente (usa gli id reali di workLocation/team restituiti sopra)
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Mario",
    "surname":"Rossi",
    "email":"mario.rossi@example.com",
    "dateOfBirth":"1990-10-09",
    "workLocationId":1,
    "teams":[1]
  }'

# Dipendente per id
curl http://localhost:8080/api/employees/1

# Lista dipendenti attivi
curl http://localhost:8080/api/employees/active

# Cambia stato (ACTIVE / INACTIVE)
curl -X PATCH http://localhost:8080/api/employees/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"INACTIVE"}'

# Elimina dipendente
curl -X DELETE http://localhost:8080/api/employees/1
```
#### Emails
```bash
# Invia manualmente le email di compleanno
curl -X POST http://localhost:8080/api/emails/send-birthdays

# Storico log email inviate
curl http://localhost:8080/api/emails/logs

# Aggiorna il template email
curl -X PUT http://localhost:8080/api/emails/template \
  -H "Content-Type: application/json" \
  -d '{"subject":"Buon Compleanno!","template":"Tanti auguri, {name}!"}'
```

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
