package io.github.bluemarlin.launcher;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public class UrlReader {
	
	private final Consumer<String> callback;
	
	public UrlReader(Consumer<String> callback) {
		this.callback = callback;
	}

	public void download(String url) throws IOException {
		InputStream in;
		try {
			in = new URL( url ).openStream();
			stream(in);
		} catch (MalformedURLException e) {
			// shouldn't happen
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	// convert InputStream to String
	private void stream(InputStream is) throws IOException {

		BufferedReader br = null;

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				callback.accept(line);
			}

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 
}
