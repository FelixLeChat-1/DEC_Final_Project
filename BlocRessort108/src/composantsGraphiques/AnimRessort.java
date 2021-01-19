package composantsGraphiques;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import ecouteurs.RepaintListener;
import geometrie.BlocRessort;
import modeleaffichage.ModeleAffichage;
import moteurPhysique.MoteurPhysique;
import moteurPhysique.Vecteur;

/**
 * 
 * @author Daniel Chahfe et Felix Boucher
 *
 */
public class AnimRessort extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;

	private final int TEMPS_SLEEP = 25;
	private double deltaT = 8/1000.0;

	private double dimBloc = 0.4;

	private double xInit = 1.50;
	private Vecteur posX = new Vecteur(xInit, 0);

	private double tempsTotalEcoule = 0;
	private boolean animEnCours = false;
	private boolean pauseAccessible = false;

	private double largeur = 3;
	private double hauteur;

	private ModeleAffichage modeleAffichage;
	private BlocRessort blocRessort;
	public MoteurPhysique moteur;

	private ArrayList<RepaintListener> listeEcouteurs = new ArrayList<RepaintListener>();

	/**
	 * Constructeur de la scene
	 */
	// Daniel Chahfe
	public AnimRessort() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(600, 400));

		blocRessort = new BlocRessort(largeur, dimBloc, posX.getX());
		modeleAffichage = new ModeleAffichage(600, 400, largeur);
		hauteur = modeleAffichage.getHautUnitesReelles();
	}


	/**
	 * methode qui dessine la scene
	 */
	//Daniel Chahfe

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform atr = modeleAffichage.getMatMC();
		
		dessinerAxe(g2d, atr);

		g2d.setColor( Color.black );
		g2d.setStroke(new BasicStroke(7));



		blocRessort.setX(posX.getX());
		blocRessort.dessiner(g2d, atr);

		Line2D plancher = new Line2D.Double(0, hauteur/2 + dimBloc, getWidth(), hauteur/2 + dimBloc);

		g2d.draw(atr.createTransformedShape(plancher));




		//
		for (RepaintListener objEcout: listeEcouteurs ) {
			objEcout.tempsListen(tempsTotalEcoule);
			objEcout.vitesseListen(blocRessort.getVitesse());
			objEcout.positionListen(blocRessort.getPosX());
			objEcout.accelListen(blocRessort.getAccel());
			objEcout.frictionListen(blocRessort.getForceFriction());
			objEcout.rappelListen(blocRessort.getForceRappel());
			objEcout.pauseAccListen(pauseAccessible);
		}
		//
	}
	//felix boucher
	private void dessinerAxe(Graphics2D g2d, AffineTransform atr) {
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		g2d.drawString("0", getWidth()/2 + 4, getHeight() - 2);
	}
	/**
	 * methode qui calcule une iteration physique, donc met à jour toutes les valeurs avec un deltaT
	 */
	//Felix Boucher
	public void calculerIterationPhysique() {
		tempsTotalEcoule += deltaT;
		System.out.println("\nTemps total écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé)");
		blocRessort.unPasEuler(deltaT);

	}

	/**
	 * Methode qui change le vecteur position du bloc
	 * @param posX le vecteur position du bloc
	 */
	//  Daniel Chahfe
	public void setPosX(Vecteur posX) {
		this.posX = posX;
		blocRessort.setPosX(posX);
	}


	/**
	 * mehtode qui gere l'animation
	 */
	// Felix Boucher(calcul de x1 et x2 afin d'un arrêt au bon moment) et Daniel Chahfe (structure et methodes repaint, calculerIteration)
	@Override
	public void run() {
		while (animEnCours) { 
			double x1, x2;

			x1= posX.getX();
			posX.setX(blocRessort.getPosX().getX());

			calculerIterationPhysique();

			x2 = posX.getX();

			repaint(); 

			if (Math.abs(x2-x1)<0.00001) {
				System.out.println("X2-X1 :" + Math.abs(x2-x1));
				animEnCours = false;
			}

			try {
				Thread.sleep(TEMPS_SLEEP);
			} 
			catch (InterruptedException e) {
				System.out.println("Erreur pendant le sleep de l'animation");
			}

		}

		System.out.println("L'animation est terminée");

	}//fin methode


	/**
	 * methode qui demarre l'animation
	 */
	//  Daniel Chahfe
	public void demarrer() {
		if (!animEnCours) {
			Thread proc = new Thread(AnimRessort.this);
			proc.start();
			animEnCours = true;
			pauseAccessible = true;
		}
	}//fin methode

	/*
	 * @param objEcout le repaintListener
	 */
	//Daniel Chahfe
	public void addRepaintListener( RepaintListener objEcout) {
		listeEcouteurs.add(objEcout);
	}

	/**
	 * methode qui est appelee lorsqu'un clic est fait sur la composante scene
	 */
	// Daniel Chahfe
	public void click() {
		demarrer();
	}
	/**
	 * terminer animation
	 */
	//felix boucher
	public void arreter() {
		animEnCours = false;

	}
	/**
	 * remettre les valeurs par defaut
	 */
	//felix boucher
	public void reset() {
		posX = new Vecteur (xInit, 0);
		animEnCours = false;
		pauseAccessible = false;
		blocRessort.setKBloc(500);
		blocRessort.setMBloc(0.7);
		blocRessort.setUcBloc(0.64);
		deltaT = 8.0/1000.0;
		tempsTotalEcoule = 0;
		setPosX(posX);
		blocRessort.setPosX(posX);
		blocRessort.setVitesse(new Vecteur(-0.0000001,0));
		blocRessort.setRappel(0, 0);
		repaint();	

	}
	//felix boucher
	public void setK(double k) {
		blocRessort.setKBloc(k);
	}
	//felix boucher
	public void setM(double m) {
		blocRessort.setMBloc(m);
	}
	//felix boucher
	public void setUc(double uc) {
		blocRessort.setUcBloc(uc);
	}
	//felix boucher
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}
	//felix boucher
	public void setU(double u) {
		blocRessort.setUc(u);
	}

	public double getLargeur() {
		return largeur;
	}

	public Vecteur getPosX() {
		return posX;
	}

	public ModeleAffichage getModeleAffichage() {
		return modeleAffichage;
	}

	public double getDimBloc() {
		return dimBloc;
	}

	public boolean isAnimEnCours() {
		return animEnCours;
	}

	public double getEtirement() {
		return blocRessort.getEtirement();
	}

	public void setEtirement(double etir) {
		blocRessort.setEtirement(etir);
	}

	public double getTempsTotalEcoule() {
		return tempsTotalEcoule;
	}

	public double getxInit() {
		return xInit;
	}



	public void setPauseAcc(boolean b) {
		pauseAccessible = b;
	}



}
