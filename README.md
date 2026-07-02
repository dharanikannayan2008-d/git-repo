# Smart Leftover Food Sharing System

## Project Overview
A full-stack web application built with Spring Boot that connects food donors (restaurants, hotels, bakeries, supermarkets, households) with NGOs, charities, volunteers, and orphanages to reduce food waste and combat hunger.

## Technology Stack
- **Backend:** Spring Boot 3.1.5, Spring Data JPA
- **Frontend:** HTML, CSS, JavaScript, Bootstrap 5, Thymeleaf
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **Java Version:** 17

## Project Structure
```
src/main/java/com/foodsharing
├── controller/           # REST/Web controllers
├── service/              # Business logic
├── repository/           # Data access layer
├── entity/               # JPA entities
├── dto/                  # Data transfer objects
└── FoodSharingApplication.java

src/main/resources
├── templates/            # Thymeleaf HTML templates
├── static/               # CSS, JS, images
└── application.properties # Configuration
```

## Features

### User Roles
1. **Donor** - Can post surplus food, view requests, approve/reject offers
2. **Receiver** - Can browse available food, make requests, track requests
3. **Admin** - Can manage users, donations, and view statistics

### Core Functionality
- ✅ User Authentication & Authorization
- ✅ Food Donation Management (CRUD)
- ✅ Food Request Workflow
- ✅ Search & Filter by Location/Category
- ✅ Image Upload Support
- ✅ Real-time Status Tracking
- ✅ Responsive UI Design
- ✅ Role-based Access Control

## Setup Instructions

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/dharanikannayan2008-d/git-repo.git
   cd git-repo
   ```

2. **Create Database**
   ```bash
   mysql -u root -p < database/schema.sql
   ```

3. **Configure Database Connection**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/food_sharing_db
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

4. **Build the Project**
   ```bash
   mvn clean install
   ```

5. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the Application**
   Open your browser and go to: `http://localhost:8080`

## Usage Guide

### For Donors
1. Register as a Donor
2. Go to Dashboard → Add Food
3. Fill in food details, upload image, set expiry time
4. View requests in My Donations
5. Approve or reject requests
6. Track food collection

### For Receivers
1. Register as NGO/Volunteer/Organization
2. Go to Dashboard → Browse Available Food
3. Search by location or category
4. View food details and request it
5. Track request status in My Requests
6. Mark as collected after pickup

### For Admins
1. Access Admin Dashboard
2. View platform statistics
3. Manage users and donations
4. Remove expired listings
5. Generate reports

## Database Schema

### Tables
- **users** - User accounts with roles
- **food_donations** - Food listings
- **food_requests** - Request tracking
- **admin** - Admin accounts

## API Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - User login
- `GET /auth/logout` - User logout

### Donor
- `GET /donor/dashboard` - View dashboard
- `POST /donor/add-food` - Add food donation
- `GET /donor/my-donations` - View donations
- `PUT /donor/edit-food/{id}` - Edit donation
- `DELETE /donor/delete-food/{id}` - Delete donation

### Receiver
- `GET /receiver/dashboard` - View dashboard
- `GET /receiver/available-food` - Browse food
- `GET /receiver/food-details/{id}` - View details
- `POST /receiver/request-food/{id}` - Request food
- `GET /receiver/my-requests` - View requests

### Admin
- `GET /admin/dashboard` - Admin dashboard

## Sample Data

You can add sample data using SQL:
```sql
INSERT INTO users (first_name, last_name, email, password, phone, city, role, is_active, is_verified)
VALUES ('John', 'Donor', 'john@donor.com', 'pass123', '9876543210', 'Delhi', 'DONOR', 1, 1);

INSERT INTO users (first_name, last_name, email, password, phone, city, role, organization_name, organization_type, is_active)
VALUES ('NGO', 'Receiver', 'ngo@receiver.com', 'pass123', '9123456789', 'Delhi', 'RECEIVER', 'Help Foundation', 'NGO', 1);
```

## Folder Structure for File Uploads
```
project-root/
├── uploads/              # Food images storage
├── src/
├── database/
└── pom.xml
```

## Error Handling
- Validation messages for all forms
- Database constraint checks
- User-friendly error pages
- Session management

## Security Features
- Password storage (encrypted in production)
- Role-based access control
- Session validation
- Input validation
- CSRF protection

## Future Enhancements
- Google Maps integration for location
- Email/SMS notifications
- QR code verification
- AI-based food freshness detection
- Mobile application
- Payment integration
- Rating system
- Analytics dashboard

## Testing

### Test Cases
- User registration and login
- Food donation creation and editing
- Food request workflow
- Search and filter functionality
- Status updates
- Admin operations

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check credentials in application.properties
   - Ensure food_sharing_db exists

2. **Port Already in Use**
   ```bash
   # Change port in application.properties
   server.port=8081
   ```

3. **File Upload Issues**
   - Create `uploads/` folder in project root
   - Check file permissions
   - Verify max file size in application.properties

## Contributors
- Dharani Kannayan

## License
MIT License

## Support
For issues and questions, please create an issue in the repository.

---

**Last Updated:** July 2, 2026
