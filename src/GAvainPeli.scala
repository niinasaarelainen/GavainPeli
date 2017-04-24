import scala.swing._
import scala.swing.event._

import java.awt.Color
import java.awt.image._
import java.io._
import javax.imageio._
import java.awt.{Color,Graphics2D,BasicStroke,Font}

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

 object GavainPeli extends SimpleSwingApplication{
  
   def top = new MainFrame {
      title    = "nuotit G-avaimella"
      contents = new kuvaPaneeli
      size     = new Dimension(500, 580)
   }


}
  
class kuvaPaneeli extends Panel {
   
  var nuotitYAkselilla = Buffer[Int]()
  var score = 0  
  var kysytty = 0
  var oikein =  false
  var randomNuottienMaara = 13
  var aluettaSiirretaanNollasta = 0  // offset
  var mouseClickedCount = 0
  val oikeaVastaus = Map(12 -> "c", 11 -> "d", 10 -> "e", 9 -> "f", 8 -> "g", 7 -> "a",  6 -> "h", 5 -> "c", 4 -> "d", 
      3 -> "e", 2 -> "f", 1 -> "g", 0 -> "a")    // alin 12=keski-c; ylin 0 = a2
    
//  clef = new BufferedImage(20, 90, BufferedImage.TYPE_INT_ARGB)   // numerot eivät vaikuta mihinkään
   var clef = ImageIO.read(new File("treble_clef_small.png"))  
   var note = ImageIO.read(new File("whole_note_small.png")) 
   var thumb = ImageIO.read(new File("Thumbs_up_small.png")) 
  
  this.listenTo(keys)
 // this.listenTo(this.mouse)
  listenTo(mouse.clicks)
  this.reactions += {
    case MouseClicked(_, p, _, _, _) =>  mouseClickedCount += 1
                                         println("Mouse clicked at y: " + (p.y/ 30 -2) )   //mouseClick(p.x, p.y)
                                         nuotitYAkselilla += p.y / 30 -2  
                                         if (mouseClickedCount == 2) klikkaustenTulos()
    case KeyTyped(_, c, _, _) => 
      if ('a' <= c.toLower && c.toLower <= 'h') {   
	       pisteet(c)
	       println(y)
      }
  }
  focusable = true
            requestFocus
  
   var y = (Math.random()*randomNuottienMaara).toInt   // aluksi on jo nuotti näkyvillä  
            
   def pisteet(c: Char) = {      
      //  println("c.toInt: " + c.toInt + ", oikVast: " + oikeaVastaus(c.toInt - 97) + ", y: " + this.y)      
        if (oikeaVastaus(y).contains(c.toLower.toString)){
           this.score += 1
           oikein = true
        } else oikein = false  
        this.kysytty += 1   
        this.y = (Math.random()*randomNuottienMaara).toInt  + aluettaSiirretaanNollasta 
        this.repaint
   }
  
  
  def klikkaustenTulos() = {
      randomNuottienMaara = Math.abs(nuotitYAkselilla(0)-nuotitYAkselilla(1)) +1
      println(randomNuottienMaara)
      aluettaSiirretaanNollasta = Math.min(nuotitYAkselilla(0), nuotitYAkselilla(1)) +1 
      println(aluettaSiirretaanNollasta)
  }
  
  
 
  override def paintComponent(g: Graphics2D) = {    
   
    g.drawImage(clef, 0, 0, null)   //  The last parameter is an ImageObserver which we won't use here
    g.drawImage(note, 245, 12 +30* this.y, null) 
    if (y == 12) g.fillRect(235, 60*7, 125, 3)       // apuviivat
    if (y == 0) g.fillRect(235, 60, 125, 3)  
    if (oikein)  g.drawImage(thumb, 350, 450, null) 
    
    for(i<- 1 to 5)                                // viivaston viivat
       g.fillRect(50, 60 + 60*i, 400, 3)
       // g.fillRect(50, 60*i, 400, 3)    kun alue oli c1-g2
    g.setFont(new Font("Batang", Font.PLAIN, 22))
    g.setColor(Color.GREEN)
    if(kysytty == 0)
       g.drawString("Mikä nuotti? Vastaus näppäimistöltä.", 109, 480)   
    else 
       g.drawString("Oikein:  " + this.score + " / " + this.kysytty, 210, 480)   
  }
  
 
}