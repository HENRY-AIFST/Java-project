import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor {

    public static BufferedImage toGrayscale(BufferedImage original) {
        BufferedImage grayscaleImage = new BufferedImage(
                original.getWidth(), original.getHeight(), original.getType());

        int width = original.getWidth();
        int height = original.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int avg = (red + green + blue) / 3;

                Color grayColor = new Color(avg, avg, avg);

                grayscaleImage.setRGB(x, y, grayColor.getRGB());
            }
        }
        return grayscaleImage;
    }

    public static BufferedImage toNegative(BufferedImage original) {
        BufferedImage negativeImage = new BufferedImage(
                original.getWidth(), original.getHeight(), original.getType());

        int width = original.getWidth();
        int height = original.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int invertedRed = 255 - color.getRed();
                int invertedGreen = 255 - color.getGreen();
                int invertedBlue = 255 - color.getBlue();

                Color invertedColor = new Color(invertedRed, invertedGreen, invertedBlue);
                negativeImage.setRGB(x, y, invertedColor.getRGB());
            }
        }
        return negativeImage;
    }
}
