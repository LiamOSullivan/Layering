package net.liamosullivan.layering;

import processing.core.*;
//import controlP5.*;
//import java.awt.Frame;
//import java.awt.BorderLayout;
//import processing.core.PConstants;
import java.util.*;

public class LayeringView {
	PApplet grandparent;
	Layering parent;
	//ControllerFrame cf;
	//ControlP5 cp5View;
	//Toggle lockToggle;
	boolean verboseMode = false;
	boolean displayLayerContents =true;
	//MorphLayer properties
	float mlw, mlh;
	int barH = 20;
	PFont font;
	PFont boldFont;
	int textH = 22;
	int iPtSize = 30, anchorSize=20;
	int dragSwatchSize =40;
	int anchorTextSize = 20;
	int layerTextSize = 40;
	int dialogTextSize = 30;
	//MorphParameter properties
	int baseFillSat = 150, baseFillBright =150;
	int baseStrokeSat = 200, baseStrokeBright =200;
	byte [] mpHues; 
	byte[] mlHues; 
	int mpOpac = 200, mlFillOpac = 100, mlStrokeOpac = 150; 
	int mlStrokeWeight = 3, mpvStrokeWeight =2;
	int mlSwatchW =10;

	public LayeringView(PApplet grandparent_, Layering parent_) {

		// TODO Auto-generated constructor stub
		grandparent = grandparent_;
		parent = parent_;
		addControlP5GUI();
		mpHues = new byte[parent.maxMParams];
		mlHues = new byte[parent.MAX_LAYERS];
		mpHues= generateHues(parent.maxMParams);
		mpHues = shuffle(mpHues);
		mlHues = generateHues(parent.MAX_LAYERS);
		mlHues = shuffle(mlHues);
		grandparent.colorMode(PApplet.HSB, 255.0F);
		grandparent.rectMode(PApplet.CENTER);
		grandparent.ellipseMode(PApplet.CENTER);
		font = grandparent.createFont("LucidaConsole-48.vlw", 24);
	}
	//Method for initialising the MorphOSC GUI elements
	private void addControlP5GUI(){
		//cp5View = new ControlP5(grandparent);
//		lockToggle = cp5View.addToggle("Lock")
//				.setPosition(100, grandparent.height-100)
//				.setSize(25,25);
//		//lockToggle.setId(parent.sZoneList.size());
//		lockToggle.setId(0);
//		SafeZone szlock = new SafeZone(grandparent, 0, lockToggle.getPosition().x, lockToggle.getPosition().y,
//				(float) lockToggle.getWidth(), (float) lockToggle.getHeight()); //GUI lock
//		parent.sZoneList.add(szlock); 
	}

	private byte[] generateHues(int max_) {
		int max = max_;
		byte hueStep = (byte)PApplet.floor(255 / (max));
		//System.out.println("Hue Step is " +hueStep);
		byte [] hues = new byte[max];
		for (int i = 0; i < max; i+=1) {
			hues[i] = (byte)(hueStep*i); //gives larger hue changes for low numbers of params, then wraps around
			if(hues[i]<0){
				hues[i]+=128;
			}
			//System.out.println("Hue #" + i + ": " + hues[i]);
			//grandparent.cp5.getController().setColor(hues[i]);
		}
		return hues;
	}

	protected void draw() {
		//draw layers
		//System.out.println("LayerView draw()");
		for (int i = 0; i < parent.layerList.size(); i += 1) {
			InteractiveLayer ml = parent.layerList.get(i);
			//System.out.println("Layer position: " + ml.getPosition());
			grandparent.pushMatrix();
			grandparent.translate(ml.getPosition().x, ml.getPosition().y);
			drawLayer(ml);
			grandparent.popMatrix();
		}
		
		InteractiveLayer ml = parent.layerList.get(1);
		//System.out.println("Layer position: " + ml.getPosition());
		grandparent.pushMatrix();
		grandparent.translate(ml.getPosition().x, ml.getPosition().y);
		displayTexture();
		grandparent.popMatrix();
		//draw MPs
//		for(int i=0;i<parent.mpList.size();i+=1){
//			drawMPSwatch(parent.mpList.get(i));  //swatches (select zones)
//			drawMPValue(parent.mpList.get(i));	 //value (value zones)
//		}

//		if(parent.isDraggingMParameter){
//			drawDraggingMP();
//		}
//		else if(parent.isDraggingMPValue){
//			drawDraggingMPV();
//		}
	}
	
