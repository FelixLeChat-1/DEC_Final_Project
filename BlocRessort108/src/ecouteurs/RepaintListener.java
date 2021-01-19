package ecouteurs;

import java.util.EventListener;

import moteurPhysique.Vecteur;
/**
 * 
 * @author Daniel Chahfe
 *
 */
public interface RepaintListener extends EventListener {
		public void tempsListen(double temps);
		public void vitesseListen(Vecteur vitesse);
		public void positionListen(Vecteur position);
		public void accelListen(Vecteur accel);
		public void frictionListen(Vecteur friction);
		public void rappelListen(Vecteur rappel);
		public void pauseAccListen(boolean pauseAcc);
}
