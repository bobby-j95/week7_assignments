import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

//Created by Robert Johns
public class PhoneBook {

	private FastScanner in = new FastScanner();
	// Keep list of all existing (i.e. not deleted yet) contacts.
	private List<List<Contact>> contacts = new ArrayList<>();

	public static void main(String[] args) {
		new PhoneBook().processQueries();
	}

	private Query readQuery() {
		String type = in.next();
		int number = in.nextInt();
		if (type.equals("add")) {
			String name = in.next();
			return new Query(type, name, number);
		} else {
			return new Query(type, number);
		}
	}

	private void writeResponse(String response) {
		System.out.println(response);
	}

	private void processQuery(Query query) {
		int hash = query.number % 100000;
		if (query.type.equals("add")) {
			// if we already have contact with such number,
			// we should rewrite contact's name
			boolean wasFound = false;
			// if contact equals the hash input the name and number into the loop
			for (Contact contact : contacts.get(hash))
				if (contact.number == query.number) {
					contact.name = query.name;
					wasFound = true;
					break;
				}
			// otherwise, just add it
			if (!wasFound)
				contacts.get(hash).add(new Contact(query.name, query.number));
			// searches contact based off of hash value and deletes contact
		} else if (query.type.equals("del")) {
			for (Contact contact : contacts.get(hash))
				if (contact.number == query.number) {
					contacts.get(hash).remove(contact);
					break;
				}
		} else {
			//if contact number is what is trying to be found then the response is that contact info
			String response = "not found";
			for (Contact contact : contacts.get(hash))
				if (contact.number == query.number) {
					response = contact.name;
					break;
				}
			writeResponse(response);
		}
	}

	public void processQueries() {
		int queryCount = in.nextInt();
		//adds contacts for every space and get a unique identifier each time
		for (int i = 0; i < 100000; i++) {
			contacts.add(new ArrayList<Contact>());
		}
		for (int i = 0; i < queryCount; ++i) {
			processQuery(readQuery());
		}
	}

	static class Contact {
		String name;
		int number;

		public Contact(String name, int number) {
			this.name = name;
			this.number = number;
		}
	}

	static class Query {
		String type;
		String name;
		int number;

		public Query(String type, String name, int number) {
			this.type = type;
			this.name = name;
			this.number = number;
		}

		public Query(String type, int number) {
			this.type = type;
			this.number = number;
		}
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}
	}
}