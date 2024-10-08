import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import javax.imageio.ImageIO;

public class FloodFillNoGUI {

    private BufferedImage originalImage;
    private BufferedImage processedImage;

    public static void main(String[] args) {
        FloodFillNoGUI floodFill = new FloodFillNoGUI();
        floodFill.loadImage("imagemteste.png");

        System.out.println("Aplicando Flood Fill com Pilha...");
        floodFill.applyFloodFillWithStack(10, 10, Color.YELLOW.getRGB());

        System.out.println("Salvando imagem processada com Pilha...");
        floodFill.saveImage("imagempilha.png");

        floodFill.loadImage("imagemteste2.png");  

        System.out.println("Aplicando Flood Fill com Fila...");
        floodFill.applyFloodFillWithQueue(10, 10, Color.YELLOW.getRGB());

        System.out.println("Salvando imagem processada com Fila...");
        floodFill.saveImage("imagemfila.png");
    }

    public void loadImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            originalImage = ImageIO.read(imageFile);
            processedImage = ImageIO.read(imageFile); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível carregar a imagem.");
        }
    }

    public void saveImage(String outputPath) {
        try {
            File outputFile = new File(outputPath);
            ImageIO.write(processedImage, "png", outputFile);
            System.out.println("Imagem salva em: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível salvar a imagem.");
        }
    }

    public void applyFloodFillWithStack(int x, int y, int newColor) {
        if (processedImage == null) {
            System.out.println("Imagem não carregada.");
            return;
        }

        int targetColor = processedImage.getRGB(x, y);
        if (targetColor == newColor) {
            return;
        }

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] point = stack.pop();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= processedImage.getWidth() || py < 0 || py >= processedImage.getHeight()) {
                continue;
            }

            if (processedImage.getRGB(px, py) != targetColor) {
                continue;
            }

            processedImage.setRGB(px, py, newColor);

            stack.push(new int[]{px - 1, py});
            stack.push(new int[]{px + 1, py});
            stack.push(new int[]{px, py - 1});
            stack.push(new int[]{px, py + 1});
        }
    }

    public void applyFloodFillWithQueue(int x, int y, int newColor) {
        if (processedImage == null) {
            System.out.println("Imagem não carregada.");
            return;
        }

        int targetColor = processedImage.getRGB(x, y);
        if (targetColor == newColor) {
            return;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int px = point[0];
            int py = point[1];

            if (px < 0 || px >= processedImage.getWidth() || py < 0 || py >= processedImage.getHeight()) {
                continue;
            }

            if (processedImage.getRGB(px, py) != targetColor) {
                continue;
            }

            processedImage.setRGB(px, py, newColor);

            queue.add(new int[]{px - 1, py});
            queue.add(new int[]{px + 1, py});
            queue.add(new int[]{px, py - 1});
            queue.add(new int[]{px, py + 1});
        }
    }
}