	public void displayTexture() {
		//parent.colorMode(HSB, 255);
		TextureLayer tl = (TextureLayer)parent.layerList.get(1);
		grandparent.strokeWeight(3);
		grandparent.fill(tl.fColour, tl.fOpacity);
		//grandparent.rect(0, 0, tl.lWidth, tl.lHeight); /////////////////////////////////////Layer space
		//    beginShape();
		//    texture(textureImg);
		//    vertex(lx-halfWidth, ly-halfHeight, lx-halfWidth, ly-halfHeight);
		//    vertex(lx+halfWidth, ly-halfHeight, lx+halfWidth, ly-halfHeight);
		//    vertex(lx+halfWidth, ly+halfHeight, lx+halfWidth, ly+halfHeight);
		//    vertex(lx-halfWidth, ly+halfHeight, lx-halfWidth, ly+halfHeight);
		//    endShape();
		//    
		if (tl.textureImg!=null) {
			//image(textureImg, lx, ly);
			//tint(fColour);
			grandparent.imageMode(parent.CENTER);
			grandparent.image(tl.textureImg, 0, 0, tl.lWidth, tl.lHeight );
		}
		else {
			System.out.println("No texture loaded");
		}
		//image(textureImg, lx-halfWidth, ly-halfHeight, lWidth, lHeight );
		
		
	}
	
	// Image for lock status (deprecated)
	//if (IsLocked) {
	//			parent.image(locked, lockX, lockY, locked.width,
	//					locked.width);
	//		} else {
	//			parent.image(unlocked, lockX, lockY, unlocked.width,
	//					unlocked.width);
	//		}


	// draw borders for controllers added to MorphOSC
	//			grandparent.stroke(255, 200);
	//			grandparent.strokeWeight(4);
	//			grandparent.noFill();
	//			grandparent.rectMode(PApplet.CORNER);
	//			for (int i = 0; i < parent.cList.size(); i += 1) {
	//				Controller c = parent.cList.get(i);
	//				grandparent.rect(c.getPosition().x,c.getPosition().y,c.getWidth(),c.getHeight());
	//
	//			}
	//			//draw dragged MorphParameter
	//			grandparent.noStroke();
	//			grandparent.rectMode(PApplet.CENTER);
	//			if (parent.isDraggingMParameter) {
	//				MorphParameter mpDrag = parent.mpList.get(parent.dragMParamID);
	//				PVector szSize = mpDrag.getSelectZone().getSize();
	//				PVector mV = parent.getMouseVector();
	//				grandparent.fill(getStrokeByID(mpDrag.getId()));
	//				grandparent.rect(mV.x, mV.y, szSize.x, szSize.y); // show rect being
	//				// dragged with MP
	//				// color
	//			}
	//			//draw dragged MorphParameter value
	//			if (parent.isDraggingMPValue) {
	//				MorphParameter mpDrag = parent.mpList.get(parent.draggingMPValue);
	//				PVector vzSize = mpDrag.getValueZone().getSize();
	//				PVector mV = parent.getMouseVector();
	//				grandparent.noFill();
	//				grandparent.stroke(getStrokeByID(mpDrag.getId()));
	//				grandparent.rect(mV.x, mV.y, vzSize.x, vzSize.y); // show rect being
	//				// dragged with MP
	//				// color stroke
	//			}
	//			grandparent.rectMode(PApplet.CORNER);
	//		if(verboseMode){
	//				showMParameters();			
	//			}
	//
	//		}

