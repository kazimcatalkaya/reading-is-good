# READING IS GOOD

Bu uygulama ile kitap siparişi verilir ,siparişler izlenir.

## Gereksinimler
* java 11
* mongoDB
* docker
* maven

## Kullanilan Teknolojiler

* Spring Boot, Spring Web, Spring Security , Spring Data MongoDB ,JWT
* Lombok
* MapStruct
* Swagger 2


## Uygulama Çalıştırma

Uygulamanın ana dizininde terminal açılır.
`mvn clean package docker:build` komutu ile uygulama build edilir ve docker image olusturulur.
`docker-compose up -d` komutu çalıştırılarak docker compose dosyası içerisinde ki servisler ayağa kaldırılır.

## Uygulama Arayüzleri
Swagger UI ile API dökümantasyonuna erişilir.

SwaggerUI URL http://localhost:8080/swagger-ui.html

Mongo Express UI ile mongo db de tutulan dokumanlara erişilir.Mongo Express UI URL http://localhost:8081/db/readingIsGood/
Mongo Experess kullanıcı adı : `user` şifresi: `pass`

## Controller
### BookController 
Kitaplar ile ilgili işlemler bu servis ile yapılır.
* `getAllBooks` Bütün kitaplar listelenir.
* `createBook` Yeni kitap oluşturulur.
* `updateBook` Mevcut kitap bilgileri güncellenir.										   

Kitap kayıt ve güncelleme işlemi Manager Role e sahip kullanıcıya ait Bearer token ile gerçekleştirilir.

Sadece öntanımlı email ve şifresi "admin" olan kullanıcı kitap oluşturma ve güncelleme işlemlerini yapabilir.

### CustomerController
Müşteri kayıt ve giriş işlemleri yapılır. Giriş yapmış olan müşterinin kitapları listelenir.

* `registerCustomer` Müşteri kayıt edilir.
* `loginCustomer` Müşteri giriş yapabilir.
* `getOrders` Müşteriye ait siparişler listelenir.

Kayıt ve giriş servisleri Bearer token dönmektedir. Bu token ile kullanıcı işlemlerini yapabilir.

### OrderController
Siparişler ile ilgili işlemler bu servis altında yapılmaktadır.

* `createOrder` Yeni sipariş olusturur.
* `getOrderById` Verilen sipariş ID bilgisi ile sipariş detayları getirilir.
* `getOrdersByDateInterval` Verilen başlangıç ve bitiş tarihi arasındaki siparişler listelenir. Başlangıç ve bitiş tarihleri opsiyonel olup herhangi biri belirtilmediği takdirde default değerler uygulanır.

### Statistic Controller
Kullanıcı istatikleri listelenir.
* `getStatistics` Giriş yapmış olan müşterinin ay bazında sistemde oluşturduğu siparişlerin istatistik bilgileri listelenir.

# Postman Collection
### Postman üzerinden örnek requestler ile senaryo

* Kullanıcı login : admin:admin kullanıcısı ile giriş yapılır.

* Kitap Oluşturma 1: admin bearer token header'a eklenip, kitap oluşturulur.

* Kitap Oluşturma 2: admin bearer token header'a eklenip, yeni kitap oluşturulur.

* Kitap Listeleme : Eklenen bütün kitaplar listelenir.

* Kitap Güncelleme : Eklenmiş olan kitaplardan biri güncellenir.

* Kitap Listeleme : Eklenen bütün kitaplar listelenir. Güncellenen kitap görülür.

* Yeni Kullanıcı Kaydı : Yeni bir kullanıcı kaydı oluşturulur.

* Kullanıcı girişi : Oluşturulan kullanıcı ile giriş yapılır.

* Kitap Sipariş : Yeni kullanıcı ile kitap sipariş verilir.
  
* Kitap Sipariş 2: Yeni kullanıcı ile kitap sipariş verilir.

* Sipariş Görüntüleme ID : id ile sipariş edilmiş kitap görüntülenir.

* Tüm Siparişleri Listeleme : Yeni kullanıcının tüm siparişleri listelenir.

* Sipariş Görüntüleme Tarih Aralığı : Tarih aralığına uyan siparişler görüntülenir.

* istatistik Görüntüleme : Giriş yapmış kullanıcının ay bazlı kitap sipariş istatistikleri listelenir.
