import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Corrida {
    static List<Integer> resultados = new ArrayList<>();
    static int indiceVencedor = 0;
    
    public static void main(String[] args) {
        int threadsParadas = 0;
        
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(getRunnable(i));
            threads.add(thread);
            thread.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
                System.out.println("Thread " + threads.indexOf(t) + " parada");
                threadsParadas++;
                if (threadsParadas == 2) {
                    Optional<Integer> exists = resultados.stream().filter(passos -> passos < resultados.get(indiceVencedor)).findFirst();
                    if (exists.isPresent()) {
                        indiceVencedor = exists.get();
                    }
                    System.out.println("A Thread " + (indiceVencedor + 1) + " venceu com: " + resultados.get(indiceVencedor) + " passos");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static int gerarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(4) + 1;
    }
    public static int realizarCorrida(int idDaThread) {
        int posicaoAtual = 0;
        int totalPassos = 0;

        System.out.println("Thread " + idDaThread + " - iniciando");

        while (posicaoAtual < 50) {
            int passos = gerarNumeroAleatorio();
            posicaoAtual += passos;
            totalPassos += passos;

            System.out.println("Vez da Thread " + idDaThread);
            System.out.println("Numero sorteado: " + passos);
            System.out.println("Thread " + idDaThread + " andou " + passos + " casas");
            System.out.println("Posição atual da Thread " + idDaThread + ": " + posicaoAtual);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        System.out.println("Thread " + idDaThread + " - chegou a posição 50! Total de passos: " + totalPassos);
        return totalPassos;
    }
    private static Runnable getRunnable(int i){
        return new Runnable() {
            @Override
            public void run() {
                int totalPassos = realizarCorrida(i);
                resultados.add(totalPassos);
            }
        };
    }
}
