package muehle;

public class Spielreglen {
	
	// einen Feld, in dem die Information von jeder Stein gespeichert
	public static Stein[] steinen = new Stein[18];
	
	
	public Spielreglen() {
		erstellenSteinen();
		gleichX();
	}
	
	/* Erstellung von 18 Stein, die in der Anfang auf linke und richte Seiten obereinander liegen.
	 * anfangPositionX und anfangPositionY ist abhängig von der Framesgroße.
	 * In der For-Schleife wird die Steinen mit ID 0-8 in der linken Seite von dem Spielfeld mit Farbe rot geliegt und
	 * Steinen mit ID 9-17 in der rechten Seite mit Farbe blau geliegt.
	 */
	public void erstellenSteinen() {
		int anfangPositionX = 10;
		int anfangPsoitionY1 = 10;
		int anfangPsoitionY2 = 10;
			
		for(int i =0; i< 18; i++) {
				
			if (i<=8) {
				steinen[i] = new Stein(i, 10,anfangPsoitionY1,'r');
				anfangPsoitionY1=anfangPsoitionY1+10;
			} 
				else {
					steinen[i] = new Stein(i,500,anfangPsoitionY2,'b');
					anfangPsoitionY2=anfangPsoitionY2+10;
				}
		}
	}
	/*Methode zu bestimmen, ob Steinen auf die gleichen X-achse stehen
	 * 
	 */
	public int gleichX() {
		int a =0;
		
		int[] postionenY = {50,100,150,250,350,400,450};
		int[] postionenX = {50,100,150,250,350,400,450};
		
		for(int i=0; i <18; i++) {
			for(int j=0;j<18;j++ ) {
				if ( i != j && steinen[i].steinX == steinen[j].steinX && 10 < steinen[i].steinX && steinen[i].steinX < 500  ) {
					System.out.println("i= "+i +" | j= "+ j);
					a++;
				}
			}
		}
		return a/2;
		
	}
		
}