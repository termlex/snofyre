package uk.nhs.cfh.dsp.srth.distribution;



import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * An utility class for performing zip operations on a zip archive
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 1, 2011 at 5:19:10 PM
 */
public class ZipArchiveUtils {

    private static Logger logger = Logger.getLogger(FileDownloader.class.getName());
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String ERR_MESSAGE = "Nested exception message is : ";

    public ZipArchiveUtils() {
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    SEPARATOR+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning(ERR_MESSAGE+ e.fillInStackTrace().getMessage());
        }
    }

    public static void copyInputStream(InputStream in, OutputStream out)
            throws IOException{
        byte[] buffer = new byte[1024];
        int len;

        while((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);

        in.close();
        out.close();
        logger.info("Finished copying inputstream.");
    }

    public static void extractZipFileContents(File file){
        if (file != null) {
            extractZipFileContents(file, file.getParent());
        }
    }

    public static void extractZipFileContents(File file, String destinationFolder) {
        try
        {
            logger.info("Extracting zip file contents...");
            ZipFile zipFile = new ZipFile(file);

            Enumeration entries = zipFile.getEntries();

            while(entries.hasMoreElements()) {
                ZipArchiveEntry entry = (ZipArchiveEntry)entries.nextElement();

                if(entry.isDirectory()) {

                    logger.info("Extracting directory: " + entry.getName());
                    File dir = new File(destinationFolder, entry.getName());
                    boolean canWrite = dir.exists();
                    if(! canWrite)
                    {
                        canWrite = dir.mkdir();
                    }

                    if(canWrite){
                        continue;
                    }
                    else{
                        logger.warning("Error creating directory during extraction");
                    }
                }

                logger.info("Extracting file: " + entry.getName());
                copyInputStream(zipFile.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(new File(destinationFolder, entry.getName()))));
            }

            zipFile.close();
            logger.info("Closed zip file.");
        } catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace());
        }
    }

    public static void main(String[] args) {

        String propsFilePath = "packextract.properties";
        // check properties file exists in configurator folder
        File propsFile = new File(USER_DIR, propsFilePath);
        if(propsFile.exists() && propsFile.canRead())
        {
            // load values from properties file
            Properties props = new Properties();
            try
            {
                props.load(new FileInputStream(propsFile));
                String trudPackName = props.getProperty("trud.pack.name");
                String installationPath = props.getProperty("installation.path");
                String snofyrePackName = props.getProperty("snofyre.pack.name");
                String snomedPackName = props.getProperty("snomed.pack.name");

                // check downloaded pack exists
//                String downloadedPackPath = installationPath+SEPARATOR+trudPackName;
                logger.info("Verifying downloaded pack and starting extraction");
                File downloadedPack = new File(installationPath, trudPackName);
                if(downloadedPack.exists() && downloadedPack.canRead())
                {
//                    // extract pack into same folder
//                    ZipArchiveUtils.extractZipFileContents(downloadedPack);

                    // extract snofyre data to installation folder
                    logger.info("Extracting TRUD Pack");
                    ZipArchiveUtils.extractZipFileContents(downloadedPack, installationPath);

                    File snofyrePack = new File(installationPath, snofyrePackName);
                    if(snofyrePack.exists() && snofyrePack.canRead())
                    {
                        logger.info("Extracting SNOFYRE CT DATA");
                        ZipArchiveUtils.extractZipFileContents(snofyrePack, installationPath);
                    }

                    File snomedPack = new File(installationPath, snomedPackName);
                    if(snomedPack.exists() && snomedPack.canRead())
                    {
                        logger.info("Extracting SNOMED CT DATA");
                        ZipArchiveUtils.extractZipFileContents(snomedPack, installationPath);
                    }

                    logger.info("Finished downloading all packs from TRUD");
                }
                else
                {
                    logger.info("Error unpacking downloaded pack. Pack has not been downloaded or is corrupt!");
                }
            }
            catch (IOException e) {
                logger.warning(ERR_MESSAGE + e.fillInStackTrace());
            }
        }
        else
        {
            throw new IllegalArgumentException("Can not find properties file. It must located at : "+USER_DIR+SEPARATOR+propsFilePath);
        }
    }
}
