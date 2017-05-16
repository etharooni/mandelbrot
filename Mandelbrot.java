package mandelbrot;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mandelbrot {
	
	//the boundary of the period 2 disk: r = 0.25, x = r*cos(theta)-1, y = r*sin(theta)
	//static double theta = Math.PI/2;
	//static double r = 0.25;
	//static double cx = r*Math.cos(theta) - 1;
	//static double cy = r*Math.sin(theta);
	static double cx = -1.74006238257933990522;
	static double cy = 0.02817533977921104899;
	//-0.1002,0.8383
	static double rng = 10.0;
	static double rmin = cx - rng/2;
	static double rmax = cx + rng/2;
	static double imin = cy - rng/2;
	static double imax = cy + rng/2;
	
	static int width = 1920/2;
	static int height = 1080/2;
	static BufferedImage blarg;
	
	static BufferedImage gradient;
	
	public static void main(String[] args) throws IOException{
		int i=0;
		File gradientFile = new File("C:/Users/ethan/Desktop/gradient.bmp");
		gradient = ImageIO.read(gradientFile);
		while (rng >.0000001){
			blarg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			File outputfile = new File("frame" + i + ".png");
		    draw();
		    i++;
		    newRange(rng/1.5);
			//mandelbrot(.3859375,.559375);
		    ImageIO.write(blarg, "png", outputfile);
		}
	}
	
	static void newRange(double range){
		rng = range;
		rmin = cx - rng/2;
		rmax = cx + rng/2;
		imin = cy - rng/2;
		imax = cy + rng/2;
	}
	
	static void draw(){
		for (int x=0; x<width; x++) {
			for(int y=0; y<height; y++){
				int c = color((mandelbrot((double)x/(double)width,(double)y/(double)width)));
				blarg.setRGB(x, y, c);
			}
		}
	}
	
	private static int color(double c) {
		int yPos = Math.max(0, Math.min((gradient.getHeight()-(int)c*5), gradient.getHeight()-1));
		int gradientColor = gradient.getRGB(0, yPos);
		//Color color = new Color(gradientColor);
		return gradientColor;
	}

	static double mandelbrot(double x,double y) {
		double a = 0.0;
		double b = 0.0;
		double t = 0.0;
		while(a*a + b*b < 4 && t < 256) {
			double temp = a*a - b*b + rmin + (rmax - rmin) * x;
			b = 2*a*b + imax + (imin - imax) * y;
			a = temp;
			t++;
			//System.out.println(t + ":" + a + "," + b);
		}
		return t;
	}
}
