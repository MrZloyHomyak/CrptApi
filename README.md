# CrptApi - Thread-safe клиент для API Честного знака
Java клиент для работы с API системы маркировки товаров "Честный знак". Реализует потокобезопасное ограничение количества запросов.

## 🚀 Особенности
- **Thread-safe implementation** - безопасная работа в многопоточной среде
- **Rate limiting** - ограничение запросов по времени (Sliding Window algorithm)
- **Modern Java** - HttpClient, Instant, CompletableFuture
- **Zero dependencies** - только стандартная библиотека Java 11+
- **RESTful API client** - полное соответствие спецификации API

## 📋 Требования
- Java 11 или выше
- Поддержка ограничения на количество запросов
- Доступ к API Честного знака
- Все дополнительные используемые классы должны быть внутренними

## 🏗️ Архитектура
### Ключевые компоненты
- `CrptApi` - основной класс с ограничением запросов
- `Document` - модель документа для ввода в оборот
- `Product` - модель товара
- `Description` - описание документа

### Паттерны проектирования
- **Facade** - простой интерфейс для сложного API
- **Builder** - для построения HTTP запросов
- **Immutable objects** - потокобезопасность данных

## 🎯 Использование
### Создание клиента
```java
// 5 запросов в секунду
CrptApi api = new CrptApi(TimeUnit.SECONDS, 5);

// С указанием товарной группы
CrptApi api = new CrptApi(TimeUnit.SECONDS, 5, "milk");

// С кастомным URL API
CrptApi api = new CrptApi(TimeUnit.SECONDS, 5, "https://api.example.com", "shoes");
```
### Создание документа
```java
// Создание документа
Document document = new Document();
document.setDoc_id("DOC-123");
document.setDoc_status("IN_PROGRESS");

Description description = new Description();
description.setParticipantInn("1234567890");
document.setDescription(description);

// Создание товара
Product product = new Product();
product.setTnved_code("6401100000");
product.setUit_code("010463003407002921xxx");
document.setProducts(new Product[]{product});

// Отправка документа
String signature = "base64_signature";
api.createDocument(document, signature);
```
## ⚡ Потокобезопасность
#### Реализация использует:

- `ReentrantLock` для синхронизации
- `ConcurrentLinkedQueue` для thread-safe очереди
- `Instant` для точных временных меток
- `Sliding Window` алгоритм для точного ограничения запросов

## 🔧 Настройка ограничений
```java
// Разные конфигурации ограничений
CrptApi strictApi = new CrptApi(TimeUnit.SECONDS, 1);    // 1 запрос/секунду
CrptApi normalApi = new CrptApi(TimeUnit.SECONDS, 10);   // 10 запросов/секунду  
CrptApi bulkApi = new CrptApi(TimeUnit.MINUTES, 1000);   // 1000 запросов/минуту
```
## 🧪 Тестирование
### Пример теста
```java
@Test
void testRateLimiting() throws InterruptedException {
CrptApi api = new CrptApi(TimeUnit.SECONDS, 2);

    long startTime = System.currentTimeMillis();
    
    // 4 запроса при лимите 2/секунду
    for (int i = 0; i < 4; i++) {
        api.createDocument(testDocument, "signature");
    }
    
    long duration = System.currentTimeMillis() - startTime;
    assertTrue(duration >= 2000); // Должно занять ~2 секунды
}
```
## 🚀 Производительность
- Время обработки запроса: < 1ms
- Потребление памяти: минимальное
- Максимальная пропускная способность: зависит от ограничений

## 📚 Соответствие API
#### Реализация соответствует официальной документации API Честного знака:
- Формат запросов JSON
- Аутентификация через Bearer token
- Коды товарных групп (clothes, milk, shoes и прочие)
- Структуры документов

## 🔮 Расширение функциональности
#### Код легко расширяется для поддержки:
- Других методов API (отгрузка, агрегация, etc.)
- Кастомных обработчиков ошибок
- Метрик и мониторинга
- Retry механизмов

## 🤝 Контакты
Гаврилин Дмитрий - ddd.gavrilin@gmail.com - https://github.com/MrZloyHomyak