	//draw MorphParameter swatch 
//	void drawMPSwatch(MorphParameter p_){
//		//Display a colour swatch next to controller at SelectZone 
//		
//		grandparent.pushStyle();
//		grandparent.rectMode(PApplet.CORNER);
//		grandparent.noStroke();
//		grandparent.fill(mpHues[p_.getId()], baseStrokeSat, baseStrokeBright, mpOpac);
//		grandparent.rect(p_.getSelectZone().pos.x, p_.getSelectZone().pos.y, 
//				p_.getSelectZone().getSize().x, p_.getSelectZone().getSize().y);
//		grandparent.popStyle();
//		
//	}
	
//	void drawMPValue(MorphParameter p_){
//
//		grandparent.pushStyle();
//		grandparent.rectMode(PApplet.CORNER);
//		grandparent.strokeWeight(mpvStrokeWeight);
//		grandparent.stroke(mpHues[p_.getId()], baseStrokeSat, baseStrokeBright, mpOpac);
//		grandparent.noFill();
//		//grandparent.fill(mpHues[p_.getId()], baseStrokeSat, baseStrokeBright, mpOpac);
//		grandparent.rect(p_.getValueZone().getPosition().x, p_.getValueZone().getPosition().y
//				,p_.getValueZone().getSize().x, p_.getValueZone().getSize().y);
//
//		grandparent.fill(255,200);
//		grandparent.textSize((p_.getValueZone().getSize().y/2));
//		grandparent.textAlign(PApplet.LEFT);
//		grandparent.text(PApplet.nf(p_.getValueZone().getValue(), 1, 3),
//				p_.getValueZone().getPosition().x+mpvStrokeWeight, 
//				p_.getValueZone().getPosition().y+mpvStrokeWeight+p_.getValueZone().getSize().y/2);
//		grandparent.popStyle();
//
//	}

	//For Desktop Mode
	//	private void initGUI() {
	//
	//		try {
	//			locked = parent.loadImage("data/lock_lock_01_small.jpg");
	//			unlocked = parent.loadImage("data/lock_unlock_01_small.jpg");
	//		} catch (Exception e) {
	//			// TODO Implement catch block
	//			e.printStackTrace();
	//			System.out.println("Could not load images!");
	//
	//		}
	//		if (IsLocked) {
	//			lockX = 50;
	//			lockY = parent.height-locked.height;
	//		} else {
	//			lockX = 50;
	//			lockY = parent.height-unlocked.height;
	//		}
	//		// SafeZones for GUI elements
	//		SafeZone lock = new SafeZone(parent, sZoneList.size(), lockX, lockY,
	//				(float) locked.width, (float) locked.height); //GUI lock
	//		lock.setId(sZoneList.size());
	//		sZoneList.add(lock); 
	//		sZoneList.size() += 1;
	//
	//	}


	//For Android mode
	protected void initGUI() {

		//		try {
		//			locked = parent.loadImage("data/lock_lock_01_small.jpg");
		//			unlocked = parent.loadImage("data/lock_unlock_01_small.jpg");
		//		} catch (Exception e) {
		//			// TODO Implement catch block
		//			e.printStackTrace();
		//			System.out.println("Could not load images!");
		//
		//		}
		//		if (IsLocked) {
		//			lockX = 50;
		//			lockY = parent.height-locked.height;
		//		} else {
		//			lockX = 50;
		//			lockY = parent.height-unlocked.height;
		//		}
		//		// SafeZones for GUI elements
		//		SafeZone lock = new SafeZone(parent, sZoneList.size(), lockX, lockY,
		//				(float) locked.width, (float) locked.height); //GUI lock
		//		lock.setId(sZoneList.size());
		//		sZoneList.add(lock); 
	}

