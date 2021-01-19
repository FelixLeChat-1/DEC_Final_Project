package aapplication;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import ecouteurs.RepaintListener;
import moteurPhysique.Vecteur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.event.ChangeListener;
import composantsGraphiques.AnimRessort;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.border.LineBorder;
/**
 * 
 * @author Daniel Chahfe et Felix Boucher
 *
 */
public class Application extends JFrame {

	private JPanel contentPane;
	private JSpinner spnMasse;
	private JSlider sliderEtirement;
	private JSpinner spnTemps;
	private JLabel lblDeltaT;
	private JLabel lblMasse;
	private JLabel lblFriction;
	private JLabel lblEtirement;
	private JLabel lblK;
	private JSpinner spnRessort;
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmQuitter;
	private JRadioButton rdbtnAlu_Alu;
	private JRadioButton rdbtnBronze_Fer;
	private JRadioButton rdbtnNickel_Acier;
	private JRadioButton rdbtnAcier_Plomb;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnAnimer;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JLabel lblX;
	private JLabel lblFrictionX;
	private JLabel lblRappelX;
	private JLabel lblNormaleX;
	private JLabel lblGravitanionnelleX;
	private JLabel lblVitesseX;
	private JLabel lblAccelX;
	private JLabel lblPositionX;
	private JLabel lblFrictionY;
	private JLabel lblRappelY;
	private JLabel lblNormaleY;
	private JLabel lblGravY;
	private JLabel lblVitesseY;
	private JLabel lblAccelY;
	private JLabel lblPositionY;
	private JLabel lblY;
	private JSeparator separator_3;
	/**
	 * Launch the application.
	 */
	//Felix Boucher
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application frame = new Application();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	// Felix Boucher
	public Application() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 827);
		
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		mntmQuitter = new JMenuItem("Quitter");
		mnOptions.add(mntmQuitter);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//danial chafhe
		AnimRessort animRessort = new AnimRessort();
		animRessort.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				animRessort.click();
				animDisable();
				btnAnimer.setEnabled(false);
			}
		});
		animRessort.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if ((e.getX() > 10) && (e.getX() < animRessort.getWidth() - animRessort.getDimBloc()*animRessort.getModeleAffichage().getPixelsParUniteX())) {
					animRessort.setPosX(new Vecteur(e.getX()/animRessort.getModeleAffichage().getPixelsParUniteX(), animRessort.getPosX().getY()));
				}
				animRessort.repaint();
			}
		});
		
		//felix boucher
		animRessort.setBounds(10, 11, 600, 400);
		contentPane.add(animRessort);
		
		spnMasse = new JSpinner();
		spnMasse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				animRessort.setM((double) spnMasse.getValue());
			}
		});
		spnMasse.setModel(new SpinnerNumberModel(0.7, 0.1, 4.0, 0.01));
		spnMasse.setBounds(171, 514, 80, 27);
		contentPane.add(spnMasse);
		
		
		//felix boucher
		spnTemps = new JSpinner();
		spnTemps.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.setDeltaT((double) spnTemps.getValue()/1000.0);
			}
		});
		spnTemps.setModel(new SpinnerNumberModel(8.0, 3.0, 10.0, 0.01));
		spnTemps.setBounds(171, 545, 80, 27);
		contentPane.add(spnTemps);
		
		lblDeltaT = new JLabel("\u0394T (ms)\r\n");
		lblDeltaT.setBackground(Color.BLACK);
		lblDeltaT.setForeground(Color.WHITE);
		lblDeltaT.setBounds(10, 545, 52, 27);
		contentPane.add(lblDeltaT);
		
		lblMasse = new JLabel("Masse (kg)");
		lblMasse.setForeground(Color.WHITE);
		lblMasse.setBackground(Color.BLACK);
		lblMasse.setBounds(10, 520, 80, 14);
		contentPane.add(lblMasse);
		
		lblFriction = new JLabel("Coefficient de friction");
		lblFriction.setForeground(Color.WHITE);
		lblFriction.setBounds(10, 626, 151, 14);
		contentPane.add(lblFriction);
		
		lblEtirement = new JLabel("\u00C9tirement:");
		lblEtirement.setForeground(Color.GREEN);
		lblEtirement.setBounds(10, 424, 67, 14);
		contentPane.add(lblEtirement);
		
		
		//daniel chafhe
		sliderEtirement = new JSlider();
		sliderEtirement.setBackground(Color.BLACK);
		sliderEtirement.setForeground(Color.BLACK);
		sliderEtirement.setMinimum(1);
		sliderEtirement.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.setPosX(new Vecteur((((double) sliderEtirement.getValue()*3/100)), 0));
				btnAnimer.setEnabled(true);
				animRessort.repaint();
			}
		});
		sliderEtirement.setBounds(10, 451, 600, 50);
		contentPane.add(sliderEtirement);
		
		lblK = new JLabel("Constante du ressort (N/m)\r\n");
		lblK.setForeground(Color.WHITE);
		lblK.setBackground(Color.BLACK);
		lblK.setBounds(10, 579, 164, 14);
		contentPane.add(lblK);
		
		//felix boucher
		spnRessort = new JSpinner();
		spnRessort.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				animRessort.setK((double) spnRessort.getValue());
			}
		});
		spnRessort.setModel(new SpinnerNumberModel(500.0, 50.0, 800.0, 10.0));
		spnRessort.setBounds(171, 573, 80, 27);
		contentPane.add(spnRessort);
		
		//felix boucher
		rdbtnAlu_Alu = new JRadioButton("Aluminium - Aluminium (0.4)");
		rdbtnAlu_Alu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				animRessort.setUc(0.4);
			}
		});
		buttonGroup.add(rdbtnAlu_Alu);
		rdbtnAlu_Alu.setBounds(10, 647, 241, 23);
		contentPane.add(rdbtnAlu_Alu);
		
		//felix boucher
		rdbtnBronze_Fer = new JRadioButton("Bronze - Fer (0.22)");
		rdbtnBronze_Fer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.setUc(0.22);
			}
		});
		buttonGroup.add(rdbtnBronze_Fer);
		rdbtnBronze_Fer.setBounds(10, 673, 241, 23);
		contentPane.add(rdbtnBronze_Fer);
		
		//felix boucher
		rdbtnNickel_Acier = new JRadioButton("Nickel - Acier (0.64)");
		rdbtnNickel_Acier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.setUc(0.64);
			}
		});
		rdbtnNickel_Acier.setSelected(true);
		buttonGroup.add(rdbtnNickel_Acier);
		rdbtnNickel_Acier.setBounds(10, 698, 241, 23);
		contentPane.add(rdbtnNickel_Acier);
		
		//felix boucher
		rdbtnAcier_Plomb = new JRadioButton("Acier - Plomb (0.95)");
		rdbtnAcier_Plomb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.setUc(0.95);
			}
		});
		buttonGroup.add(rdbtnAcier_Plomb);
		rdbtnAcier_Plomb.setBounds(10, 724, 241, 23);
		contentPane.add(rdbtnAcier_Plomb);
		
		//daniel chahfe
		Image Animer = new ImageIcon(this.getClass().getResource("/Animer.jpg")).getImage();
		btnAnimer = new JButton(new ImageIcon(Animer));
		btnAnimer.setEnabled(false);
		btnAnimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				animRessort.click();
				animDisable();
				btnAnimer.setEnabled(false);
			}
		});
		btnAnimer.setBackground(Color.BLACK);
		btnAnimer.setForeground(Color.GREEN);
		btnAnimer.setFont(new Font("OCR A Extended", Font.BOLD, 34));
		btnAnimer.setBounds(622, 11, 258, 109);
		contentPane.add(btnAnimer);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(165, 514, 10, 86);
		contentPane.add(separator);
		
		//felix boucher et Daniel Chahfe
		Image Pause = new ImageIcon(this.getClass().getResource("/Pause.jpg")).getImage();
		JButton btnPause = new JButton(new ImageIcon(Pause));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (animRessort.isAnimEnCours()) {
					animRessort.arreter();
				}else {
					animRessort.calculerIterationPhysique();
					animRessort.repaint();
				}
				animDisable();
				btnAnimer.setEnabled(true);
			}
		});
		btnPause.setForeground(Color.GREEN);
		btnPause.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		btnPause.setBackground(Color.BLACK);
		btnPause.setBounds(622, 131, 258, 50);
		contentPane.add(btnPause);
		
		//felix boucher et Daniel Chahfe
		Image Recommencer = new ImageIcon(this.getClass().getResource("/Recommencer.jpg")).getImage();
		JButton btnRecommencer = new JButton(new ImageIcon(Recommencer));
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.reset();
				animEnable();
				btnAnimer.setEnabled(false);
				spnMasse.setValue(0.7);
				spnRessort.setValue((double)500);
				rdbtnNickel_Acier.setSelected(true);
				sliderEtirement.setValue(50);
				
				
			}
		});
		btnRecommencer.setForeground(Color.GREEN);
		btnRecommencer.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		btnRecommencer.setBackground(Color.BLACK);
		btnRecommencer.setBounds(622, 194, 258, 50);
		contentPane.add(btnRecommencer);
		
		//Daniel Chahfe
		Image VDTest = new ImageIcon(this.getClass().getResource("/VDTest.jpg")).getImage();
		JButton btnValuerDeTest = new JButton(new ImageIcon(VDTest));
		btnValuerDeTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animRessort.reset();
				animRessort.setEtirement(0.8);
				animRessort.setPosX(new Vecteur(0.8+1.5, 0));
				animRessort.repaint();
				animEnable();
				btnAnimer.setEnabled(true);
				animRessort.setPauseAcc(true);
			}
		});
		btnValuerDeTest.setForeground(Color.GREEN);
		btnValuerDeTest.setFont(new Font("OCR A Extended", Font.BOLD, 24));
		btnValuerDeTest.setBackground(Color.BLACK);
		btnValuerDeTest.setBounds(622, 257, 258, 50);
		contentPane.add(btnValuerDeTest);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(264, 514, 10, 233);
		contentPane.add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setBackground(Color.BLACK);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(0, 638, 252, 14);
		contentPane.add(separator_2);
		
		lblX = new JLabel("X");
		lblX.setForeground(Color.WHITE);
		lblX.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblX.setBounds(407, 515, 32, 21);
		contentPane.add(lblX);
		lblFrictionX = new JLabel("Force de friction (N) : ");
		lblFrictionX.setForeground(Color.WHITE);
		lblFrictionX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrictionX.setBounds(282, 540, 263, 14);
		contentPane.add(lblFrictionX);
		
		lblRappelX = new JLabel("Force de rappel (N) : ");
		lblRappelX.setForeground(Color.WHITE);
		lblRappelX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRappelX.setBounds(282, 565, 263, 20);
		contentPane.add(lblRappelX);
		
		lblNormaleX = new JLabel("Force normale (N) : 0");
		lblNormaleX.setForeground(Color.WHITE);
		lblNormaleX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNormaleX.setBounds(282, 596, 258, 23);
		contentPane.add(lblNormaleX);
		
		lblGravitanionnelleX = new JLabel("Force gravitionnelle (N) : 0");
		lblGravitanionnelleX.setForeground(Color.WHITE);
		lblGravitanionnelleX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGravitanionnelleX.setBounds(282, 633, 258, 22);
		contentPane.add(lblGravitanionnelleX);
		
		lblVitesseX = new JLabel("Vitesse (m/s) : ");
		lblVitesseX.setForeground(Color.WHITE);
		lblVitesseX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVitesseX.setBounds(282, 666, 258, 14);
		contentPane.add(lblVitesseX);
		
		lblAccelX = new JLabel("Acc\u00E9l\u00E9ration (m/s\u00B2 ) : ");
		lblAccelX.setForeground(Color.WHITE);
		lblAccelX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAccelX.setBounds(282, 696, 263, 14);
		contentPane.add(lblAccelX);
		
		lblPositionX = new JLabel("Position (m ) : ");
		lblPositionX.setForeground(Color.WHITE);
		lblPositionX.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPositionX.setBounds(282, 721, 263, 14);
		contentPane.add(lblPositionX);
		
		lblFrictionY = new JLabel("Force de friction (N) : 0");
		lblFrictionY.setForeground(Color.WHITE);
		lblFrictionY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrictionY.setBounds(571, 540, 321, 14);
		contentPane.add(lblFrictionY);
		
		lblRappelY = new JLabel("Force de rappel (N) : 0");
		lblRappelY.setForeground(Color.WHITE);
		lblRappelY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRappelY.setBounds(571, 565, 277, 20);
		contentPane.add(lblRappelY);
		
		lblNormaleY = new JLabel("Force normale (N) : " + (-(-9.8 * (double)spnMasse.getValue())));
		lblNormaleY.setForeground(Color.WHITE);
		lblNormaleY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNormaleY.setBounds(571, 596, 246, 23);
		contentPane.add(lblNormaleY);
		
		lblGravY = new JLabel("Force gravitionnelle (N) : " + (-9.8 * (double)spnMasse.getValue()));
		lblGravY.setForeground(Color.WHITE);
		lblGravY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGravY.setBounds(571, 633, 287, 22);
		contentPane.add(lblGravY);
		
		lblVitesseY = new JLabel("Vitesse (m/s) : 0");
		lblVitesseY.setForeground(Color.WHITE);
		lblVitesseY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVitesseY.setBounds(571, 666, 277, 14);
		contentPane.add(lblVitesseY);
		
		lblAccelY = new JLabel("Acc\u00E9l\u00E9ration (m/s\u00B2 ) : 0");
		lblAccelY.setForeground(Color.WHITE);
		lblAccelY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAccelY.setBounds(571, 696, 277, 14);
		contentPane.add(lblAccelY);
		
		lblPositionY = new JLabel("Position (m ) : 0");
		lblPositionY.setForeground(Color.WHITE);
		lblPositionY.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPositionY.setBounds(571, 721, 258, 14);
		contentPane.add(lblPositionY);
		
		lblY = new JLabel("Y");
		lblY.setForeground(Color.WHITE);
		lblY.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblY.setBounds(699, 512, 32, 23);
		contentPane.add(lblY);
		
		separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setForeground(Color.BLACK);
		separator_3.setBackground(Color.BLACK);
		separator_3.setBounds(549, 514, 10, 233);
		contentPane.add(separator_3);
		
		JLabel lblTemps = new JLabel("    Temps \u00E9coul\u00E9 (s): 0s");
		lblTemps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTemps.setForeground(Color.WHITE);
		lblTemps.setBorder(new LineBorder(Color.WHITE, 2));
		lblTemps.setBounds(632, 318, 241, 74);
		contentPane.add(lblTemps);
		
		//Daniel Chahfe
		animRessort.addRepaintListener(new RepaintListener(){
			@Override
			public void tempsListen(double temps) {
				lblTemps.setText("Temps \u00E9coul\u00E9 (s): " + String.format("%.2f", temps) + "s");
			}

			@Override
			public void vitesseListen(Vecteur vitesse) {
				lblVitesseX.setText("Vitesse (m/s) : " + String.format("%.3f", vitesse.getX()) + "m/s");
				lblVitesseY.setText("Vitesse (m/s) : " + String.format("%.2f", vitesse.getY()) + "m/s");
			}

			@Override
			public void positionListen(Vecteur position) {
				lblPositionX.setText("Position (m): " + String.format("%.2f", position.getX() - animRessort.getxInit()) + "m");
				lblPositionY.setText("Position (m): " + String.format("%.2f", position.getY())+ "m");
				
			}

			@Override
			public void accelListen(Vecteur accel) {
				lblAccelX.setText("Acc\u00E9l\u00E9ration (m/s\u00B2) : " + String.format("%.2f", accel.getX()) + "m/s\u00B2");
				lblAccelY.setText("Acc\u00E9l\u00E9ration (m/s\u00B2) : " + String.format("%.2f", accel.getY()) + "m/s\u00B2");
			}

			@Override
			public void frictionListen(Vecteur friction) {
				lblFrictionX.setText("Force de friction (N) : " + String.format("%.2f", friction.getX()));
				lblFrictionY.setText("Force de friction (N) : " + String.format("%.2f", friction.getY()));
			}

			@Override
			public void rappelListen(Vecteur rappel) {
				lblRappelX.setText("Force de rappel (N) : " + String.format("%.2f", rappel.getX()));
				lblRappelY.setText("Force de rappel (N) : " + String.format("%.2f", rappel.getY()));
			}
			
			@Override
			public void pauseAccListen(boolean pauseAcc) {
				btnPause.setEnabled(pauseAcc);
			}
			
		});
	}
	
	/**
	 * methode qui disable certains composants lorsque l<animation nest pas en cours
	 */
	//Daniel Chahfe
	public void animDisable() {
		sliderEtirement.setEnabled(false);
		spnMasse.setEnabled(false);
		spnTemps.setEnabled(false);
		spnRessort.setEnabled(false);
		rdbtnAlu_Alu.setEnabled(false);
		rdbtnBronze_Fer.setEnabled(false);
		rdbtnNickel_Acier.setEnabled(false);
		rdbtnAcier_Plomb.setEnabled(false);
	}
	
	/**
	 * methode qui enable certains composants lorsque l<animation nest pas en cours
	 */
	//Daniel Chahfe
	public void animEnable() {
		sliderEtirement.setEnabled(true);
		spnMasse.setEnabled(true);
		spnTemps.setEnabled(true);
		spnRessort.setEnabled(true);
		rdbtnAlu_Alu.setEnabled(true);
		rdbtnBronze_Fer.setEnabled(true);
		rdbtnNickel_Acier.setEnabled(true);
		rdbtnAcier_Plomb.setEnabled(true);
	}
}
