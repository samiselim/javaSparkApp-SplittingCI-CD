import spark.servlet.SparkApplication;

import static spark.Spark.get;

public class App implements SparkApplication {
	public static void main(String[] args) {
		new App().init();
	}

	@Override
	public void init() {
		get("/", (req, res) -> "Final Project 26.12.2023!!!");
	}
}