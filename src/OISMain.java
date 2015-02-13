import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/*	Main class
 *  Create OISGUI and that is it
 */
public class OISMain {

	public static void main(String[] args) {
		OISGUI gui = new OISGUI();
	}

	// http://stackoverflow.com/questions/1139547/detect-internet-connection-using-java

	public static boolean isInternetReachable() {
		try {
			// make a URL to a known source
			URL url = new URL("http://www.google.com");

			// open a connection to that source
			HttpURLConnection urlConnect = (HttpURLConnection) url
					.openConnection();

			// trying to retrieve data from the source. If there
			// is no connection, this line will fail
			Object objData = urlConnect.getContent();

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
