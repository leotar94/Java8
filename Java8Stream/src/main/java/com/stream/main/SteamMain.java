package com.stream.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Hashtable;

import com.stream.Album;

public class SteamMain {

	public static void main(String[] args) {
		List<Album> albums = new ArrayList<Album>();
		albums.add(new Album("Pink Floyd", "The Division Bell", 1994,"Cluster One",
		         "What Do You Want from Me",
		         "Poles Apart",
		         "Marooned",
		         "A Great Day for Freedom",
		         "Wearing the Inside Out",
		         "Take It Back",
		         "Coming Back to Life",
		         "Keep Talking",
		         "Lost for Words",
		         "High Hopes"));
		for(Album album : albums) {
			if(album.getAnno()<2000)
				System.out.println(album.getAutore());
		}
		albums.stream().filter(album ->album.getAnno()<2000)
				.map(Album::getAutore)
				.forEach(System.out::println);
		/*
		 * Ma cos�� allora uno stream? Nessuna parentela con
		 * InputStream o OutputStream. Uno stream � un�interfaccia
		 *  che restituisce un flusso di dati finito o infinito
		 *  su cui � possibile fare operazioni di filtro,
		 *  mappa e riduzione (leggiamo aggregazione per il momento).
		 *  Pu� operare in modo sequenziale (che � il default) oppure
		 *  a richiesta in modo parallelo. Possiamo vedere lo stream
		 *  come una pipeline di operazioni su dati che, come ogni
		 *  tubatura che si rispetti, ha una sorgente, una serie
		 *  di pezzi intermedi e una destinazione.
		 */

		//OPERAZIONI INTERMEDIE
		Stream<Album> stream1=albums.stream();
		Stream<Album> stream2= stream1.filter(album->album.getAnno()<2000);
		/*
		 * Come conferma la documentazione, questa operazione (filter)
		 * restituisce uno stream che soddisfa un Predicate
		 * passato come parametro. Un Predicate (predicato in italiano)
		 * � una interfaccia funzionale (come poteva non esserlo,
		 * abbiamo passato una lambda ) che ha un unico compito:
		 * quello di rispondere vero o falso nel metodo test.
		 * Gli oggetti che soddisfano il test andranno avanti
		 * nella pipeline, gli altri saranno scartati.
		 */
		Stream<String> stream3=stream2.map(Album::getAutore);
		/*
		 * Anche map torna uno stream, per�, a differenza di filter,
		 * ci permette di cambiare il tipo di oggetti nello stream
		 * passando una lambda che sia compatibile con l�interfaccia
		 * funzionale Function. Questa interfaccia richiede di
		 * implementare il metodo apply che prende un oggetto di
		 * tipo T, quello delle stream corrente e torna un oggetto
		 * di tipo R. Nell�esempio di sopra, ad uno stream di Album
		 * passiamo una lambda che dato un album ritorna il suo titolo.
		 */
		stream3.forEach(System.out::println);

		//OPERAZIONE TERMINALE
		List<String> autori= albums.stream().filter(album-> album.getAnno()<2000)
				.map(Album::getAutore).collect(Collectors.toList());
		double avgCanzoni = albums.stream()
				.mapToInt(album->album.getCanzoni().size())
				.average().getAsDouble();
		System.out.println(avgCanzoni);
		/*
		 * Nel primo esempio, al posto di applicare un forEach,
		 * tramite il metodo collect riversiamo il risultato dello
		 * stream in una lista. Nel secondo, torniamo per ogni
		 * album il numero di canzoni e ne calcoliamo la media
		 * (come double). Notiamo una cosa: il metodo average non �
		 * disponibile per qualsiasi stream, ma solo per quelli numerici,
		 * per cui non possiamo usare semplicemente map ma dobbiamo convertire
		 * lo stream in un IntStream tramite mapToInt.
		 */
		//in JAVA7
		int limit = 10;
		List<String> songs = new ArrayList<>();
		for (Album album : albums) {
			if (album.getYear() < 2000) {
				songs.addAll(album.getSongs());
			}
		}
		Collections.sort(songs);
		for (int i = 0; i < limit; i++) {
			System.out.println(songs.get(i));
		}

		//in JAVA8
		albums.stream()
	    .filter(album -> album.getAnno() < 2000)
	    .flatMap(album -> album.getCanzoni().stream())
	    .sorted()
	    .limit(5)
	    .forEach(System.out::println);
		//se vogliamo aggiungere filtri
		//JAVA7
		int limit = 10;
		Set<String> songs = new TreeSet<>();
		for (Album album : albums) {
			if (album.getYear() < 2000) {
				for (String song : album.getSongs()) {
					if (song.startsWith("D"))
						songs.add(song);
				}
			}
		}
		int temp = 0;
		for (String s : songs) {
			System.out.println(s);
			temp++;
			if (temp == limit)
				break;
		}
		//JAVA 8
		albums.stream()
	    .filter(album -> album.getAnno() < 2000)
	    .flatMap(album -> album.getCanzoni().stream().filter(s -> s.startsWith("A"))) //case sensitive
	    .distinct()
	    .sorted()
	    .limit(10)
	    .forEach(System.out::println);

		//come fare in un ambito di sviluppo multithread?, non è
		//un problema... basta utilizzare semplicemente parallelStream.
		albums.parallelStream()
				.flatMap(album->album.getCanzoni().stream())
				.count();
		//la notazione rimane la stessa, cambia solo parallel stream
		/*
		NOTA BENE, non bisogna usare il foreach in parallelo, potrebbe
		avviare un'eccezione o potebbe estituie risultati diversi
		dovuti all'esecuzione multithread
		soluzione? Buttiamo lo stream in una lista o map...
		 */



	}

}
