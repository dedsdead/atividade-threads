import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class WikiMulti {
    public static void main(String[] args) {        
        System.out.println("Executando com varias Threads:");
        LocalTime inicio = LocalTime.now();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            Thread t = new Thread(getRunnable(i));
            threads.add(t);
            t.start();
            System.out.println("Thread " + threads.indexOf(t) + "iniciada");
        }
        for (Thread t : threads) {
            try {
                t.join();
                System.out.println("Thread " + threads.indexOf(t) + "parada");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    private static Runnable getRunnable(int i){
        return new Runnable() {
            @Override
            public void run() {
                String url = "https://pt.wikipedia.org/wiki/" + i;
                System.out.println(url);
                String resultado = verificaPaginaWikiExistente(url);
                System.out.println(resultado);
            }
        };
    }
}
