package moteurPhysique;

/**
 * Cette classe regroupera les calculs physiques nécessaires au mouvement des objets des divers objets dans la scène. 
 *  
 * @author Felix Boucher
 *classe pour calculer tout les éléments physiques
 */
public class MoteurPhysique {
	private final static double gravite = 9.8066;
	private static Vecteur forceRappel = new Vecteur ();
	
	private static Vecteur forceFriction = new Vecteur();
	public static Vecteur sommeForce = new Vecteur();
	
	public MoteurPhysique() {
		
	}

	/**
	 * calcule les forces et l'acceleration afin de mettre à jour la position et la vitesse
	 * @param deltaT
	 * @param position
	 * @param vitesse
	 * @param accel
	 * @param k
	 * @param u
	 * @param m
	 * @param etirement
	 */
	public static void unPasEuler(double deltaT, Vecteur position, Vecteur vitesse, Vecteur accel, double k, double u, double m, double etirement ) {

		double normeRappel = -k * etirement;
		forceRappel.setX(normeRappel);
		
		double normeFriction = -1 * u * m * gravite * vitesse.getX()/Math.abs(vitesse.getX());
		forceFriction.setX(normeFriction);
		
		sommeForce.setX(forceRappel.getX() + forceFriction.getX());
		accel.setX(sommeForce.getX()/m);
				
		System.out.println("rappel : " + forceRappel.getX());
		System.out.println("friction : " + forceFriction.getX());
		System.out.println("accel : " + accel.getX());
		
		
		Vecteur deltaVitesse = Vecteur.multiplie(accel, deltaT);
		Vecteur resultV = vitesse.additionne( deltaVitesse ); 
		vitesse.setX(resultV.getX());	//on change le vecteur vitesse
		System.out.println("vitesse : " + vitesse.getX());
		
	
		Vecteur deltaPosition = Vecteur.multiplie(vitesse, deltaT);
		Vecteur resultP = position.additionne(deltaPosition); //on calcule la position en considerant la nouvelle vitesse
		position.setX(resultP.getX());  //on change le vecteur position
		System.out.println("position : " + position.getX());
		

	}//fin méthode



}
