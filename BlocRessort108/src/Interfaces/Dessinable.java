package Interfaces;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/**
 * interface qui permet de creer des objets dessinables
 * @author Daniel Chahfe
 *
 */
public interface Dessinable {
	public void dessiner(Graphics2D g2d, AffineTransform mat);
}