	//Interactive layer display method
	private void drawLayer(InteractiveLayer ml_){

		//default style- bottom of stack
		//grandparent.fill(mlHues[ml_.getId()]);

		//drawLayerBar + main layer
		grandparent.pushStyle();
		grandparent.strokeWeight(mlStrokeWeight);
		if (ml_.isOverLayerBar&&!parent.IsLocked) {
			grandparent.stroke(mlHues[ml_.getId()], baseStrokeSat, baseStrokeBright, mlStrokeOpac);
		}
		else {
			grandparent.noStroke();
		}
		grandparent.fill(mlHues[ml_.getId()], baseFillSat, baseFillBright, mlFillOpac);
		grandparent.rect(0, 0, ml_.lWidth, ml_.lHeight);   //main layer
		grandparent.fill(mlHues[ml_.getId()], baseFillSat, baseFillBright, mlStrokeOpac);
		grandparent.rect(0, (float)(-ml_.halfHeight - 0.5D * barH), ml_.lWidth, barH); //draw top window bar
		grandparent.popStyle();

		//draw MP swatches on layer bar
		grandparent.pushStyle();
		grandparent.noStroke();
		grandparent.rectMode(PApplet.CENTER);
//		for(int j =0;j< ml_.mpList.size();j+=1){
//			MorphParameter mp =ml_.mpList.get(j);
//			//grandparent.rect((j+1)*barH-ml_.halfWidth,-ml_.halfHeight + 0.5F * barH,  mpSwatchSize, mpSwatchSize);
//			grandparent.fill(mpHues[mp.getId()], baseFillSat, baseStrokeSat, mpOpac);
//			grandparent.rect((j+1)*2*mlSwatchW-ml_.halfWidth,-ml_.halfHeight-barH/2,  mlSwatchW, barH-mlStrokeWeight/2);
//			//grandparent.textSize(barH);
//			//grandparent.text(""+mp.getId(), (j+1)*barH-ml_.halfWidth,-ml_.halfHeight); //place MP swatches on top bar
//
//		}
		//grandparent.noFill();
		//grandparent.textSize(anchorTextSize);
		//grandparent.ellipseMode(PApplet.CENTER);
		grandparent.popStyle();

		//draw layer ID number
		grandparent.pushStyle();
		grandparent.strokeWeight(3.0F);
		grandparent.textAlign(PApplet.LEFT);
		grandparent.textFont(font, layerTextSize);
		grandparent.fill(mlHues[ml_.getId()], baseFillSat, baseFillBright,mlStrokeOpac);
		grandparent.text(ml_.id, ml_.halfWidth-layerTextSize,ml_.halfHeight-ml_.layerTextSize/2);
		grandparent.popStyle();

		//draw handles (when over)
		grandparent.pushStyle();
		if (ml_.isOverHandle&&!parent.IsLocked) {
			//System.out.println("Showing Handle");
			grandparent.stroke(mlHues[ml_.getId()], baseStrokeSat, baseStrokeBright, mlStrokeOpac);
			grandparent.strokeWeight(mlStrokeWeight);
			//grandparent.textFont(font, dialogTextSize);
			//grandparent.fill(mlHues[ml_.getId()], baseFillSat, baseFillBright);
			grandparent.noFill();
			grandparent.ellipseMode(PApplet.CENTER);
			grandparent.ellipse(ml_.handleX[ml_.handleId], ml_.handleY[ml_.handleId], ml_.handleSize, ml_.handleSize);
		
		}
		grandparent.popStyle();


		//draw anchors
		grandparent.pushStyle();
		grandparent.fill(mlHues[ml_.getId()], baseFillSat, baseFillBright);
		grandparent.noStroke();
		grandparent.textAlign(PApplet.RIGHT);
//		for(int j =0;j<ml_.maList.size();j+=1){		
//			MorphAnchor ma = ml_.maList.get(j);
//			grandparent.ellipse(ma.getPosition().x, ma.getPosition().y, anchorSize, anchorSize); //draw a circle at the anchor point
//			//parent.text(""+ma.getPosition().x + ma.getPosition().y, ma.getPosition().x, ma.getPosition().y); 
//			if (verboseMode) {
//				for(int k=0;k<ma.valueList.size();k+=1){ //TODO: change to method getValueList
//					grandparent.stroke(mlHues[ml_.getId()], baseStrokeSat, baseStrokeBright, mlStrokeOpac);
//					//parent.text("*", ma.getPosition().x, ma.getPosition().y+(i*anchorTextSize)); 
//					grandparent.text(ma.getMPValueByIndex(k), ma.getPosition().x, ma.getPosition().y+(k*anchorTextSize)); 
//					//TODO: get rid of valueList and just use MPs, as there is anml.mpList for each anchor 
//					//that can hold 'current' value
//				}
//			}
//
//		}
		grandparent.popStyle();

	}

