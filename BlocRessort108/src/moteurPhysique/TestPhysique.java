package moteurPhysique;

/**
 * 
 * @author Felix Boucher
 *classe pour tester le moteur physique
 */
public class TestPhysique {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double gravite = 9.8066;
		double m = 0.7;
		double u = 0.64;
		double deltaT = 8/1000.0;
		double k = 500;
		Vecteur vitesse = new Vecteur(-0.0000001, 0); 
		double temps = 0;
		Vecteur position = new Vecteur(0.8, 0);
		Vecteur accel = new Vecteur(0, 0);


		for (int i=0; i<4; i++) {
			temps+=deltaT;
			System.out.println("temps : " + temps);
			
			MoteurPhysique.unPasEuler(deltaT, position, vitesse, accel, k, u, m, position.getX());
			
			
		}



	}

}
