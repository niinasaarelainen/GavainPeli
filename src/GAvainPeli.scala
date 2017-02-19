import scala.swing._
import java.awt.Color
import java.awt.event._
import java.awt.image._
import java.io._
import javax.imageio._


 object GavainPeli extends SimpleSwingApplication{
  
  def top = new MainFrame {
    title    = "tunnista nuotti G-avaimella"
    contents = new kuvaPaneeli
    size     = new Dimension(500, 580)
  }
}

  
  class kuvaPaneeli extends Panel {
    
  var clef = new BufferedImage(20, 90, BufferedImage.TYPE_INT_ARGB)   // numerot eivät vaikuta mihinkään
  clef = ImageIO.read(new File("treble_clef_small.png"))  
  
  var note = ImageIO.read(new File("whole_note_small.png")) 
  
  var y = (Math.random()*10).toInt +1
  println(y)
  
  // 8 = e1, 7 = f1, 4 = h1, 10 = c1, TEE APUVIIVa   TODO
 
  /*  ImageIcon ei piirry paintComponentissa !!!
    val newimg = img.getScaledInstance(100, 150,  java.awt.Image.SCALE_SMOOTH) // scale it the smooth way 
    val clef = new ImageIcon(newimg)  // transform it back
  */

  override def paintComponent(g: Graphics2D) = {    // 102 = c2

    g.drawImage(clef, 0, 0, null)   //  The last parameter is an ImageObserver which we won't use here
     g.drawImage(note, 220, 12 +30*y, null) 
    for(i<- 1 to 5)
       g.fillRect(50, 60*i, 400, 2)
  }
  
 
}