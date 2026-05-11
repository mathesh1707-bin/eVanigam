# eVanigam

A modern full-stack e-commerce web application built with React, Spring Boot, and MySQL featuring authentication, product management, cart functionality, and order tracking.

## 🚀 Features

- User Authentication
- Role-Based Access (Admin/User)
- Product Catalog
- Add to Cart
- Order Management
- REST API Integration
- Responsive UI

## 🛠️ Tech Stack

**Frontend:** React.js, CSS, JavaScript  
**Backend:** Spring Boot, Java  
**Database:** MySQL  
**Authentication:** JWT

## ⚙️ Installation

```bash
git clone <repo-link>
cd eVanigam
```

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

## 🔐 Environment Variables

Create an `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/evanigam
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_secret_key
```

## 📌 Future Enhancements

- Online Payments
- Wishlist
- Product Reviews
- Analytics Dashboard

## 👨‍💻 Author

Mathesh Subramanian
