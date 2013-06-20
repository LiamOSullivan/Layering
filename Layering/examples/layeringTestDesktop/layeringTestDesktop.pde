/*
Test sketch for experiments with the Layering package
 
 */
import net.liamosullivan.layering.*;

Layering m;

PFont font;
int fps = 50; //target framerate
int fr=0; //actual framerate
void setup() {
  size(800, 600);
  background(0);
  frameRate(fps);
  font=createFont("ArialMT-48.vlw", 48);
  textFont(font, 24);

  m = new Layering(this);
  //  m.setOSCListenPort(8000);
  //  m.setOscSendPort(8001);
  //  //m.setOSCLocalAddr("127.0.0.1");
  //  m.setOSCRemoteAddr("127.0.0.1");
  //  //m.setOSCRemoteAddr("192.168.42.160"); //usb tethering
  //  m.setAndroidMode(false);



  //m.getControllerInfo(); //print the controllers added to MorphOSC only
  m.addLayer(LayerType.DUMMY, width/2, height/2); //MorphLayer can be added in the sketch or via interaction at runtime
  m.addLayer(LayerType.TEXTURE, 100.0F, 100.0F);
  //m.setVerboseMode(false); //set true for more detailed output
  // m.verifyOSC();
}

void draw() {
  //background(0);

  showFramerate();
}



//void controlEvent(ControlEvent e) {
//  if (e.isGroup()) {
//    //do nothing here
//  }
//  else if (e.isController()) {
//    //println("Event from controller "+e.getController().getName());
//    //println("Controller ID "+e.getController().getId());
//  }
//  m.controlEvent(e);
//}

//Callback method for in-sketch control
void morphOSCEvent() {
}

void showFramerate() {
  fill(255);
  textAlign(CENTER);
  textSize(12);
  if (frameCount%5==0) {
    fr= int(frameRate);
  }
  text("fps: " + fr+"/"+fps, width/2, height - 20);
}

