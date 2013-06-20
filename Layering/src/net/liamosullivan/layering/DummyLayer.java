package net.liamosullivan.layering;


import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class DummyLayer extends InteractiveLayer
{
	PApplet parentApplet;
//	protected ArrayList<MorphParameter> mpList = new ArrayList();
//	//private int nMPs = 0; //number of MPs currently in layer
//	protected ArrayList<MorphAnchor> maList = new ArrayList();
//	//int nMAs = 0; //number of MAs currently in layer
	//List<Float> distances = new ArrayList();


	

	DummyLayer(PApplet p_, int id_, int x_, int y_, int w_, int h_, int dispW_, int dispH_)
	{
		super(p_, id_, x_, y_, w_, h_, dispW_, dispH_);
		
	}

//	public void addMorphParameter(MorphParameter mp_)
//	{
//		MorphParameter mp = mp_;
//		if(!mpList.contains(mp)){
//			this.mpList.add(mp);
//			//System.out.println("MorphParam "+mp.getId()+" added to layer "+ this.id);
//			//System.out.println("Current Value is "+mp.getValue());
//		}
//		else{
//			//System.out.println("MorphParam "+mp.getId()+" already in layer "+ this.id);
//			//System.out.println("Current Value is "+mp.getValue());
//		}
//	}

//	public void removeMorphParameter(int idRemove_) {
//		int idRemove = idRemove_;
//	}

//	protected ArrayList getMPList(){
//		return mpList;
//	}

//	protected int getNMPs(){
//		return mpList.size();
//	}

//	protected void addMorphAnchor(MorphAnchor ma_)
//	{
//		MorphAnchor ma = ma_;
//		PVector av = ma.getPosition();
//		ma.setPosition(new PVector (av.x-lx, av.y-ly)); //correct anchor position position relative to layer axes
//		//ma.setMorphParameterValueById(id_, val_);
//		this.maList.add(ma);
//		System.out.println("MorphAnchor "+ma.getId()+" added to layer "+ this.id);
//
//	}

//	protected void removeMorphAnchor(int idRemove_) {
//		//System.out.println("Remove MA call");
//		int idRemove = idRemove_;
//		for(int i=0; i<maList.size();i+=1){
//			if(maList.get(i).getId()==idRemove){
//				maList.remove(i);
//				System.out.println("Removing MA#"+idRemove+"from position #"+i+" in maList");
//				//if this is the last MA, remove the MP too
//				if(maList.size()==0&&removeMPWithLastMA){
//					//removeMorphParameter(); //TODO: handle MPs when last MA removed
//				}
//				break;
//			}
//
//		}
//
//
//	}

//	void moveMorphAnchor(int ix_, int iy_)
//	{
//		//int ix = ix_;
//		//int iy = iy_;
//	}
//	protected ArrayList getMAList(){
//		return maList;	
//
//	}
//	public int getNMAs(){
//		return maList.size();
//	}

//	void displayInterp() {
//		this.parentApplet.pushMatrix();
//		this.parentApplet.translate(this.lx, this.ly);
////		for (int j = 0; j < this.distances.size(); j++) {
////			float f = ((Float)this.distances.get(j)).floatValue();
////		}
//
//		this.parentApplet.popMatrix();
//	}
//
//	boolean checkOnPNode(int x_, int y_) {
//		for (int j = 0; j < this.maList.size(); j++) {
//			//MorphAnchor localMorphAnchor = (MorphAnchor)this.maList.get(j);
//		}
//		return false;
//	}

//	PShape getActiveArea() {
//		PShape ps = new PShape();
//		return ps;
//	}

//	protected MorphParameter[] interpolate(PVector p_) {
//		PVector P = p_;
//		//float[] iParams = new float[mpList.size()];
//		float [] d = getInvDistances(P);
//		float[] was = getWeightedAves(d);
//		MorphParameter [] interpMP = new MorphParameter[mpList.size()];
//		for(int i=0;i<mpList.size();i+=1){
//			interpMP[i] = new MorphParameter(parent, mpList.get(i).getId(),mpList.get(i).getName());
////			interpMP[i].setId(mpList.get(i).getId());
////			interpMP[i].setName(mpList.get(i).getName());
//			interpMP[i].setValue(was[i]);
//		}
//		return interpMP;
//	}

//	private float[] getInvDistances(PVector p_) {
//		PVector pv = p_;
//		pv.x=pv.x-lx; //correct for position of layer
//		pv.y=pv.y-ly;
//		float[] dists = new float[maList.size()];
//		//System.out.print("Distance Values :");
//		for (int i = 0; i < maList.size(); i++) {
//			MorphAnchor ma = (MorphAnchor)this.maList.get(i);
//			dists[i] = PVector.dist(pv, ma.p);
//			//System.out.print("\t " +parent.nf(dists[i], 2, 2));
//		}
//		System.out.println();
//		float mx = PApplet.max(dists);
//		//System.out.println("max: "+mx);
//		//System.out.print("Inverse Distance Values :");
//		for (int i=0; i<maList.size(); i+=1) {
//			dists[i] = dists[i]/mx;      //normalise
//			dists[i] = 1.0F/dists[i];     //invert
//			//System.out.print("\t " +parent.nf(dists[i], 2, 2));
//		}
//		//System.out.println();
//		return dists;
//	}

//	float[] getWeightedAves(float[] d_) {
//		float[] d = d_; //the normalised distances from the interpolation point to each anchor
//		float[] wOut = new float[maList.get(0).getMPList().size()]; //weighted output for each parameter added to anchors
//		float wSum = 0.0F;
//		//Multiply MP value by weights
//		//System.out.println("Weightings: ");
//		for(int i =0;i<maList.size();i+=1){
//			MorphAnchor ma = maList.get(i);
//			wSum += d[i];
//			for (int j = 0; j < ma.valueList.size(); j++) {
//				float val = ma.getMPValueByIndex(j);
//				//MorphParameter mp = (MorphParameter) maList.get(i).getMPList().get(j);
//				//System.out.println("Anchor #"+i+" MP index"+j+" has value "+val);
//				wOut[j] += d[i] * val;
//			}
//		}
//					
//		for (int i = 0; i < wOut.length; i++) {
//			wOut[i] /= wSum;
//		}
//		//System.out.println();
//		return wOut;
//	}

	//@Override
//	public void resizeContents(PVector sf_)
//	{
//		PVector scaler = sf_;
//		//System.out.println("scaleX "+scaler.x+"scaleY "+scaler.y);
////		for(int i =0;i<maList.size();i+=1){
////			MorphAnchor ma = maList.get(i);
////			ma.setPosition(new PVector (ma.getPosition().x*=scaler.x, ma.getPosition().y*=scaler.y));
////		}
//
//
//	}
	
}