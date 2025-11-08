# Vector Graphics Editor

A full-stack web application for creating and editing vector graphics with real-time manipulation capabilities. Built with Spring Boot backend and modern JavaScript frontend, supporting extensible shape system and multiple export formats.

## 🚀 Features

- **Geometric Figure Drawing** - Line, Polyline, Circle, Ellipse, Rectangle, Square
- **Advanced Editing Tools** - Select, move, delete, and modify shapes
- **Dynamic Styling** - Customize fill color, stroke color, and stroke width
- **Multi-format Serialization** - Export/import canvas data in JSON and XML
- **Extensible Architecture** - Plugin system for adding custom shapes
- **Responsive UI** - Clean interface built with HTML5 Canvas

## 🛠 Technology Stack

### Backend
- **Java 17** + **Spring Boot** (MVC, Services, Controllers)
- **Design Patterns**: Factory Method, Strategy, DTO
- **RESTful APIs** with JSON/XML serialization
- **Maven** for dependency management

### Frontend  
- **Vanilla JavaScript** with ES6+ features
- **HTML5 Canvas** for high-performance rendering
- **Modular Architecture** with tool-based system
- **CSS3** for modern UI styling

## 🏗 Architecture
Client Layer (Browser)
↓ HTTP/REST
Spring Boot Controllers
↓ Service Layer
Business Logic & Shape Processing
↓ Data Layer
Shape Factories → Drawing Strategies → Serialization
### Core Components

**Backend Services:**
- `ShapeController` - REST endpoints for shape operations
- `ShapeService` - Business logic and data management
- `ShapePluginService` - Dynamic shape loading system
- Factory classes for each shape type

**Frontend Modules:**
- Tool classes for drawing and manipulation
- Shape-specific tools (LineTool, CircleTool, etc.)
- Canvas rendering engine
- Properties editing system

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Modern web browser

### Installation & Run

1. **Clone and build**
   ```bash
   git clone https://github.com/EgorBars/SpringBoot.git
   cd SpringBoot
   mvn clean install

2. **Run the application**
    ```
    mvn spring-boot:run

3. **Access the editor**

    Open http://localhost:8080 in your browser
