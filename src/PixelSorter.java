import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class PixelSorter extends JPanel {

	private static final long serialVersionUID = -3184172796602661277L;
	
	private BufferedImage img;
	private BufferedImage origImg;
	private int height;
	private int width;
	
	
	//MODE:
	//-1 -> original
	//0  -> black
	//1  -> bright
	//2  -> white
	//b(16777216)
	
	public static final int ORIGINAL = -1;
	public static final int BLACK = 0;
	public static final int BRIGHT = 1;
	public static final int WHITE = 2;
	
	private int mode = BRIGHT;
	
	
	private int blackValue = -13487566;//-10000000;
	private int brightnessValue = 255;
	private int whiteValue = -3618616;//-6000000;
	
	private int row = 0;
	private int column = 0;
	
	
	public PixelSorter(){
		super();
	}
	
	public void setMode(int newMode){
		mode = newMode;
		draw();
	}
	public void setBrightness(int newBrightness){
		brightnessValue = newBrightness;
		draw();
	}
	
	
	public void loadImage(String fileName){
		File file = new File(fileName);
		
		try {
			img = ImageIO.read(file);
			height = img.getHeight();
			width = img.getWidth();
		} catch (IOException e) {e.printStackTrace();}
		
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
		
		setBrightness(brightnessValue-5);
		//repaint();
		//ImageIO.write(arg0, "", "cat_"+brightnessValue+".jpg");
		
	} 

	
	public void draw() {
		
		row = 0;
		column = 0;
		img = deepCopy(origImg);
		
		if(mode != ORIGINAL){
			
			
			while(column < width-1) {
				sortColumn();
				column++;
			}
			  
			while(row < height-1) {
				sortRow();
				row++;
			}
		}
		  
		repaint();
	}


	private void sortRow() {
	  int x = 0;
	  int y = row;
	  int xend = 0;
	  
	  while(xend < width-1) {
	    switch(mode) {
	      case BLACK:
	        x = getFirstNotBlackX(x, y);
	        xend = getNextBlackX(x, y);
	        break;
	      case BRIGHT:
	        x = getFirstBrightX(x, y);
	        xend = getNextDarkX(x, y);
	        break;
	      case WHITE:
	        x = getFirstNotWhiteX(x, y);
	        xend = getNextWhiteX(x, y);
	        break;
	      default:
	        break;
	    }
	    
	    if(x < 0) break;
	    
	    int sortLength = xend-x;
	    
	    int[] pixels = new int[sortLength];
	    
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


	private void sortColumn() {
	  int x = column;
	  int y = 0;
	  int yend = 0;
	  
	  while(yend < height-1) {
	    switch(mode) {
	      case BLACK:
	        y = getFirstNotBlackY(x, y);
	        yend = getNextBlackY(x, y);
	        break;
	      case BRIGHT:
	        y = getFirstBrightY(x, y);
	        yend = getNextDarkY(x, y);
	        break;
	      case WHITE:
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
	private int getFirstNotBlackX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  
	  while((img.getRGB(x, y)) < blackValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	private int getNextBlackX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  
	  if(x >= width) return width-1;
	  
	  while((img.getRGB(x, y)) > blackValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}

	//BRIGHTNESS
	private int getFirstBrightX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  while(getBrightness(img.getRGB(x, y)) < brightnessValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	private int getNextDarkX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  
	  if(x >= width) return width-1;
	  
	  while(getBrightness(img.getRGB(x, y)) > brightnessValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}

	//WHITE
	private int getFirstNotWhiteX(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  while((img.getRGB(x, y)) > whiteValue) {
	    x++;
	    if(x >= width) return -1;
	  }
	  return x;
	}

	private int getNextWhiteX(int _x, int _y) {
	  int x = _x+1;
	  int y = _y;
	  
	  if(x >= width) return width -1;
	  
	  while((img.getRGB(x, y)) < whiteValue) {
	    x++;
	    if(x >= width) return width-1;
	  }
	  return x-1;
	}


	//BLACK
	private int getFirstNotBlackY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  if(y < height) {
	    while((img.getRGB(x, y)) < blackValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	private int getNextBlackY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  
	  if(y < height) {
	    while((img.getRGB(x, y)) > blackValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	//BRIGHTNESS
	private int getFirstBrightY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  if(y < height) {
	    while(getBrightness(img.getRGB(x, y)) < brightnessValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	private int getNextDarkY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  
	  if(y < height) {
	    while(getBrightness(img.getRGB(x, y)) > brightnessValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	//WHITE
	private int getFirstNotWhiteY(int _x, int _y) {
	  int x = _x;
	  int y = _y;
	  
	  if(y < height) {
	    while((img.getRGB(x, y)) > whiteValue) {
	      y++;
	      if(y >= height) return -1;
	    }
	  }
	  return y;
	}

	private int getNextWhiteY(int _x, int _y) {
	  int x = _x;
	  int y = _y+1;
	  
	  if(y < height) {
	    while((img.getRGB(x, y)) < whiteValue) {
	      y++;
	      if(y >= height) return height-1;
	    }
	  }
	  return y-1;
	}

	
	private float getBrightness(int rgb){
		Color color = new Color(rgb);
		float average = color.getRed()+color.getGreen()+color.getBlue();
		average /= 3;
		
		return average;
	}
	
	
	
	
	
	
}//*/
