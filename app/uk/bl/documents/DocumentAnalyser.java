/**
 * 
 */
package uk.bl.documents;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Alert;
import models.Document;
import models.WatchedTarget;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;

import play.Logger;
import play.libs.F.Function0;

import com.avaje.ebean.Ebean;

import controllers.Documents;
import controllers.WaybackController;
import eu.scape_project.bitwiser.utils.FuzzyHash;
import eu.scape_project.bitwiser.utils.SSDeep;

/**
 * 
 * Cleaned up and extended document analyser.
 * 
 * Relies on content being present in Wayback for analysis.
 * 
 * @author andy
 *
 */
public class DocumentAnalyser {

	private static String waybackUrl = WaybackController.getWaybackEndpoint();
	
	public DocumentAnalyser() {
	}

	public void extractMetadata(Document document) {
		// Get the binary hash
		try {
			byte[] digest = DigestUtils.sha256(getWaybackInputStream(document.documentUrl, document.waybackTimestamp));
			document.sha256Hash = String.format("%064x", new java.math.BigInteger(1, digest));
			Logger.info("Recorded sha256Hash "+document.sha256Hash+" for "+document.documentUrl);
		} catch (Exception e) {
			Logger.error("Failure while SHA256 hashing "+document.documentUrl);
			e.printStackTrace();
		}
		
		// Extended metadata and text:
		String text = null;
		Logger.info("Running document parser process...");
		try {
			// Use Tika on it:
			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			BodyContentHandler handler = new BodyContentHandler();
			try {
				parser.parse(getWaybackInputStream(document.documentUrl, document.waybackTimestamp), handler, metadata);
			} catch( Exception e) {
				Logger.error("Exception while running Tika on "+document.documentUrl);
				e.printStackTrace();
			}
			// Pull in the text:
			text = handler.toString();
			// Use anything we find:
			if( StringUtils.isBlank(document.title) && 
					StringUtils.isNotBlank(metadata.get(TikaCoreProperties.TITLE)) ) {
				document.title = metadata.get(TikaCoreProperties.TITLE);
			}
			if( metadata.get(TikaCoreProperties.CREATED) != null ) {
				Date created = metadata.getDate(TikaCoreProperties.CREATED);
				if( document.publicationDate == null ) {
					document.publicationDate = created;
				}
				if( document.publicationYear == null ) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy");
					document.publicationYear = Integer.parseInt(df.format(created));
				}
			}
			if( StringUtils.isBlank(document.author1Fn) && 
					StringUtils.isNotBlank(metadata.get(DublinCore.CREATOR)) ) {
				String[] authsplit = metadata.get(DublinCore.CREATOR).trim().split("\\s+", 2);
				document.author1Fn = authsplit[0];
				if( authsplit.length > 1 ) {
					document.author1Ln = authsplit[1];
				}
			}
			// Output all for debugging:
			for( String k : metadata.names()) {
				Logger.debug("Found "+k+" -> "+metadata.get(k));
			}
		} catch (Exception e) {
			Logger.error("Failure while parsing "+document.documentUrl);
			e.printStackTrace();
		}
		
		// Use the text from Tika to make a fuzzy hash:
		Logger.info("Attempting ssdeep hash generation...");
		if( StringUtils.isNoneBlank(text)) {
			SSDeep ssd = new SSDeep();
			FuzzyHash fh = ssd.fuzzyHashBuf(text.getBytes());
			document.ctpHash = fh.toString();
			Logger.info("Recorded ctpHash "+document.ctpHash+" for "+document.documentUrl);
		}
	}
	
	private InputStream getWaybackInputStream(String url, String timestamp ) throws IOException {
		String wbu = waybackReplayUrl(url, timestamp);
		URL wburl = new URL(wbu);
		HttpURLConnection conn = (HttpURLConnection)wburl.openConnection();
		// Do NOT follow redirects, as we want precisely the right timestamp:
		HttpURLConnection.setFollowRedirects(false);
		// Get the input stream:
		return conn.getInputStream();
	}
	
	private String waybackReplayUrl(String url, String timestamp) {
		return waybackUrl + "replay?url=" + url + "&date=" + timestamp;
	}
	
	/**
	 * 
	 * @author andy
	 *
	 */
	public static class ExtractFunction implements Function0<Boolean> {
		
		public List<Document> documents;
		public ExtractFunction(List<Document> documents) {
			this.documents = documents;
		}
		
		@Override
		public Boolean apply() {
			Logger.info("Extracting from "+documents.size()+" documents...");
			for (Document document : documents) {
				DocumentAnalyser da = new DocumentAnalyser();
				da.extractMetadata(document);
				Logger.info("Saving updated document metadata.");
				Ebean.save(document);
				if( document.book != null ) {
					Ebean.save(document.book);			
				}
			}
			Logger.info("Checking similarity against all documents for each WatchedTarget...");
			for (Document doc1 : documents) {
				for (Document doc2 : doc1.watchedTarget.documents ) {
					// Don't compare one with itself:
					if( ! doc1.documentUrl.equals(doc2.documentUrl) ) {
						double similarity = FuzzyHash.compare(doc1.ctpHash, doc2.ctpHash);
						if( similarity >= 90 ) {
							Alert alert = new Alert();
							alert.user = doc1.watchedTarget.target.authorUser;
							alert.text = "possible duplicate found: " + Alert.link(doc1) + " matches " +
									Alert.link(doc2) + " with " + similarity + "% " +
									"(" + Alert.compareLink(doc1, doc2) + ")";
							Ebean.save(alert);
							
						}
					}
				}
			}
			return true;
		}
	}


}
