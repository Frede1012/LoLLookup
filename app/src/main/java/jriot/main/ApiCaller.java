package jriot.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.softstew.util.Logger;

public class ApiCaller {

	int connectTimeout;
	int readTimeout;

	public ApiCaller(int connectTimeout, int readTimeout) {
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	public String request(String URL) throws JRiotException {

		try {
			String requestURL = URL;

			URL url = new URL(requestURL);

			Logger.logDebug("API REQUEST: " + URL);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			// DEFAULT TIMEOUTS = 20 sec
			connection.setConnectTimeout(connectTimeout * 1000);
			connection.setReadTimeout(readTimeout * 1000);
			// connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setInstanceFollowRedirects(false);

			Logger.logDebug("SERVER RESPONSE CODE: "
					+ connection.getResponseCode());

			if (connection.getResponseCode() != 200) {
				// return "[" + connection.getResponseCode() + "]";
				throw new JRiotException(connection.getResponseCode());
			}

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();

			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}

			connection.disconnect();
			return response.toString();

		} catch (JRiotException je) {
			je.printStackTrace();
			throw je;
		} catch (Exception e) {
			e.printStackTrace();
			throw new JRiotException(1337);
		}

	}
}
