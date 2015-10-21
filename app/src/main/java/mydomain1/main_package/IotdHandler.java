package mydomain1.main_package;

/**
 * Created by himanshu on 10/14/2015.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class IotdHandler extends DefaultHandler {
    private static final String TAG = IotdHandler.class.getSimpleName();

    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inItem = false;
    private boolean inDate = false;

        private static String url = "http://www.nasa.gov/rss/dyn/image_of_the_day.rss";
    private static StringBuffer title = new StringBuffer();
    private static StringBuffer description = new StringBuffer();
    private static  String date = null;


    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {



        if (localName.equals("enclosure")) {
            url = attributes.getValue("url");
        }

        if (localName.startsWith("item")) {
            inItem = true;
        } else {
            if (inItem) {
                if (localName.equals("title")) {
                    inTitle = true;
                } else {
                    inTitle = false;
                }

                if (localName.equals("description")) {
                    inDescription = true;
                } else {
                    inDescription = false;
                }

                if (localName.equals("pubDate")) {
                    inDate = true;
                } else {
                    inDate = false;
                }
            }
        }

    }

    public void characters(char ch[], int start, int length) {
        String chars = (new String(ch).substring(start, start + length));

        if (inTitle) {
            title.append(chars);
        }

        if (inDescription) {
            description.append(chars);
        }

        if (inDate && date == null) {
            //Example: Tue, 21 Dec 2010 00:00:00 EST
            String rawDate = chars;
            try {
                SimpleDateFormat parseFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                Date sourceDate = parseFormat.parse(rawDate);

                SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
                date = outputFormat.format(sourceDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void processFeed() {
        try {
            URL url = new URL("http://www.nasa.gov/rss/dyn/image_of_the_day.rss");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(this);
            xr.parse(new InputSource(url.openStream()));

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } catch (SAXException e) {
            Log.e(TAG, e.toString());
        } catch (ParserConfigurationException e) {
            Log.e(TAG, e.toString());
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }
    public static Bitmap getBitmap()
    {
        Bitmap bitmap =null;
        try {
            HttpURLConnection conn = (HttpURLConnection)new URL("http://www.nasa.gov/rss/dyn/image_of_the_day.rss").openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getUrl() {
        return url;
    }

    public static String getTitle() {
        return title.toString();
    }

    public static String getDescription() {
        return description.toString();
    }

    public String getDate() {
        return date;
    }


}
