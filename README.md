# ИКМ Вавилина Анастасия ИТ-78
# Структура проекта
src/
└── main/
    ├── java/
    │   └── com/
    │       └── crm/
    │           ├── CrmApplication.java
    │           ├── controller/
    │           │   ├── ClientController.java
    │           │   ├── DealController.java
    │           │   ├── HomeController.java
    │           │   └── UserController.java
    │           ├── entity/
    │           │   ├── Client.java
    │           │   ├── Deal.java
    │           │   └── User.java
    │           ├── repository/
    │           │   ├── ClientRepository.java
    │           │   ├── DealRepository.java
    │           │   └── UserRepository.java
    │           └── service/
    │               └── (сервисные классы)
    └── resources/
        ├── static/
        │   └── css/
        │       └── style.css
        ├── templates/
        │   ├── add-client.html
        │   ├── add-deal.html
        │   ├── add-user.html
        │   ├── clients.html
        │   ├── dashboard.html
        │   ├── deals.html
        │   ├── edit-client.html
        │   ├── edit-deal.html
        │   ├── edit-user.html
        │   ├── users.html
        │   ├── view-client.html
        │   ├── view-deal.html
        │   └── view-user.html
        └── application.properties
