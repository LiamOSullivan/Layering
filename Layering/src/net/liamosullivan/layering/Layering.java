package net.liamosullivan.layering;

import controlP5.ControlEvent;
//import controlP5.Controller;
//import controlP5.Slider;
//import controlP5.ControlP5;
//import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
//import processing.core.PImage;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent; 
//import oscP5.*;
//import netP5.NetAddress;

/*TODO:
 * Add ability to drag multiple parameters to a layer at once e.g. using multi-select.


 */
public class Layering implements PConstants {
	PApplet parent;
	//ControlP5 cp5;
	//OscP5 oscP5;
	// Defaults
	int maxMParams = 64;
	int nMParams = 0;
	int MAX_LAYERS = 5;
	//int nLayers = 0;
	int DEFAULT_LAYER_W = 100, DEFAULT_LAYER_H = 100; // default dimensions of added MorphLayers

	// Display assets
	private float lockX, lockY; // position of the GUI lock/unlock button
	int lockImageScale = 4;
	//	List<Controller> cList = new ArrayList();
	//	List<MorphParameter> mpList = new ArrayList<MorphParameter>();
	List<InteractiveLayer> layerList = new ArrayList<InteractiveLayer>();
	//	List<SafeZone> sZoneList = new ArrayList<SafeZone>();
	LayeringView gui;
	// List <Integer> dragIds = new ArrayList <Integer>(); //list of paramIDs
	// being dragged-and-dropped.

	// Setup options
	public boolean addLayersFromGUI = true; // add MorphLayers at runtime with
	// GUI interaction
	public boolean highlightControllers = true; // color controllers added as
	// MorphParameters
	public boolean useControlFrame= false;
	public boolean useSoftwareBus = false; //sends data back to sketch
	public boolean useOSCAgent = false; //sends data out via OSCAgent

	//MorphOSCView gui;

	// Interaction flags
	public boolean IsLocked = false;
	public boolean showMenu = false;
	boolean layerIsMoving = false, layerIsResizing = false, isOver = false;
	int movingLayer = 0;
	int resizeLayer = 0;
	int overLayer = 0;
	boolean isDraggingMParameter = false; // true if a MorphParam is
	// being dragged
	boolean isDraggingMPValue = false; // true if a MorphParam value is
	// being dragged
	int dragMParamID = 0; // id of MP or MP value
	int draggingMPValue = 0;
	// being dragged
	float mpValue = 0.0F;
	boolean isDraggingMAnchor = false;
	int [] dragMAnchorID = {-1,-1}; //id of layer and anchor
	PVector mouseVector = new PVector(0, 0);

	boolean addAnchorsToAllLayers = false;

	private LayeringController layerController;
	//	protected OSCAgent oscA;
	//protected ControllerFrame GUI;

	public Layering(PApplet p_){

		parent = p_;
		//cp5 = new ControlP5(parent);


		parent.registerMethod("pre", this);
		parent.registerMethod("draw", this);
		parent.registerMethod("dispose", this);
		parent.registerMethod("mouseEvent", this);
		parent.registerMethod("keyEvent", this);
		addMouseHandler();
		addView();
		//		if(useControlFrame){
		//			GUI.addControlFrame("2", 500,500);
		//		}
		//oscP5 = new OscP5(this,8001);
		//addOSCAgent();



	}


	public PVector getLockPosition() {
		PVector lk = new PVector(lockX, lockY);
		return lk;

	}

	public void setLockPosition(int lkx_, int lky_) {
		lockX = lkx_;
		lockY = lky_;

	}

	//	public void addController(Controller c_) {
	//		if (nMParams < maxMParams  && c_ instanceof Slider) {
	//			//Controller c = c_;
	//			//cList.add(c);
	//
	//			//Parser pr = new Parser(parent);
	////			MorphParameter mp = pr.parseController(c);
	////			mp.setId(nMParams);
	////			mpList.add(mp);
	////			nMParams += 1;
	//			//addSafeZone(c);
	//
	//
	//		} else {
	//			System.out.println("Can't add Controller, maximum reached");
	//		}
	//	}

	//	public void addAllControllers(ControlP5 cp5In) {
	//		List controllerList = cp5In.getAll();
	//		for(int i=0;i<controllerList.size();i+=1){
	//			if(controllerList.get(i) instanceof Slider){
	//				Controller c = (Controller)controllerList.get(i);
	//				addController(c);
	//			}
	//		}
	//
	//	}

	//	void addSafeZone(Controller c){
	////		SafeZone sz = new SafeZone(parent, sZoneList.size(), c.getPosition().x, 
	////				c.getPosition().y,(float) c.getWidth(), (float) c.getHeight()); 
	////		sz.setId(sZoneList.size());
	////		sZoneList.add(sz); 
	////		System.out.println("Safe Zone #"+sz.getId()+" added to controller #"+c.getId()
	////				+" @ ("+sz.getPosition().x+", "+sz.getPosition().y+")");
	//
	//	}


