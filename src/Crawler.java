import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Crawler {
	private Set<String> traversedURLs;
	private Queue<String> remainingURLs;
	private Set<String> mails;

	public Crawler() {
		traversedURLs = new HashSet<String>();
		remainingURLs = new LinkedList<String>();
		mails = new HashSet<String>();
	}

	public synchronized String getURLFromRemaining() {
		String url = remainingURLs.poll();
		if (!traversedURLs.contains(url)) {
			traversedURLs.add(url);
			return url;
		}
		return null;
	}

	public synchronized void addURL(String URL) {
		if (!remainingURLs.contains(URL))
			remainingURLs.add(URL);
	}

	public void printAll() {
		System.out.println();
		traversedURLs.forEach(url -> { System.out.println(url); });
		System.out.println();
		mails.forEach(mail -> { System.out.println(mail); });

		System.out.println("\nSites traversed: " + traversedURLs.size());
		System.out.println("Mail addresses: " + mails.size());
		System.out.println("Sites remaining: " + remainingURLs.size());
	}

	public synchronized boolean isDone() {
		return traversedURLs.size() >= 100 ? true : false;
	}

	public synchronized void addMail(String mail) {
		if (!mails.contains(mail))
			mails.add(mail);
	}

	public synchronized int getTraversedSize() {
		return traversedURLs.size();
	}
}
