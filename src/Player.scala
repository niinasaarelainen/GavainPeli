import javax.sound.midi.MidiSystem

class Player  {
  
    val synth = MidiSystem.getSynthesizer()
    val channels  =  synth.getChannels()
		val ch1 = channels(0)
		val MIDINoteNumber = Map(12 -> 60, 11 -> 62, 10 -> 64,   9-> 65, 8 -> 67,  7 -> 69, 6 -> 71,
       5-> 72, 4 -> 74, 3 -> 76, 2 -> 77, 1 -> 79, 0 -> 81 ) //  0=81= a2
       
    synth.open   
       
    def soita(korkeus: Int) = {   
        ch1.programChange(10)   
        ch1.noteOn(MIDINoteNumber(korkeus), 100)  // 100= velocity   
        Thread.sleep(400)
        ch1.noteOff(MIDINoteNumber(korkeus))
    }
}