	public void addLayer(PVector v_) {
		// Used at runtime and called from mousePressed in this class only
		PVector v = v_;
		int mlx = (int) v.x;
		int mly = (int) v.y;
		addLayer(mlx,mly);
	}
	
	public void addLayer(float x_, float y_) {
		// Used if called with 2 arguments
		int mlx = (int)x_;
		int mly = (int)y_;
		addLayer(mlx, mly);

	}
	
	public void addLayer(int x_, int y_) {
		// Used if called with 2 arguments
		int mlx = x_;
		int mly = y_;
		addLayer(mlx, mly, DEFAULT_LAYER_W, DEFAULT_LAYER_H);

	}
	
	public void addLayer(float x_, float y_, float wIn_, float hIn_) {
		// Used if called with 4 arguments
		int mlx = (int) x_;
		int mly = (int) y_;
		int wIn = (int) wIn_;
		int hIn = (int) hIn_;
		addLayer(mlx,mly,wIn,hIn);
	}
	
	public void addLayer(int x_, int y_, int wIn_, int hIn_) {
		LayerType type = LayerType.DUMMY;
		addLayer(type, x_, y_, wIn_, hIn_);

	}
	
	public void addLayer(LayerType type_, PVector v_) {
		// Used at runtime and called from mousePressed in this class only
		PVector v = v_;
		int mlx = (int) v.x;
		int mly = (int) v.y;
		LayerType type = type_;
		addLayer(type, mlx, mly);
		//addLayer(mlx,mly);
	}

	
	public void addLayer(LayerType type_, int x_, int y_) {
		// Used if called with 2 arguments
		int mlx = x_;
		int mly = y_;
		LayerType type = type_;
		addLayer(type, mlx, mly, DEFAULT_LAYER_W, DEFAULT_LAYER_H);

	}
	
	public void addLayer(LayerType type_, float x_, float y_) {
		// Used if called with 2 arguments
		int mlx = (int)x_;
		int mly = (int)y_;
		LayerType type = type_;
		addLayer(type, mlx, mly, DEFAULT_LAYER_W, DEFAULT_LAYER_H);

	}
	
	public void addLayer(LayerType type_, int x_, int y_, int wIn_, int hIn_) {
		// Used if called with 4 arguments
		LayerType type = type_;
		int mlx = x_;
		int mly = y_;
		int wIn = wIn_;
		int hIn = hIn_;
		if (layerList.size() < MAX_LAYERS) {
			//			// MorphLayer(PApplet p_, int id_, int x_, int y_, int w_, int h_,
			//			// int fC_, int sC_, int dispW_, int dispH_)
			switch (type){

			case DUMMY:
				DummyLayer dl = new DummyLayer(parent,layerList.size(), mlx, mly, wIn,
						hIn, parent.width, parent.height);
				layerList.add(dl);
				System.out.println("Dummy Layer added @: (" + mlx + ", " + mly
						+ "), now there are " +layerList.size());
				break;

			case TEXTURE:
				TextureLayer tl = new TextureLayer(parent,layerList.size(), mlx, mly, wIn,
						hIn, parent.width, parent.height);
				layerList.add(tl);
				System.out.println("Texture Layer added @: (" + mlx + ", " + mly
						+ "), now there are " +layerList.size());
				break;

			default:
				break;
			}


		} else {
			System.out.println("Can't add Layer, maximum reached");
		}

	}


	//	public void getControllerInfo() {
	//		for (int i = 0; i < cList.size(); i++) {
	//			Controller c = (Controller) cList.get(i);
	//			String label = c.getLabel();
	//			String name = c.getName();
	//			String add = c.getAddress();
	//			String val = Float.toString(c.getValue());
	//			System.out.print("Controller " + i + "\t Label: " + label
	//					+ "\t Name: " + name);
	//			System.out.println("\t Address: " + add + "\t Value: " + val);
	//		}
	//	}

	//	private int getMPIndexById(int fid_){
	//		int fid = fid_;
	//		int index;
	//		MorphParameter mp;
	//		for(int i =0; i<mpList.size();i+=1){
	//			mp = mpList.get(i);
	//			if(mp.getId()==fid){
	//				return i;	
	//			}
	//		}
	//		return -1;
	//	}

	//	int getMLayerIndexById(int layerId_){
	//		int layerId=layerId_;
	//		for(int i=0; i<mlList.size();i+=1){
	//			if(mlList.get(i).getId()==layerId_){
	//				return i;
	//			}
	//
	//		}
	//		return -1;
	//
	//
	//	}

	//	MorphAnchor getMAFromLayerById(int layerId_, int anchorId_){
	//		int layerId = layerId_;
	//		int anchorId = anchorId_;
	//		MorphLayer l = mlList.get(layerId);
	//		ArrayList<MorphAnchor> al = l.getMAList();
	//		MorphAnchor ma;
	//		int index = -1;
	//		for(int i=0;i<al.size();i+=1){
	//			ma=al.get(i);
	//			if(ma.getId()==anchorId){
	//				return ma;	
	//			}
	//
	//		}
	//		return new MorphAnchor(-1,new PVector(-1,-1)); //Null MorphAnchor
	//
	//	}

