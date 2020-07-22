package neuralnetwork;

import java.time.LocalDateTime;

public class Trace {

	public static void trace(String line, Object... puts) {
		System.out.print("TRACE | " + LocalDateTime.now().toString() + " |");
		if (line.endsWith("{}")) {
			line+=" ";
		}
		String[] fragments = line.split("\\{\\}");
 
		if (puts.length==0) {
			System.out.print(line);
		} else if (puts.length == fragments.length-1) {
			for (int i = 0; i < fragments.length; i++) {
				System.out.print(fragments[i]);
				if (i<puts.length) {
				System.out.print(puts[i] == null ? "NULL" : puts[i].toString());
				}
			}
		} else {
			throw new RuntimeException("Hibás objektum és  {} szám.");
		}
		System.out.println();
	}

	public static void warn(String line, Object... puts) {
		System.out.print("WARN | " + LocalDateTime.now().toString() + " |");
		if (line.endsWith("{}")) {
			line+=" ";
		}
		String[] fragments = line.split("\\{\\}");
 
		if (puts.length==0) {
			System.out.print(line);
		} else if (puts.length == fragments.length-1) {
			for (int i = 0; i < fragments.length; i++) {
				System.out.print(fragments[i]);
				if (i<puts.length) {
				System.out.print(puts[i] == null ? "NULL" : puts[i].toString());
				}
			}
		} else {
			throw new RuntimeException("Hibás objektum és  {} szám.");
		}
		System.out.println();
	}

}