	///////////////////////////////////////////////////////////////////////////// objects being dragged

	protected void drawInterpPoint(PVector mv_){
		//draw interpolation point
		grandparent.pushStyle();
		grandparent.fill(255,255,255,150);
		grandparent.noStroke();
		//		grandparent.textSize(dialogTextSize);
		//		grandparent.textAlign(3);
		//		
		//grandparent.fill(mlHues[ml_.getId()]);
		grandparent.ellipse(mv_.x,mv_.y, iPtSize, iPtSize);
		grandparent.popStyle();

	}

//	protected void drawDraggingMP(){
//		grandparent.pushStyle();
//		grandparent.noStroke();
//		grandparent.rectMode(PApplet.CENTER);
//		//MorphParameter mpDrag = parent.mpList.get(parent.dragMParamID);
//		//PVector szSize = mpDrag.getSelectZone().getSize();
//		PVector mV = parent.getMouseVector();
//		grandparent.fill(mpHues[mpDrag.getId()], baseFillSat, baseStrokeSat);
//		grandparent.rect(mV.x, mV.y, dragSwatchSize, dragSwatchSize); // show rect being dragged
//		grandparent.fill(255);
//		grandparent.textFont(font);
//		grandparent.text(mpDrag.getId(),mV.x, mV.y);
//		grandparent.popStyle();
//	}

//	void drawDraggingMPV(){
//		grandparent.pushStyle();
//		grandparent.noStroke();
//		grandparent.rectMode(PApplet.CENTER);
//		MorphParameter mpVDrag = parent.mpList.get(parent.draggingMPValue);
//		//PVector szSize = mpVDrag.getValueZone().getSize();
//		PVector mV = parent.getMouseVector();
//		grandparent.fill(mpHues[mpVDrag.getId()], baseFillSat, baseStrokeSat);
//		//grandparent.rect(mV.x, mV.y, szSize.x, szSize.y); // show rect being dragged
//		grandparent.rect(mV.x, mV.y, dragSwatchSize, dragSwatchSize);
//		grandparent.fill(255);
//		grandparent.textFont(font);
//		grandparent.text(mpVDrag.getId(),mV.x, mV.y);
//		grandparent.popStyle();
//
//	}
	////////////////////////////////////////////////////////////////////////////////////////getters and setters
	int getFillByID(int id_){
		return 0;
	}

	void setFillByID(int id_){

	}

	int getStrokeByID(int id_){
		return 0;
	}

	void setStrokeByID(int id_){

	}
	
	
//////////////////////////////////////////////////////////////////////////////color management
	private byte[] shuffle(byte[] byteArray) {
		final Random randGenerator = new Random();;    
		for (int lastPlace = byteArray.length - 1; lastPlace > 0; lastPlace--) {
			// Choose a random location from 0..lastPlace
			byte randLoc = (byte) (randGenerator(randGenerator).nextInt(lastPlace + 1));
			// Swap items in locations randLoc and lastPlace
			byte temp = byteArray[randLoc];
			byteArray[randLoc] = byteArray[lastPlace];
			byteArray[lastPlace] = temp;
		}
		return byteArray;
	}

	private Random randGenerator(Random randGenerator_) {
		if (null == randGenerator_) {
			randGenerator_ = new Random();
		}
		return randGenerator_;
	} 


}
