package logic;

import Data.GridImage;
import Data.ImageMetaData;
import Gui.ImageGrid;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.annotations.NotNull;
import com.drew.metadata.MetadataException;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class handles the providing of images from hard disk to the application.
 * These images are stored in {@link GridImage} objects. It caches the currently
 * loaded {@link GridImage}'s and scales them with a scaling factor. It further
 * shows {@link GridImage}'s in a single view.
 *
 */
public class ImageFileHandler {

    private final static String[] FILE_EXTENSIONS = new String[]{"JPG", "JPEG", "jpg", "jpeg", "jfif", "gif", "png", "bmp"};
    //frame for a single image view
    private final static int MAX_IMAGE_FRAME_WIDTH = 1920;
    private final static int MIN_IMAGE_FRAME_HEIGTH = 1011;

    static float scalingFactor = 0.4F;

    /**
     * Stores the currenty loaded GalleryImage's in original size.
     */
    private static GridImage[] cachedGridImages;

    /**
     * Image filter to specify the detectable images.
     */
    private static final FilenameFilter IMAGE_FILTER = (File dir, String name) -> {
        for (String ext : FILE_EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    };

    /**
     * This method receives a path to a directory, checks the images inside this
     * folder and creates two arrays of GalleryImage's. ...
     *
     * @param directoryPath Directory path or an empty string.
     * @return scaledGridImages. Array of GalleryImages, never null.
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
     */
    @NotNull
    public static GridImage[] createGridImages(@NotNull String directoryPath)
            throws ImageProcessingException, IOException, MetadataException {

        GridImage[] scaledGridImages = new GridImage[0];

        File file = new File(directoryPath);
        if (file.isDirectory()) {

            File[] images = file.listFiles(IMAGE_FILTER);
            cachedGridImages = new GridImage[images.length];
            scaledGridImages = new GridImage[images.length];

            for (int i = 0; i < images.length; i++) {
                ImageIcon currentImage = new ImageIcon(images[i].getAbsolutePath());            
                List<ImageMetaData> propertyList = MetadataExtractor.extract(images[i]);

                cachedGridImages[i] = new GridImage(images[i].getName(),
                        currentImage,
                        propertyList);
                scaledGridImages[i] = new GridImage(images[i].getName(),
                        scale(currentImage),
                        propertyList);
            }
        }

        return scaledGridImages;
    }

    /**
     * Scales the actual cached {@link GridImage}'s with a scaling factor.
     *
     * @return An array of scaled {@link GridImage}'s.
     */
    @NotNull
    public static GridImage[] scaleCurrentGalleryImages() {

        GridImage[] galleryImages = new GridImage[0];

        if (cachedGridImages != null) {
            galleryImages = new GridImage[cachedGridImages.length];

            for (int i = 0; i < galleryImages.length; i++) {
                galleryImages[i] = new GridImage(cachedGridImages[i]);
                ImageIcon scaledImage = scale(galleryImages[i].getImageIcon());
                galleryImages[i].setImageIcon(scaledImage);
            }
        }

        return galleryImages;
    }

    /**
     * Scales an ImageIcon depending on the value of a scaling factor.
     *
     * @param pictureToResize
     * @return Scaled picture.
     */
    private static ImageIcon scale(ImageIcon pictureToResize) {

        Image image = pictureToResize.getImage();
        Image scaledImage = image.getScaledInstance((int) (ImageGrid.MAX_GRID_IMAGE_WIDTH * scalingFactor),
                (int) (ImageGrid.MAX_GRID_IMAGE_HEIGHT * scalingFactor),
                Image.SCALE_SMOOTH); // scale it the smooth way

        return new ImageIcon(scaledImage);
    }

    /**
     * Scales an image with newImageWidth and a ratio.
     * 
     * ratio = width/heigth.
     *
     * @param imageToScale 
     * @param newImageWidth
     * @param ration
     * @return
     */
    private static ImageIcon scale(ImageIcon imageToScale, int newImageWidth, float ratio) {

        int imageHeigth = (int) (newImageWidth / ratio);

        Image image = imageToScale.getImage();
        Image scaledImage = image.getScaledInstance(newImageWidth, imageHeigth, Image.SCALE_SMOOTH); // scale it the smooth way

        return new ImageIcon(scaledImage);

    }

    /**
     * TODO: scale image here
     *
     * @param imageToShow
     */
    public static void show(GridImage imageToShow) {

        ImageIcon origImageToShow = null;
        for (GridImage galleryImage : cachedGridImages) {
            if (galleryImage.getName().equalsIgnoreCase(imageToShow.getName())) {
                origImageToShow = galleryImage.getImageIcon();
                break;
            }
        }

        //get corresponding original image icon here
        if (origImageToShow != null) {

            //frame definition
            JFrame frame = new JFrame(imageToShow.getName());
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent event) {

                    System.out.println("frame contentWidth (after resize) = " + frame.getContentPane().getWidth());
                    System.out.println("frame contentHeigth (after resize) = " + frame.getContentPane().getHeight());
                }
            });

            frame.setVisible(true);
            frame.setSize((MAX_IMAGE_FRAME_WIDTH - 300), (MIN_IMAGE_FRAME_HEIGTH - 200));
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //frame.setUndecorated(true);
            
//            int frameWidth = frame.getContentPane().getWidth();
//            int frameHeigth = frame.getContentPane().getHeight();
//            System.out.println("frame contentWidth = " + frameWidth);
//            System.out.println("frame contentHeigth = " + frameHeigth);            
//            float frameRatio = frameWidth / (float) frameHeigth;
            
            
            int imageWidth = origImageToShow.getIconWidth();
            if (imageWidth > MAX_IMAGE_FRAME_WIDTH) {
                int imageHeight = origImageToShow.getIconHeight();
                float ratio = imageWidth / (float) imageHeight;
                origImageToShow = scale(origImageToShow, MAX_IMAGE_FRAME_WIDTH, ratio);
            }

            JLabel imageLabel = new JLabel(origImageToShow);
            frame.add(imageLabel);
            
        } else {
            System.err.println("No Image to display!");
        }
    }
    
    public static double getScalingFactor() {
        return scalingFactor;
    }

    public static void setScalingFactor(float scalingFactor) {
        ImageFileHandler.scalingFactor = scalingFactor;
    }
}
