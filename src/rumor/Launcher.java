package rumor;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
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

    @Parameter(names = {"--trials", "-t"}, description = "Number of trials.")
    private int trials = 1;

    @Parameter(names = "-n", description = "Population size.")
    private int n = 10000;

    @Parameter(names = "--record", description = "Plot state images.")
    private boolean record = false;

    /** The number of threads to use. */
    private static final int NTHREADS =
        Runtime.getRuntime().availableProcessors();

    /**
     * Run a number of trials based on input arguments.
     * @param args  command line arguments
     */
    public static void main(String[] args) {
        try {
            /* Parse command line arguments. */
            Launcher params = new Launcher();
            new JCommander(params, args);

            /* Launch each rumer. */
            ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
            CompletionService<Rumor> service =
                new ExecutorCompletionService<Rumor>(executor);
            for (int i = 0; i < params.trials; i++) {
                Rumor rumor = new Rumor(params.n);
                if (params.record) {
                    String name = String.format("rumor-%04d-", i);
                    rumor.addObserver(new RumorDrawer(name, 100, 4));
                }
                service.submit(rumor);
            }

            /* Gather up and print the results. */
            for (int i = 0; i < params.trials; i++) {
                System.out.println(service.take().get());
            }
            executor.shutdown();
        } catch (NullPointerException e) {
            usage();
        } catch (ArrayIndexOutOfBoundsException e) {
            usage();
        } catch (InterruptedException e) {
            System.out.println(e);
            System.exit(-1);
        } catch (ParameterException e) {
            System.out.println("error: " + e.getMessage());
            usage();
        } catch (ExecutionException e) {
            System.out.println(e);
            usage();
        }
    }

    /**
     * Print usage info and exit with failure.
     */
    private static void usage() {
        new JCommander(new Launcher()).usage();
        System.exit(-1);
    }

    /**
     * Hidden constructor.
     */
    private Launcher() {
    }
}
