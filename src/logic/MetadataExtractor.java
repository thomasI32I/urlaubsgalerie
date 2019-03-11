package logic;

import data.ImageMetaData;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.file.FileSystemDirectory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extracts meta data from images.
 *
 */
public class MetadataExtractor {

    /**
     *
     * @param currentPicture
     * @return List of ImageProperties
     * @throws ImageProcessingException
     * @throws IOException
     * @throws com.drew.metadata.MetadataException
     */
    public static List<ImageMetaData> extract(File currentPicture) throws ImageProcessingException, IOException, MetadataException {

        List<ImageMetaData> imagePropertyList = new ArrayList<>();
        ImageMetaData imageProperty;
        Metadata metadata = ImageMetadataReader.readMetadata(currentPicture);

        //check specific directories and files
        //1 - File
        FileSystemDirectory fileDirectory = metadata.getFirstDirectoryOfType(FileSystemDirectory.class);
        if (fileDirectory != null) {
            if (fileDirectory.containsTag(FileSystemDirectory.TAG_FILE_NAME)) {
                String fileName = fileDirectory.getString(FileSystemDirectory.TAG_FILE_NAME);
                imageProperty = new ImageMetaData(fileDirectory.getTagName(FileSystemDirectory.TAG_FILE_NAME), fileName);
                imagePropertyList.add(imageProperty);
            }
            if (fileDirectory.containsTag(FileSystemDirectory.TAG_FILE_SIZE)) {
                int fileSize = fileDirectory.getInt(FileSystemDirectory.TAG_FILE_SIZE);
                fileSize /= 1024;
                String strFileSize = String.format("%d KB", fileSize);
                imageProperty = new ImageMetaData(fileDirectory.getTagName(FileSystemDirectory.TAG_FILE_SIZE), strFileSize);
                imagePropertyList.add(imageProperty);
            }
            if (fileDirectory.containsTag(FileSystemDirectory.TAG_FILE_MODIFIED_DATE)) {
                String fileModifiedDate = fileDirectory.getString(FileSystemDirectory.TAG_FILE_MODIFIED_DATE);
                imageProperty = new ImageMetaData(fileDirectory.getTagName(FileSystemDirectory.TAG_FILE_MODIFIED_DATE), fileModifiedDate);
                imagePropertyList.add(imageProperty);
            }
        }

        //Exif IFD0
        ExifIFD0Directory exIfD0directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (exIfD0directory != null) {
            for (Tag tag : exIfD0directory.getTags()) {
                imageProperty = new ImageMetaData(tag.getTagName(), tag.getDescription());
                imagePropertyList.add(imageProperty);
            }
        }

        //Exif SubIFD
        ExifSubIFDDirectory exifSubDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifSubDirectory != null) {
            for (Tag tag : exifSubDirectory.getTags()) {
                imageProperty = new ImageMetaData(tag.getTagName(), tag.getDescription());
                imagePropertyList.add(imageProperty);
                //System.out.println("tag.getTagName(), tag.getDescription(): " + tag.getTagName() + " / " + tag.getDescription());
            }
        }
        
        return imagePropertyList;
    }
}
