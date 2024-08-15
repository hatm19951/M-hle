package muehle;

public class Stein {
	
	public String name;
	public int steinY;
	public int steinX;
	public char farbe;
	
	public Stein[] steinen = new Stein[18];
	
	public Stein(String n,int x, int y, char f) {
		this.name = n;
		this.steinY = y;
		this.steinX = x;
		this.farbe = f;
	}


	public void setSteinPositionX(int x) {
		this.steinX = x;
	}
	
	public void setSteinPositionY(int Y) {
		this.steinX = Y;
	}
	
	public void getSteinPosition() {
		System.out.println("x= " + this.steinX + "; y= " + this.steinY );
	}
	
	// Erstellung von 18 Stein
	public void erstellenSteinen() {
			for(int i =0; i< 18; i++) {
				if (i<=8) {
					steinen[i] = new Stein("s"+i+1, i,i,'r');
				} 
				else {
					steinen[i] = new Stein("s"+i+1,i,i,'b');
				}
			}
		}
	//Nachprüfung, ob es drei Steinen nebeneinander	sind.
	public void positionPrüfen(Stein[] steinen) {
		int x=0;
		int y=0;
		for(Stein i: steinen ) {
			if(i.steinX == 0) {
				
			}
		}
	}
	
	
}