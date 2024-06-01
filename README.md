


<img src ="Rohat Doğaner (1).png"/>


# Veteriner Yönetim Sistemi 
- Veteriner Yönetim Sistemi, veteriner kliniklerinin günlük işlerini düzenlemek ve yönetmek amacıyla oluşturulmuş bir REST API'dir. Bu API ile veteriner çalışanının veteriner doktorları, müşterileri, hayvanları ve aşılarını, randevuları yönetmesi sağlanır.

# Veterinary Management System
- Veterinary Management System is a REST API created to organize and manage the daily affairs of veterinary clinics. With this API, the veterinary worker is enabled to manage veterinarians, customers, animals and their vaccinations, and appointments.

# Kullanılan Teknolojiler / Used Technologies
- Java                     
- Spring Boot 
- PostgresSQL
- Swagger

# Özellikler 
- Veteriner Doktorları Yönetimi: Veteriner doktorları ekleyebilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Müşteri Yönetimi: Müşterileri kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Hayvan Yönetimi: Hayvanları sisteme kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Aşı Yönetimi: Hayvanlara uygulanan aşıları kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Randevu Yönetimi: Veteriner doktorları için randevular oluşturabilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Çeşitli filtreleme yetenekleri

# Features
- Veterinarians Management: Ability to add, update, view and delete veterinarians.
- Customer Management: Ability to register customers, update their information, list and delete them.
- Animal Management: Ability to register animals in the system, update their information, list and delete them.
- Vaccine Management: Ability to record, update, list and delete vaccinations applied to animals.
- Appointment Management: Ability to create, update, view and delete appointments for veterinarians.
- Various filtering capabilities

# UML Diyagram / UML Diagram
<img src ="rd.png"/>



# API Kullanımı / API Usage

Aşağıda, API'nin sunduğu temel endpoint'lerin bir listesi bulunmaktadır:

Below is a list of the main endpoints the API offers:



# Veterinary System API Endpoints

## AvailableDateController

| HTTP Method | Endpoint                          | Description                                                               | Response Type                         |
|-------------|-----------------------------------|---------------------------------------------------------------------------|---------------------------------------|
| GET         | /v1/available-dates/{id}          | Get available date details by ID                                          | ResultData<AvailableDataResponse>     |
| POST        | /v1/available-dates/created       | Create a new available date                                               | ResultData<AvailableDataResponse>     |
| PUT         | /v1/available-dates/update/{id}   | Update available date details by ID                                       | ResultData<AvailableDataResponse>     |
| DELETE      | /v1/available-dates/{id}          | Delete available date by ID   

## CustomerController

| HTTP Method | Endpoint                          | Description                                                               | Response Type                         |
|-------------|-----------------------------------|---------------------------------------------------------------------------|---------------------------------------|
| POST        | /api/v1/customers/created         | Create a new customer                                                     | ResultData<CustomerResponse>          |
| GET         | /api/v1/customers/{id}            | Get customer details by ID                                                | ResultData<CustomerResponse>          |
| PUT         | /api/v1/customers/update/{id}     | Update customer details by ID                                             | ResultData<CustomerResponse>          |
| DELETE      | /api/v1/customers/{id}            | Delete customer by ID                                                     | Result                                |
| GET         | /api/v1/customers/customersList   | Get a paginated list of customers                                         | ResultData<CursorResponse<CustomerResponse>> |
| GET         | /api/v1/customers/filter          | Get customers filtered by name    

## DoctorController

| HTTP Method | Endpoint                          | Description                                                               | Response Type                         |
|-------------|-----------------------------------|---------------------------------------------------------------------------|---------------------------------------|
| GET         | /v1/doctors/{id}                  | Get doctor details by ID                                                  | ResultData<DoctorResponse>            |
| POST        | /v1/doctors/created               | Create a new doctor                                                       | ResultData<DoctorResponse>            |
| PUT         | /v1/doctors/update/{id}           | Update doctor details by ID                                               | ResultData<DoctorResponse>            |
| DELETE      | /v1/doctors/{id}                  | Delete doctor by ID   

