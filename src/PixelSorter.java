import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class PixelSorter extends JPanel {

	
	private BufferedImage img;
	private BufferedImage origImg;
	//private int[][] pixelArray;
	int height;
	int width;
	
	public PixelSorter(){
		super();
	}
	
	
	public void loadImage(String fileName){
		File file = new File(fileName);
		try {
			img = ImageIO.read(file);
			height = img.getHeight();
			width = img.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
		origImg = deepCopy(img);
	}
	
	private static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	
	public BufferedImage getImage(){
		return deepCopy(img);
	}
	public BufferedImage getOriginalImage(){
		return deepCopy(origImg);
	}
	
	
	public void paintComponent (Graphics g) 
	{ 
		g.drawImage(img, 0, 0, null);
		repaint();
	} 
//}
//*	
	int mode = 0;

	//MODE:
	//0 -> black
	//1 -> bright
	//2 -> white
	//b(16777216)

	//PImage img;
	

	int loops = 1;

	int blackValue = -10000000;
	int brigthnessValue = 60;
	int whiteValue = -6000000;

	int row = 0;
	int column = 0;

	boolean saved = false;

	


	void draw() {
	  while(column < width-1) {
	    //img.loadPixels(); 
	    sortColumn();
	    column++;
	    //img.updatePixels();
	  }
	  
	  while(row < height-1) {
	    //img.loadPixels(); 
	    sortRow();
	    row++;
	    //img.updatePixels();
	  }
	  
	  //image(img,0,0);
	  //if(!saved && frameCount >= loops) {
	  //  saveFrame(imgFileName+"_"+mode+".png");
	  //  saved = true;
	  //  println("DONE"+frameCount);
	  //  System.exit(0);
	  //}
	  repaint();
	}


	void sortRow() {
	  int x = 0;
	  int y = row;
	  int xend = 0;
	  
	  while(xend < width-1) {
	    switch(mode) {
	      case 0:
	        x = getFirstNotBlackX(x, y);
	        xend = getNextBlackX(x, y);
	        break;
	      case 1:
	        x = getFirstBrightX(x, y);
	        xend = getNextDarkX(x, y);
	        break;
	      case 2:
	        x = getFirstNotWhiteX(x, y);
	        xend = getNextWhiteX(x, y);
	        break;
	      default:
	        break;
	    }
	    
	    if(x < 0) break;
	    
	    int sortLength = xend-x;
	    
	    int[] pixels = new int[sortLength];
	    //int[] sorted   = new int[sortLength];
	    
	    for(int i=0; i<sortLength; i++) {
	      pixels[i] = img.getRGB(x+i, y);
	    }
	    
	    Arrays.sort(pixels);
	    
	    for(int i=0; i<sortLength; i++) {
	      img.setRGB(x+i, y, pixels[i]);      
	    }
	    
	    x = xend+1;
	  }
	}


	void sortColumn() {
	  int x = column;
	  int y = 0;
	  int yend = 0;
	  
	  while(yend < height-1) {
	    switch(mode) {
	      case 0:
	        y = getFirstNotBlackY(x, y);
	        yend = getNextBlackY(x, y);
	        break;
	      case 1:
	        y = getFirstBrightY(x, y);
	        yend = getNextDarkY(x, y);
	        break;
	      case 2:
	        y = getFirstNotWhiteY(x, y);
	        yend = getNextWhiteY(x, y);
	        break;
	      default:
	        break;
	    }
	    
	    if(y < 0) break;
	    
	    int sortLength = yend-y;
	    
	    int[] pixels = new int[sortLength];
	    
	    for(int i=0; i<sortLength; i++) {
	      pixels[i] = img.getRGB(x, y+i);
	    }
	    
	    Arrays.sort(pixels);
	    
	    for(int i=0; i<sortLength; i++) {
	    	img.setRGB(x, y+i, pixels[i]);
	    }
	    
	    y = yend+1;
	  }
	}


	//BLACK
	int getFirstNotBlackX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  color c;
	  while((c = img.pixels[x + y * img.width]) < blackValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	int getNextBlackX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  color c;
	  while((c = img.pixels[x + y * img.width]) > blackValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}

	//BRIGHTNESS
	int getFirstBrightX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  color c;
	  while(brightness(c = img.pixels[x + y * img.width]) < brigthnessValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	int getNextDarkX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  color c;
	  while(brightness(c = img.pixels[x + y * img.width]) > brigthnessValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}

	//WHITE
	int getFirstNotWhiteX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  color c;
	  while((c = img.pixels[x + y * img.width]) > whiteValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	int getNextWhiteX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  Color c;
	  while((c = img.pixels[x + y * img.width]) < whiteValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}


	//BLACK
	int getFirstNotBlackY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  Color c;
	  if(y < height) {
	    while((c = img.pixels[x + y * img.width]) < blackValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	int getNextBlackY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  Color c;
	  if(y < height) {
	    while((c = img.pixels[x + y * img.width]) > blackValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	//BRIGHTNESS
	int getFirstBrightY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  color c;
	  if(y < height) {
	    while(brightness(c = img.pixels[x + y * img.width]) < brigthnessValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	int getNextDarkY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  color c;
	  if(y < height) {
	    while(brightness(c = img.pixels[x + y * img.width]) > brigthnessValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	//WHITE
	int getFirstNotWhiteY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  color c;
	  if(y < height) {
	    while((c = img.pixels[x + y * img.width]) > whiteValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	int getNextWhiteY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  color c;
	  if(y < height) {
	    while((c = img.pixels[x + y * img.width]) < whiteValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	
	
	
	
	
	
}//*/
