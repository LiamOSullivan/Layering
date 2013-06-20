package net.liamosullivan.layering;



import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class InteractiveLayer
implements PConstants
{
	PApplet parent;
	int id;
	float lWidth;
	float lHeight;
	float halfWidth;
	float halfHeight;
	float lx;
	float ly;
	//PVector position;
	PVector offset;
	float xOffset;
	float yOffset;
	float xRelative;
	float yRelative;
	int dispWidth;
	int dispHeight;
	int noOfHandles = 4;
	int handleId = 0;
	float handleSize = 40.0F;
	float[] handleX;
	float[] handleY;
	int barH = 20;
	int barX;
	int barY;
	PFont font;
	int dialogTextSize = 12; int layerTextSize = 28;
	boolean verboseDisplay = false; boolean displayContents = true;
	boolean isOverHandle = false; boolean isOverLayer = false; boolean isOverLayerBar = false; boolean isDraggingOnLayer = false;

	private final float MIN_WIDTH =100.0F;
	private final float MIN_HEIGHT =100.0F;


	InteractiveLayer(PApplet p_, int id_, int x_, int y_, int width_, int height_, int dispW_, int dispH_)
	{
		parent = p_;
		id = id_;
		lx = x_;
		ly = y_;

		lWidth = width_;
		lHeight = height_;
		halfWidth = (lWidth / 2);
		halfHeight = (lHeight / 2);
		dispWidth = dispW_;
		dispHeight = dispH_;
		createHandles();
		font = parent.createFont("Consolas-48.vlw", 32.0F);
	}

	int getId(){
		return this.id;
	}

	void createHandles() {
		handleX = new float[noOfHandles];
		handleY = new float[noOfHandles];
		halfWidth = (lWidth / 2);
		halfHeight = (lHeight / 2);
		handleX[0] = (- halfWidth);
		handleY[0] = (- halfHeight - (barH));
		handleX[1] = (halfWidth);
		handleY[1] = (- halfHeight - (barH));
		handleX[2] = (- halfWidth);
		handleY[2] = (halfHeight);
		handleX[3] = (halfWidth);
		handleY[3] = (halfHeight);
	}

	boolean select(PVector pv_) {
		PVector pv=pv_;
		if ((pv.x > lx - halfWidth) && (pv.x < lx + halfWidth) && 
				(pv.y > ly - halfHeight) && (pv.y < ly + halfHeight))
		{
			return true;
		}

		return false;
	}

	boolean selectHandle(PVector v_)
	{
		PVector v=v_;
		boolean selected = false;
		for (int i = 0; (i < noOfHandles) && (!selected); i++) {
			float disX = lx + handleX[i] - v.x;
			float disY = ly + handleY[i] - v.y;
			if (PApplet.sqrt(PApplet.sq(disX) + PApplet.sq(disY)) < handleSize / 2.0F) {
				handleId = i;
				//PApplet.println("HandleId " + handleId);
				selected = true;
			}
			else
			{
				selected = false;
			}
		}
		return selected;
	}

	boolean selectBar(PVector v_)
	{
		PVector v=v_;
		if ((v.x > lx - halfWidth) && (v.x < lx + halfWidth) && 
				(v.y > ly - halfHeight - barH) && (v.y < ly - halfHeight))
		{
			return true;
		}

		return false;
	}

	public void setStartPosition(PVector v_)
	{
		PVector v = v_;
		// offset = dist(v, position);
		xOffset = (v.x - lx);
		yOffset = (v.y - ly);
		System.out.println("X Offset: "+xOffset+"Y Offset: "+yOffset);
			

	}
	public void setContentsStartPos(PVector mv_)
	{
		//placeholder- implemented in subclass
	}

	public PVector getPosition(){

		return new PVector(lx,ly);

	}

	public void move(PVector v_)
	{ PVector v=v_;
	lx = (v.x - xOffset);
	ly = (v.y - yOffset);
	moveContents(new PVector(lx,ly));
	createHandles();
	}


	public void moveContents(PVector mv_)
	{
		//placeholder- implemented in subclass
	}

	void resize(PVector v_)
	{
		//TODO: better doubled-sided resizing, constrain to onscreen rather than PApplet size.
		PVector v=v_;
		//boolean hasScaled=false;
		float originalW = lWidth;
		float originalH = lHeight;
		
		lWidth = PApplet.constrain(PApplet.abs(2 * (lx - v.x)), MIN_WIDTH, parent.width);
		
		if(v.y-ly < 0){
			lHeight = PApplet.constrain(PApplet.abs(2 * (ly - v.y - barH)), MIN_HEIGHT, parent.height);
		}	
		else{
			lHeight = PApplet.constrain(PApplet.abs(2 * (ly - v.y)), MIN_HEIGHT, parent.height);	
		}
		halfWidth = ((float)(lWidth / 2.0F));
		halfHeight = ((float)(lHeight / 2.0F));
		createHandles();
		float xScale = lWidth/originalW;
		float yScale = lHeight/originalH;
		PVector scaler = new PVector (xScale, yScale);
		resizeContents(scaler);
		
	}

	void rotate()
	{
	}

	void fixFlip()
	{
	}

	void getMorphPointRelative() {
		xRelative = (parent.mouseX - lx);
		yRelative = (parent.mouseY - ly);
	}


	void displayContents(){
		//placeholder- implemented in subclass
	}

	void resizeContents(PVector sc_){
		//placeholder- implemented in subclass
	}

}