## VaccineController

| HTTP Method | Endpoint                          | Description                                                               | Response Type                         |
|-------------|-----------------------------------|---------------------------------------------------------------------------|---------------------------------------|
| POST        | /v1/vaccines/created              | Create a new vaccine                                                      | ResultData<VaccineResponse>           |
| GET         | /v1/vaccines/{id}                 | Get vaccine details by ID                                                 | ResultData<VaccineResponse>           |
| DELETE      | /v1/vaccines/{id}                 | Delete vaccine by ID                                                      | Result                                |
| PUT         | /v1/vaccines/update/{id}          | Update vaccine details by ID                                              | ResultData<VaccineResponse>           |
| GET         | /v1/vaccines/animal/{animalId}    | Get all vaccines for a specific animal                                    | ResultData<List<VaccineResponse>>     |
| GET         | /v1/vaccines/date-range           | Get vaccines within a specific date range                                 | ResultData<List<VaccineResponse>>     |
| GET         | /v1/vaccines/animal/filter/date   | Get vaccines for a specific animal within a date range                    | ResultData<List<VaccineResponse>>     |


# AnimalController

| HTTP Method | Endpoint                              | Description                                                            | Response Type                     |
|-------------|---------------------------------------|------------------------------------------------------------------------|----------------------------------|
| GET         | /v1/animals/{id}                      | Get an animal by ID                                                    | ResultData<AnimalResponse>       |
| POST        | /v1/animals/created                   | Save a new animal                                                      | ResultData<AnimalResponse>       |
| PUT         | /v1/animals/update/{id}               | Update an existing animal by ID                                        | ResultData<AnimalResponse>       |
| DELETE      | /v1/animals/{id}                      | Delete an animal by ID                                                 | Result                           |
| GET         | /v1/animals/{id}/vaccines             | Get all vaccines for a specific animal by animal ID                    | ResultData<List<VaccineResponse>>|
| GET         | /v1/animals/filterAnimalName          | Get animals filtered by name                                           | ResultData<List<AnimalResponse>> |
| GET         | /v1/animals/customer/filterId{customerId} | Get animals filtered by customer ID                                     | ResultData<List<AnimalResponse>> |
| GET         | /v1/animals/customer/filterCustomerName/{customerName} | Get animals filtered by customer name                                   | ResultData<List<AnimalResponse>> |


# AppointmentController

| HTTP Method | Endpoint                                               | Description                                                                    | Response Type                        |
|-------------|--------------------------------------------------------|--------------------------------------------------------------------------------|-------------------------------------|
| GET         | /v1/appointments/filter/doctor/{doctorId}              | Get appointments by doctor ID                                                  | ResultData<List<AppointmentResponse>>|
| DELETE      | /v1/appointments/{id}                                  | Delete an appointment by ID                                                    | Result                              |
| GET         | /v1/appointments/filter/animal/{animalId}              | Get appointments by animal ID                                                  | ResultData<List<AppointmentResponse>>|
| PUT         | /v1/appointments/update/{id}                           | Update an existing appointment by ID                                           | ResultData<AppointmentResponse>     |
| POST        | /v1/appointments/created                               | Save a new appointment                                                         | ResultData<AppointmentResponse>     |
| GET         | /v1/appointments/{id}                                  | Get an appointment by ID                                                       | ResultData<AppointmentResponse>     |
| GET         | /v1/appointments/filter/dateANDdoctor/appointments     | Get appointments by date range and optional doctor ID                          | ResultData<List<AppointmentResponse>>|
| GET         | /v1/appointments/filter/dateANDanimal                  | Get appointments by date range and optional animal ID                          | ResultData<List<AppointmentResponse>>|
| GET         | /v1/appointments/filterAnimalName/{animalName}         | Get appointments by animal name                                                | ResultData<List<AppointmentResponse>>|