	protected void setGUILockStatus(boolean set_){
		IsLocked = set_;
	}

	protected boolean getGUILockStatus(){
		return IsLocked;

	}


	public void pre() {
		parent.background(0);


	}

	public void draw() {
		//System.out.println("Draw called");
		gui.draw();

	}

	public void mouseEvent(MouseEvent e_){	
		MouseEvent e = e_;
		PVector v = new PVector(e.getX(), e.getY());
		//System.out.println("mouseEvent() called with action "+e.getAction());
		switch (e.getAction()) {
		case MouseEvent.PRESS:
			layerController.pressed(v);
			break;
		case MouseEvent.RELEASE:
			layerController.released(v);
			break;
		case MouseEvent.CLICK:
			if(e.getClickCount()==1){
				layerController.clicked(v);
			}
			else if(e.getClickCount()==2){ 
				layerController.dblClicked(v);
			}
		case MouseEvent.DRAG:
			layerController.dragged(v);
			break;
		case MouseEvent.MOVE:
			layerController.moved(v);
			break;
		}
	}



	public void keyEvent(KeyEvent e) {
	}

	public void dispose() {
	}

	//	public void controlEvent(ControlEvent e){
	//		//
	//		System.out.println("Control event in MorphOSC from "
	//						+ e.getController().getName());
	//				System.out.println("Controller no. is "
	//						+ e.getController().getId());
	//
	////		int index = getMPIndexById(e.getController().getId());
	////				System.out.println("...index is "+index);
	////		if(index!=-1 && e.isController()	&& e.getName()!="Lock"){
	////			mpList.get(index).setVZValue(e.getController().getValue());
	////		}
	////		else if(index!=-1 && e.isController() && e.getName()=="Lock"){
	////			IsLocked= !IsLocked;
	////		}
	//	}

	protected void addMouseHandler(){
		layerController = new LayeringController(this);

	}

	protected void setMouseVector(PVector mv_) {
		mouseVector = mv_;

	}

	protected PVector getMouseVector() {
		return mouseVector;

	}

	private void addView(){
		gui= new LayeringView(parent, this);		
	}

	private void addOSCAgent(){
		//		oscA = new OSCAgent(this);

	}

	//	protected void relayOSCMessage(OscMessage msg_){
	////		oscA.setMessage(msg_);
	////		oscA.send();
	//	}

	/////////////////////////////////////////////////////////////////////Public Methods

	public void setUseSoftwareBus(boolean in){
		useSoftwareBus=in;	
	}

	public boolean getUseSoftwareBus(){
		return useSoftwareBus;	
	}

	public void setUseOSCAgent(boolean in){
		useOSCAgent=in;	
	}

	public boolean getUseOSCAgent(){
		return useOSCAgent;	
	}

	//	public boolean getVerboseMode(){
	//
	////		return gui.verboseMode;
	//	}

	public void setVerboseMode(boolean mode_){

		//		gui.verboseMode=mode_;
	}

	//	public int getOSCListenPort(){
	//		
	////		return oscA.listenPort;
	//	}
	public void setOSCListenPort(int listenPort_) {
		//		oscA.listenPort = listenPort_;
		//println("Changing OSC listening port to "+listenPort);
		//		oscA.portInit();
	}
	//	public int getOSCSendPort(){
	//
	////		return oscA.sendPort;
	//	}

	public void setOscSendPort(int sendPort_) {
		//		oscA.sendPort = sendPort_;
		//		//println("Changing OSC sending port to "+sendPort);
		//		oscA.addrInit();
	}
	//	
	//	public String getOSCLocalAddr(){
	//		return oscA.localAddrString;
	//		
	//	}
	//	
	//	public void setOSCLocalAddr(String localAddrString_) {
	//		oscA.localAddrString =localAddrString_;
	//		//println("Changing OSC local address to "+localAddrString);
	//		// No need to re-initalise OSC as listen port is not being changed.
	//	}
	//	
	//	public String getOSCRemoteAddr(){
	//		return oscA.localAddrString;
	//		
	//	}

	//	public void setOSCRemoteAddr(String remoteAddrString_) {
	//		oscA.remoteAddrString =remoteAddrString_;
	//		//println("Changing OSC remote address to "+remoteAddrString);
	//		oscA.addrInit();
	//	}
	//	public void verifyOSC(){
	//		OscMessage verify = new OscMessage("OSC communication verify test");
	//		relayOSCMessage(verify);
	//	}

	public void setAndroidMode(boolean bIn_){
		//TODO: Use to determine subset of interactions in Controller 
		//for Android e.g. replacement for mouseMoved etc.
		if(bIn_){
			System.out.println("Android mode set");

		}
		else {
			System.out.println("Desktop mode set");
		}
	}



}
