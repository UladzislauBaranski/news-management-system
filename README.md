news-managment-system - web-приложение для выполнения crud операций над новостями и комментариями.
Представляет rest api интерфейс.

Endpoints:

News Page

get: /news - список всех новостей;

get: news/by{id}- новость, где “id” – идентификатор новости;

get: news/{id}?pageNumber=0&pageSize=10 - новость со списком комментариев (постранично), где “id” – идентификатор новости, pageNumber- номер страницы(начинается с нуля), pageSize- количество комментариев на странице;

get: news/by-page?pageNumber=0&pageSize=10 – список новостей постранично, где pageNumber- номер страницы(начинается с нуля), pageSize- количество комментариев на странице;

post: /news - для добавления новости;

put: /news - для изменения новости;

delete: /news - для удаления новости;

delete: / news/{id}- для удаления новости по id.

Comment Page

get: /comment - список всех комментариев;

get: comment/{id}?pageNumber=0&pageSize=10 - список комментариев постранично с привязкой к новости, где id- идентификатор новости, pageNumber- номер страницы(начинается с нуля), pageSize- количество комментариев на странице;

post: /comment/{id} - для добавления комментария к новости с идентификатором id;

put: /comment/{id} - для изменения комментария у новости с идентификатором id;

delete: /comment - для удаления комментария;

delete: /comment/{id}- для удаления комментария по id.


News And Comment Page

get: /news-comment/{id} - список всех новостей и комментариев;

get: /news-comment/by-news-date - поиск всех новостей и комментариев по точному совпадению даты из новостей;

get: /news-comment/by-news-title - поиск всех новостей и комментариев по любому совпадению заголовка из новостей;

get: /news-comment/by-news-text - поиск всех новостей и комментариев по любому совпадению текста из новостей;

get: /news-comment/by-comment-date - поиск всех новостей и комментариев по точному совпадению даты из комментариев;

get: /news-comment/by-comment-text - поиск всех новостей и комментариев по любому совпадению текста из комментариев;

get: /news-comment/by-comment-username - поиск всех новостей и комментариев по любому совпадению username из комментариев;


Для переключения между БД(из встроенной на внешнюю) нужно зайти в “edit configure” и прописать в поле “Active profile” - postgresql.
