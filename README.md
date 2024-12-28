# MustCloud - Bulut Depolama Uygulaması

MustCloud, kullanıcıların dosyalarını güvenli bir şekilde depolayabilecekleri, paylaşabilecekleri ve yönetebilecekleri bir web tabanlı bulut depolama uygulamasıdır.

## Özellikler

- 📁 Dosya Yükleme ve Depolama
- 🔄 Dosya Paylaşımı
- 📥 Dosya İndirme
- 👥 Kullanıcı Yönetimi
- 🔐 Admin ve Normal Kullanıcı Rolleri
- 💫 Modern ve Kullanıcı Dostu Arayüz

## Teknolojiler

- Backend: Spring Boot
- Frontend: HTML, CSS, JavaScript
- Veritabanı: PostgreSQL
- Template Engine: Thymeleaf
- UI Framework: Custom CSS

## Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/yourusername/mustcloud.git
```

2. PostgreSQL veritabanını oluşturun:
```sql
CREATE DATABASE mustcloud;
```

3. `application.properties` dosyasını düzenleyin:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mustcloud
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Projeyi çalıştırın:
```bash
./mvnw spring-boot:run
```

5. Tarayıcınızda şu adresi açın:
```
http://localhost:8080
```

## Kullanım

### Normal Kullanıcı
- Dosya yükleme
- Dosya indirme
- Dosya paylaşma
- Dosya silme

### Admin Kullanıcı
- Tüm normal kullanıcı özellikleri
- Kullanıcı yönetimi
- Tüm dosyaları görüntüleme
- Yeni kullanıcı ekleme

## Ekran Görüntüleri

### Login Sayfası
![login](https://github.com/user-attachments/assets/81b04998-8904-4622-8823-48b2f6dd5bd1)

### Register Sayfası
![register](https://github.com/user-attachments/assets/43ccf4de-29e5-402b-9e35-5ae07872a664)


### User Dashboard
![user-dashboard](https://github.com/user-attachments/assets/2630a02d-1872-4a1d-8792-f444e5349109)
![user-file-select](https://github.com/user-attachments/assets/49b4ebf7-ce21-4c2a-8e84-99347a31196c)



### Admin Sayfası
![Admin-Dosya](https://github.com/user-attachments/assets/336fc5db-d91f-47d3-96c2-3a394a1b69a7)
![Admin-kullanıcı](https://github.com/user-attachments/assets/2703807a-6c44-46f5-b858-331931e9e973)
![Admin-Kullanıcı-ekle](https://github.com/user-attachments/assets/7ecfcf76-82b2-4b03-8302-ed434b643cc3)


### Dosya Paylaşımı ve Dosya Yükleme 
![File-share](https://github.com/user-attachments/assets/b958cec5-2864-478c-9a55-4fb3f203e527)
![File-upload](https://github.com/user-attachments/assets/dd58d974-dc90-4fdb-b4f1-7d90f6b6c5cd)

## Güvenlik

- Şifre hashleme
- Rol tabanlı yetkilendirme
- Güvenli dosya depolama
- Kullanıcı doğrulama

## Katkıda Bulunma

1. Bu projeyi fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/amazing`)
3. Değişikliklerinizi commit edin (`git commit -m 'Yeni özellik eklendi'`)
4. Branch'inizi push edin (`git push origin feature/amazing`)
5. Pull Request oluşturun

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Daha fazla bilgi için [LICENSE](LICENSE) dosyasına bakın.

## İletişim

- Proje Sahibi: [Codriot](https://github.com/codriot) 
- E-posta: [codriot](mustafa.al10@yahoo.com)

## Teşekkürler
[Yusuf Akçal](https://github.com/Yusufakcl)

Bu projeyi geliştirirken kullandığımız açık kaynak yazılımlara ve topluluklara teşekkür ederiz:

- Spring Boot
- Thymeleaf
- PostgreSQL
- Font Awesome
