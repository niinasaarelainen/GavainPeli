import scala.swing._
import java.awt.Color
import java.awt.event._
import java.awt.image._
import java.io._
import javax.imageio._
import scala.swing.event.KeyTyped
import java.awt.{Color,Graphics2D,BasicStroke,Font}

import scala.collection.mutable.Map


 object GavainPeli extends SimpleSwingApplication{
  
  def top = new MainFrame {
    title    = "tunnista nuotti G-avaimella"
    contents = new kuvaPaneeli
    size     = new Dimension(500, 580)
  }



}
  
class kuvaPaneeli extends Panel {
   
  var score = 0  
  var kysytty = 0
  var oikein =  false
  val oikeaVastaus = Map(10 -> "c", 9 -> "d", 8 -> "e", 7 -> "f",  6 -> "g", 5 -> "a", 4 -> "bh", 
      3 -> "c", 2 -> "d", 1 -> "e", 0 -> "f")  
    
//  clef = new BufferedImage(20, 90, BufferedImage.TYPE_INT_ARGB)   // numerot eivät vaikuta mihinkään
   var clef = ImageIO.read(new File("treble_clef_small.png"))  
   var note = ImageIO.read(new File("whole_note_small.png")) 
   var thumb = ImageIO.read(new File("Thumbs_up_small.png")) 
  
  this.listenTo(keys)
  this.reactions += {
  //  case MouseClicked(_, p, _, _, _) => mouseClick(p.x, p.y)
    case KeyTyped(_, c, _, _) => 
      if ('a' <= c && c <= 'h') {    // testataan niin että  h--> b !!!!!!!!!!
	       pisteet(c)
      }
  }
  focusable = true
            requestFocus
  
   var y = (Math.random()*10).toInt +1  // aluksi on jo nuotti näkyvillä  
   println("eka y: " + this.y)
            
   def pisteet(c: Char) = {      
      println("c.toInt: " + c.toInt + ", oikVast: " + oikeaVastaus(c.toInt - 97) + ", y: " + this.y)      
      if (oikeaVastaus(y).contains(c.toString)){
         this.score += 1
         oikein = true
      } else oikein = false  
      this.kysytty += 1   
      this.y = (Math.random()*10).toInt +1   
      this.repaint
      
   }
  
  // 8 = e1, 7 = f1, 4 = h1, 10 = c1, TEE APUVIIVa   TODO
 
 
 
  override def paintComponent(g: Graphics2D) = {    // 102 = c2

    g.drawImage(clef, 0, 0, null)   //  The last parameter is an ImageObserver which we won't use here
    g.drawImage(note, 220, 12 +30* this.y, null) 
    if (oikein)  g.drawImage(thumb, 320, 450, null) 
    for(i<- 1 to 5)
       g.fillRect(50, 60*i, 400, 2)
    g.setFont(new Font("Batang", Font.PLAIN, 20))
    g.setColor(Color.GREEN)
    g.drawString("Oikein: " + this.score + "/" + this.kysytty, 199, 460)   
  }
  
 
}