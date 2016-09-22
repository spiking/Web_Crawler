import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Processor extends Thread {
	private Crawler crawler;
	private URL url;
	private URLConnection urlConn;

	public Processor(Crawler crawler, URL url) {
		this.crawler = crawler;
		this.url = url;
	}

	public void run() {

		while (!crawler.isDone()) {
			String nextURL = crawler.getURLFromRemaining();
			if (nextURL != null) {

				try {
					url = new URL(nextURL);
					urlConn = url.openConnection();

					if (urlConn.getContentType() != null && urlConn.getContentType().contains("text/html")) {

						InputStream in = url.openStream();
						Document document = Jsoup.parse(in, "UTF-8", url.toString());
						Elements links = document.select("a[href]");

						System.out.println("\n" + this.getName() + " visited webpage at " + url);
						System.out.println("Total traversed " + crawler.getTraversedSize() + " links");

						links.forEach(link -> {
							String absHref = link.attr("abs:href");
							if (link.toString().contains("mailto"))
								crawler.addMail(link.absUrl("href"));
							else
								crawler.addURL(link.absUrl("href"));

						});

					} else {
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
