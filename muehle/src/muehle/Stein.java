package muehle;

public class Stein {
	
	/*@id ist einen Zahl, wo das Stein in der Feld "steinen" von Spielregelen Klass gespeichert wird.
	 *@steinY position von Stein in der Y-Achse.
	 *@steinX position von Stein in der X-Achse.
	 *@farbe ist einen Char mit 'r' oder 'b', sodass man die Farbe von der Srein bestimmen kann, oder 
	 *zum welcher Spieler gehört. 
	 */
	public int id;
	public int steinY;
	public int steinX;
	public char farbe;
	
	
	
	public Stein(int i,int x, int y, char f) {
		this.id = i;
		this.steinX = x;
		this.steinY = y;
		this.farbe = f;
	}


	public void setSteinPosition(int x,int y) {
		this.steinX = x;
		this.steinY = y;
	}
	
	public void getSteinPosition() {
		System.out.println("x= " + this.steinX + "; y= " + this.steinY );
	}

}
