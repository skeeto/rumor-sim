package rumor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parse command line arguments and execute a number of rumor models
 * in parallel.
 */
public class Launcher {

    /** The number of threads to use. */
    private static final int NTHREADS =
        Runtime.getRuntime().availableProcessors();

    /**
     * Run a number of trials based on input arguments.
     * @param args  command line arguments
     */
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);
            int trials = args.length > 1 ? Integer.parseInt(args[1]) : 1;
            ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
            CompletionService<Rumor> service =
                new ExecutorCompletionService<Rumor>(executor);
            for (int i = 0; i < trials; i++) {
                service.submit(new Rumor(n));
            }
            for (int i = 0; i < trials; i++) {
                System.out.println(service.take().get());
            }
            executor.shutdown();
        } catch (NumberFormatException e) {
            usage();
        } catch (NullPointerException e) {
            usage();
        } catch (ArrayIndexOutOfBoundsException e) {
            usage();
        } catch (InterruptedException e) {
            System.out.println(e);
            System.exit(-1);
        } catch (ExecutionException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }

    /**
     * Print usage info and exit with failure.
     */
    private static void usage() {
        System.out.println("Usage: rumor n [trials]");
        System.exit(-1);
    }

    /**
     * Hidden constructor.
     */
    private Launcher() {
    }
}
