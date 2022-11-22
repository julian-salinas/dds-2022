package presentacion;

import spark.Spark;

public class Server {
  public static void main(String[] args) {
    Bootstrap.init();
    Spark.port(5000);
    Router.configure();
  }

}
