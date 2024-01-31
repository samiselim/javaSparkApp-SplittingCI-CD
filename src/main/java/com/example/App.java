import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExtendedSparkApp implements SparkApplication {

    public static void main(String[] args) {
        new ExtendedSparkApp().init();
    }

    @Override
    public void init() {
        // Set up static files location
        staticFileLocation("/public");

        // Define routes
        get("/", (req, res) -> "Final Project 26.12.2023!!!");

        get("/hello", (req, res) -> "Hello, World!");

        post("/user/:id", (req, res) -> "User ID: " + req.params(":id"));

        get("/template", (req, res) -> {
            // Render template using Velocity Template Engine
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello, Velocity!");
            return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/hello.vtl"));
        });

        before((req, res) -> {
            // Middleware: Perform actions before each request
            System.out.println("Received request at: " + LocalDateTime.now());
        });

        // Database Integration example
        String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
        String DB_USER = "username";
        String DB_PASSWORD = "password";
        Sql2o sql2o = new Sql2o(DB_URL, DB_USER, DB_PASSWORD);

        get("/users", (req, res) -> {
            try (Connection conn = sql2o.open()) {
                // Retrieve users from the database
                // ...
                return "List of users";
            }
        });

        // Exception handling
        exception(Exception.class, (e, req, res) -> {
            // Handle exceptions
            res.status(500);
            res.body("Internal Server Error");
        });
    }
}

