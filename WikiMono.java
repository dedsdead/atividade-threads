import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;

public class WikiMono {
    public static void main(String[] args) {        
        System.out.println("Executando com uma Thread:");
        LocalTime inicio = LocalTime.now();
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
            System.out.println("Thread parada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LocalTime fim = LocalTime.now();
        System.out.println("Tempo de execução: " + Duration.between(inicio, fim).toMillis() + " ms");
    }
    public static String verificaPaginaWikiExistente(String url) {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                return url + " - existe";
            } else if (conn.getResponseCode() == 404) {
                return url + " - não existe";
            } else {
                return url + " - erro";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url + " - erro: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return url + " - erro: " + e.getMessage();
        }
    }
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                String url = "https://pt.wikipedia.org/wiki/" + i;
                System.out.println(url);
                String resultado = verificaPaginaWikiExistente(url);
                System.out.println(resultado);
            }
        }
    };
}
