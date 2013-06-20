package net.liamosullivan.layering;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class TextureLayer extends InteractiveLayer{

	ArrayList <Float> colBounds = new ArrayList<Float>();
	ArrayList <ArrayList<Float>>rowBounds = new ArrayList<ArrayList<Float>>();
	PGraphics textureImg; // dummy image 
	int colourIndex = 0, lastColourIndex = 0;
	int minJitter = 5, maxJitter = 100; //range of random values to be used
	//variables for texture cell management...
	int colCount=0;
	int hInc = 0, vInc = 0; //separate increments for the vertical and horizontal directions
	float hTotal = 0, vTotal =0; //for keeping track of accumulated col and row boundaries
	float hTotalNorm = 0.0F, vTotalNorm = 0.0F;
	int baseHue, baseSat, baseBright;
	int fColour = 150, fOpacity=100, sColour = 150,  sOpacity =150; 
	public TextureLayer(PApplet p_, int id_, int x_, int y_, int w_, int h_, int dispW_, int dispH_) {
		// TODO Auto-generated constructor stub
		super(p_, id_, x_, y_, w_, h_, dispW_, dispH_);
		textureImg = generateTexture();
	}


	void init() {
	}

	PGraphics generateTexture() {
		 System.out.println("Generating Texture");
		//textureImg.loadPixels();
		colBounds.add(hTotalNorm); //add column with edge at x =0 (less conditon-check logic later)
		PGraphics temp = parent.createGraphics((int)lWidth, (int)lHeight);
		System.out.println("Buffer created");
		temp.beginDraw();
		System.out.println("Draw started");
		for (int i=0; i<temp.width; i+=hInc) {
			hInc = parent.floor(parent.random(minJitter, maxJitter)); //  randomisation of the outer loop increment produces a 'random pattern'.
			// the upper bound on the range of random values produced effectively controls 
			//'pattern jitter', or the amount of variance in the pattern from a periodic grid. 
			//note the use of floor() to convert the float to an int
			hTotal+=hInc;
			if (hTotal>lWidth) {
				hTotal = lWidth;
			}
			hTotalNorm = parent.map(hTotal, 0.0F, lWidth, 0.0F, 1.0F);
			colBounds.add(hTotalNorm);
			lastColourIndex = (lastColourIndex + 1)%2; //  modulo operation effectviely returns either 0 or 1
			colourIndex = lastColourIndex;
			vTotal =0;
			vTotalNorm = 0.0F;
			rowBounds.add(new ArrayList<Float> ());
			rowBounds.get(colCount).add(vTotalNorm);
			for (int j=0; j<temp.height; j+=vInc) {
				vInc = parent.floor(parent.random(minJitter, maxJitter)); 
				vTotal+=vInc;
				if (vTotal>lHeight) {
					vTotal = lHeight;
				}
				vTotalNorm = parent.map(vTotal, 0.0F, lHeight, 0.0F, 1.0F);
				rowBounds.get(colCount).add(vTotalNorm); 
				for (int r=0; r<hInc; r+=1) {//  increment horizontal pixel position (within each grid square)
					for (int s=0; s<vInc; s+=1) { //  increment vertical pixel position
						temp.set(i+r, j+s, parent.color(baseHue, baseSat+(colourIndex*baseSat), baseBright, 100));
					}
				}
				//vBound = j+(i*j); //the pixel row no of the lower cell bound
				//text(colIndex+": "+vBound, i, j);
				//PVector cell = new PVector(hInc, vInc, vBound);
				//cells.add(cell);
				colourIndex = (colourIndex + 1)%2;
			}
			colCount+=1;
			//println();
		}
		// System.out.println("Cells: ");
		//  for (int i=0;i<cells.size(); i+=1) {
		//    PVector cell = cells.get(i);
		//   System.out.print("\t("+cell.x+", "+cell.y+", "+cell.z+" )");
		//  }
		System.out.print("Column Boundaries:");
		for (int i=0;i<colBounds.size(); i+=1) {
			float c = colBounds.get(i);
			System.out.print("\t"+c);
		}
		System.out.println();

		System.out.println("Row Boundaries:");
		for (int i=0;i<rowBounds.size(); i+=1) {
			ArrayList<Float> r = rowBounds.get(i);
			for (int j=0;j<r.size();j+=1) {
				float rData = r.get(j);
				System.out.print("\t"+rData);
			}
			System.out.println();
		}
		//println();
		//textureImg.filter(BLUR, 1.0); //second parameter is the radius of the blur to be used
		//filter(BLUR, 1.0);

		//long elapsed = millis()-startTime;
		//println("\n\nElapsed Setup Time: "+elapsed);
		temp.endDraw();
		return temp;
	}

	void mapTexture(PImage source) {
	};
	void remapTexture() {
	}; 

	PVector findCell(int xIn_, int yIn_) {
		int xIn = xIn_;
		int yIn = yIn_;
		int colCount =-1, rowCount =-1;
		PVector cellNo = new PVector(-1, -1);
		// this loop finds which column mouseX is in 
		for (int i = 0; i < colBounds.size()-1; i+=1) {  
			float c1 = colBounds.get(i);
			float c2 =colBounds.get(i+1); 
			if (xIn > c1 && xIn < c2) {
				colCount=i;  //mouseX is in this column
				//println("Mouse is in column "+colCount);
				break;
			}
			else {
				colCount = colBounds.size(); //mouseX is in last column
			}
		}
		//this loop searches the column found previously and
		//finds which cell mouseY lies in
		ArrayList<Float> r = rowBounds.get(colCount);
		//println("Rows for column "+colCount);
		for (int i=0;i< r.size();i+=1) {
			float data = r.get(i);
			//System.out.print("\t "+data);
		}
		//println();
		for (int i = 0; i < r.size()-1; i++) {
			float r1 = r.get(i);
			float r2 = r.get(i+1); 
			if (yIn >=r1 && yIn <= r2) {
				rowCount=i;
				break;
			}
			else {
				rowCount=r.size(); //mouseY is in bottom-most row
			}
		}
		cellNo=new PVector(colCount, rowCount);
		return cellNo;
	}

}
