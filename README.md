# Web Quiz
Spring RESTful Web service with quizzes. It allows user to register, get all available quizzes, solve quiz by the id,
create and delete his own quizzes and get solved ones.

The idea and basic implementation
stages were taken from the [JetBrains Academy](https://hi.hyperskill.org/ "Hyperskill")

Last stage implementation was reviewed by Hyperskill Team.

### Endpoints
All endpoints except the first one return `401 Unauthorized` status code if user is not authorized. 

| URL | Method | Description |
| --- | --- | --- |
| /api/register | POST | Consumes `JSON` request body with user credentials. Status code is `200 OK` if user was registered successfully and consumed body is valid, `400 Bad Request` if user with specified email already exists or consumed body is invalid | 
| /api/quizzes | GET | Returns `JSON` with `Page` entity with some metadata and all available quizzes inside `content` section. May consume `page` request parameter to specify returned page. Status code is `200 OK` if user is authorized |
| /api/quizzes | POST | Consumes `JSON` request body with suggested quiz and saves it to database. Returns `JSON` with saved quiz without answers. Status code is `200 OK` if user is authorized and consumed body is valid, `400 Bad Request` if consumed body is invalid |
| /api/quizzes/completed | GET | Returns `JSON` with `Page` entity with some metadata and all completed by current user quizzes inside `content` section. May consume `page` request parameter to specify returned page. Status code is `200 OK` if user is authorized |
| /api/quizzes/{id} | GET | Returns `JSON` with specified quiz id. Status code is `200 OK` if user is authorized and quiz with specified id exists, `404 Not Found` if doesn't exist |
| /api/quizzes/{id} | DELETE | Deletes user's quiz with specified id from database. Status code is `204 No Content` if user is authorized and deleted successfully, `404 Not Found` if quiz doesn't exist, `403 Forbidden` if user is not the author of this quiz |
| /api/quizzes/{id}/solve | POST | Consumes `JSON` request body with suggested answers to specified quiz. Returns `JSON` with feedback. Status code is `200 OK` if user is authorized, quiz with specified id exists and consumed body is valid, `404 Not Found` if quiz doesn't exist, `400 Bad Request` if consumed body is invalid |

### Examples
- To register a new user
```json
{
  "email": "test@gmail.com",
  "password": "secret"
}
```
- To add a new quiz
```json
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```
- Response after adding a new quiz
```json
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```
- To submit a new answer
```json
{
  "answer": [0,2]
}
```
- Response after submitting an answer
```json
{
  "success":true,
  "feedback":"Congratulations, you're right!"
}
```
or 
```json
{
  "success":false,
  "feedback":"Wrong answer! Please, try again."
}
```