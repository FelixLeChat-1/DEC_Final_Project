package geometrie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Interfaces.Dessinable;
import modeleaffichage.ModeleAffichage;
import moteurPhysique.Vecteur;
import moteurPhysique.MoteurPhysique;

/**
 * 
 * @author Daniel Chahfe et Felix Boucher
 * classe qui contient l'objet dessinable blocRessort
 */
public class BlocRessort implements Dessinable {

	private ModeleAffichage modeleAffichage;
	private double largeur;
	private double hauteur;
	private double dimBloc;
	private double x;
	private Image img = null;
	private double gravite = 9.8066;

	private double k = 500;
	private double m = 0.7;
	private double uc = 0.64;


	private double xInit = 1.50;
	private double etirement;

	public Vecteur posX = new Vecteur(xInit, 0);
	private Vecteur vitesse = new Vecteur(-0.0000001, 0);
	private Vecteur accel = new Vecteur(0, 0);
	private Vecteur forceRappel = new Vecteur (0,0);
	private Vecteur forceFriction = new Vecteur (0,0);
	private double facteurVecteur = 1.5;
	

	/**
	 * constructeur de blocRessort
	 * @param l importe la composante largeur de la scene d'animation
	 * @param dB importe la composante dimention du bloc
	 * @param X importe la composante position x du bloc
	 */
	public BlocRessort(double l, double dB, double X) {
		this.x = X;
		dimBloc = dB;
		largeur = l;
		modeleAffichage = new ModeleAffichage(600, 400, largeur);
		hauteur = modeleAffichage.getHautUnitesReelles();
		etirement =  posX.getX() - xInit;
		lireImage();
	}

	/**
	 * methode qui dessine le bloc et le ressort
	 * @param g2d composante de dessin
	 * @param mat matrice de transformation
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		g2d.setColor( Color.black );
		g2d.setStroke(new BasicStroke(7));
		AffineTransform matLocale = modeleAffichage.getMatMC();

		setRappel(k, etirement); 

		Rectangle2D bloc = new Rectangle2D.Double(x-dimBloc/2, hauteur/2, dimBloc, dimBloc);
		
		

		Image scaledImg = img.getScaledInstance((int) ((x-dimBloc/2)*modeleAffichage.getPixelsParUniteX()), (int) (dimBloc*modeleAffichage.getPixelsParUniteY()), 0);
		g2d.drawImage(scaledImg, 0,  (int) (hauteur/2*modeleAffichage.getPixelsParUniteY()), null);

		g2d.draw(matLocale.createTransformedShape(bloc));

		
		dessinerFleche(g2d, matLocale);
		
		g2d.setColor( Color.black );
		g2d.setStroke(new BasicStroke(7));




	}/**
	*
	*dessine la fleche vecteur force
	*@param g2d, matLocale 
	*
	**/
	//felix boucher
	private void dessinerFleche(Graphics2D g2d, AffineTransform matLocale) {
		Path2D fleche = new Path2D.Double();
		Path2D pointeH = new Path2D.Double();
		Path2D pointeB = new Path2D.Double();

		fleche.moveTo(x , hauteur/2+dimBloc/2);
		
		if ((forceRappel.getX()/Math.abs(forceRappel.getX()) == 1)){
			
			fleche.lineTo(x - 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			pointeH.moveTo(x - 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			pointeB.moveTo(x - 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			
			pointeH.lineTo(x - 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur+0.1, hauteur/2+dimBloc/2+0.1);
			pointeB.lineTo(x - 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur+0.1, hauteur/2+dimBloc/2-0.1);
			
			
		}else {
			fleche.lineTo(x + 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			pointeH.moveTo(x + 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			pointeB.moveTo(x + 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur, hauteur/2+dimBloc/2);
			
			pointeH.lineTo(x   + 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur-0.1, hauteur/2+dimBloc/2+0.1);
			pointeB.lineTo(x + 1.5*Math.abs(forceRappel.getX())/1000*facteurVecteur-0.1, hauteur/2+dimBloc/2-0.1);
		}
		
		
		
		if ( Math.abs(forceRappel.getX())>1) {
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor( Color.cyan );
			g2d.draw(matLocale.createTransformedShape(fleche));
			g2d.draw(matLocale.createTransformedShape(pointeH));
			g2d.draw(matLocale.createTransformedShape(pointeB));
			
			}
			
			g2d.setColor( Color.black );
			g2d.setStroke(new BasicStroke(7));
	}

	/**
	 * methode qui change le x
	 * @param x la position x du bloc
	 */
	//Daniel Chahfe
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * methode qui cherche l'image du ressort
	 */
	//Daniel Chahfe
	private void lireImage() {
		URL fich = getClass().getClassLoader().getResource("ressort.png");
		if (fich == null) {
			JOptionPane.showMessageDialog(null,	"Fichier d'image introuvable!");
		} else {
			try {
				img = ImageIO.read(fich);
			} 
			catch (IOException e) {
				System.out.println("Erreur de lecture du fichier d'image");
			}
		}
	}
	/**
	 * appelle la méthode unpaseuler du moteur physique pour mettre à jour les variables
	 * @param deltaT
	 */
	//felix boucher
	public void unPasEuler(double deltaT) {

		setFriction(uc, m);
		setRappel(k, etirement);

		MoteurPhysique.unPasEuler(deltaT, posX, vitesse, accel, k, uc, m, etirement );
		etirement = posX.getX()-xInit;

		
		
	}
	//felix boucher
	public Vecteur getPosX() {
		return posX;
	}
	//felix boucher
	public void setPosX(Vecteur posX) {
		this.posX = posX;

	}

	public double getEtirement() {
		return etirement;
	}
//felix boucher
	public void setEtirement(double etir) {
		this.etirement = etir;
		this.posX = new Vecteur(xInit + etir, 0);
	}

	public void setVitesse(Vecteur vitesse) {
		this.vitesse = vitesse;
	}

	public Vecteur getVitesse() {
		return vitesse;
	}

	public Vecteur getAccel() {
		return accel;
	}
	public void setUc(double uc) {
		this.uc = uc;
	}
	//felix boucher
	public void setRappel(double k, double etirement) {
		forceRappel.setX(-1 * k * etirement);
	}
	//felix boucher
	public void setFriction(double uc, double m) {
		forceFriction.setX(-1 * uc * m * gravite * (vitesse.getX()/Math.abs(vitesse.getX())));
	}
	public void setKBloc(double k) {
		this.k = k;
	}

	public void setMBloc(double m) {
		this.m = m;
	}

	public void setUcBloc(double uc) {
		this.uc = uc;
	}
	public Vecteur getForceRappel() {
		return forceRappel;
	}

	public Vecteur getForceFriction() {
		return forceFriction;
	}

	public double getM() {
		return m;
	}
	
	
	
	
	
}
