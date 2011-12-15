package com.alwex.jboy.hardware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 *
 * @author Alex
 */
public class MEM extends AbstractHardware
{

    protected static Logger logger;
    private static MEM instance;

    public MEM()
    {
        logger = Logger.getLogger(this.getClass());
    }

    public static MEM getInstance()
    {
        if (null == instance)
        {
            instance = new MEM();
        }

        return instance;
    }

    public byte[] read(String filename)
    {
        byte[] bytes = null;
        try
        {
            File theFile = new File(filename);
            bytes = loadByteFromFile(theFile);

        }
        catch (FileNotFoundException ex)
        {
        }
        catch (IOException ex)
        {
        }

        return bytes;
    }

    /**
     * lit un fichier en octet
     *
     * @param theFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static byte[] loadByteFromFile(File theFile) throws FileNotFoundException, IOException
    {
        InputStream input = new FileInputStream(theFile);
        long length = theFile.length();

        byte[] bytes = new byte[(int) length];

        int offset = 0, numread = 0;
        while (offset < bytes.length)
        {
            numread = input.read(bytes, offset, bytes.length - offset);
            offset += numread;
        }

        input.close();

        return bytes;
    }
}
