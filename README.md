# library

## Project Structure

### Backend

The backend directory of your project is typically where all the server-side code resides. This includes all the logic for handling HTTP requests, interacting with databases, and managing user authentication.

In a Java Spring Boot project managed with Maven, the backend directory structure might look something like this:

- `src/`: This is the main directory where all your Java source code resides.
    - `main/`: This directory contains the application's main source code.
        - `java/`: This directory contains all your Java classes, typically organized in packages.
            - `com/example/myapp/`: This directory is an example of a package directory. It would contain various subdirectories for different components of your application, such as controllers, services, repositories, and models.
        - `resources/`: This directory contains resources that will be included in the final build. This typically includes application properties files, static resources, and templates.
    - `test/`: This directory contains all your test code, typically mirroring the structure of your `main/` directory.
- `pom.xml`: This is the Project Object Model (POM) file for Maven. It includes information about the project and configuration details used by Maven to build the project.
- `target/`: This directory is generated when you build your project. It contains all the compiled .class files and the final .jar or .war file.

### Frontend

The frontend directory of your project is where all the client-side code resides. This includes all the logic for rendering the user interface and handling user interactions.

In a React project managed with npm, the frontend directory structure might look something like this:

- `src/`: This directory contains the source code for the project.
    - `components/`: This directory contains the React components for the frontend.
    - `context/`: This directory contains the global state management using Jotai.
    - `assets/`: This directory contains static files like images.
    - `hook/`: This directory contains custom React hooks.
- `public/`: This directory contains public assets and the HTML file.
- `package.json`: This is the npm configuration file for the frontend.


## Project Setup

### Frontend Setup

The frontend of this project is a React application managed with npm. To set up the frontend, navigate to the `frontend` directory in your terminal and run the following command:

```bash
npm install
```

This will install all the necessary dependencies for the frontend.

### Backend Setup

The backend of this project is a Spring Boot application managed with Maven. To set up the backend, navigate to the project directory in your terminal and run the following command:

```bash
mvn clean install
```

This will install all the necessary dependencies for the backend.

## Running the Project

### Running the Backend

The backend of this project is a Spring Boot application managed with Maven. To run the backend, navigate to the project directory in your terminal and run the following command:

```bash
mvn spring-boot:run
```

This will start the Spring Boot application and make it accessible at `http://localhost:8080`.

### Running the Frontend

The frontend of this project is a React application managed with npm. To run the frontend, navigate to the `frontend` directory in your terminal and run the following command:

```bash
npm run dev
```

This will start the React development server and make the frontend accessible at `http://localhost:5173`.