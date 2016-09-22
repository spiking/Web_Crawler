import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws MalformedURLException {

		long start = System.currentTimeMillis();

		Crawler crawler = new Crawler();
		URL url = new URL("http://cs.lth.se/eda095");
		ExecutorService executor = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 10; i++) {
			Processor process = new Processor(crawler, url);
			executor.submit(process);
			System.out.println("Thread started!");
		}

		crawler.addURL(url.toString()); // start URL
		executor.shutdown();

		try {
			executor.awaitTermination(60, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long finished = System.currentTimeMillis();
		crawler.printAll();
		System.out.println("Time: " + (finished - start) / (double) 1000);
	}
}
