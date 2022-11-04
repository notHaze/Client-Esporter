package ihm;


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.BorderLayout;
import javax.swing.JLabel;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import ihm.component.boutonMenu;
import ihm.erreur.Error;
import types.EcurieInfo;
import types.JoueurInfo;
import types.exception.InvalidPermission;
import utilisateur.User;

public class MasterFrame {

	private JFrame frame;
	public static final Color COULEUR_MASTER = new Color(0,164,210);
	public static final Color COULEUR_MASTER_FOND = Color.DARK_GRAY;
	public static final Color COULEUR_TEXTE = Color.WHITE;
	private JPanel panelMenu;
	private ButtonGroup boutonGroupMenu;
	private static MasterFrame instance;
	private User user;
	private JLabel nomCompte;
	private JLabel logoCompte;
	private JPanel header;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MasterFrame window = getInstance();
					window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MasterFrame() {
		try {
			this.user = new User();
			
		} catch (UnknownHostException e) {
			error(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialize();
	}
	
	public static synchronized MasterFrame getInstance() {
		if (instance==null)
			instance = new MasterFrame();
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setMinimumSize(new Dimension(1300, 500));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		header = new JPanel();
		header.setBackground(COULEUR_MASTER_FOND);
		frame.getContentPane().add(header, BorderLayout.NORTH);
		header.setLayout(new BorderLayout(0, 0));
		
		

		JPanel connexion = new JPanel();
		connexion.setPreferredSize(new Dimension(250,50));
		connexion.setBackground(COULEUR_MASTER);
		header.add(connexion, BorderLayout.EAST);
		
		nomCompte = new JLabel("compte");
		nomCompte.setHorizontalAlignment(SwingConstants.CENTER);
		nomCompte.setForeground(COULEUR_TEXTE);
		connexion.add(nomCompte);
		
		logoCompte = new JLabel("logo");
		logoCompte.setHorizontalAlignment(SwingConstants.CENTER);
		logoCompte.setForeground(COULEUR_TEXTE);
		connexion.add(logoCompte);
		
		JPanel panel = new JPanel();
		panel.setBackground(COULEUR_MASTER_FOND);
		header.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEsporter = new JPanel();
		panelEsporter.setBackground(COULEUR_MASTER_FOND);
		panel.add(panelEsporter, BorderLayout.WEST);
		panelEsporter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		
		JLabel logo = new JLabel("logo");
		logo.setForeground(COULEUR_TEXTE);
		panelEsporter.add(logo);
		
		JLabel nomComp = new JLabel("Esporter");
		nomComp.setForeground(COULEUR_TEXTE);
		panelEsporter.add(nomComp);
		
		panelMenu = new JPanel();

		panelMenu.setBackground(COULEUR_MASTER_FOND);
		FlowLayout flowLayout_1 = (FlowLayout) panelMenu.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		panel.add(panelMenu, BorderLayout.CENTER);
		
		JPanel panelDummyRight = new JPanel();
		panelDummyRight.setPreferredSize(new Dimension(35, 10));
		panelDummyRight.setBackground(COULEUR_MASTER_FOND);
		panel.add(panelDummyRight, BorderLayout.EAST);
		
		JPanel panelDummyTop = new JPanel();
		panelDummyTop.setPreferredSize(new Dimension(10, 35));
		panelDummyTop.setBackground(COULEUR_MASTER_FOND);
		panel.add(panelDummyTop, BorderLayout.NORTH);
		
		
		boutonGroupMenu = new ButtonGroup();
		TypeMenu m = TypeMenu.Visiteurs;
		boutonMenu[] menu = m.getMenu();
		for (int i=0; i<menu.length;i++) {
			panelMenu.add(menu[i]);
			boutonGroupMenu.add(menu[i]);
		}
		
		JPanel footer = new JPanel();
		frame.getContentPane().add(footer, BorderLayout.SOUTH);
		
		setCompte();
		
		connexion.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				user.login("test", "mdpTest");
			}
		});
	}
	
	
	
	public void setMenu(TypeMenu m) {
		panelMenu.removeAll();
		boutonGroupMenu = new ButtonGroup();
		boutonMenu[] menu = m.getMenu();
		for (int i=0; i<menu.length;i++) {
			panelMenu.add(menu[i]);
			boutonGroupMenu.add(menu[i]);
		}
		setCompte();
		frame.getContentPane().repaint();
	}
	
	public void setCompte() {
		switch(user.getPermission()) {
		case ARBITRE:
			nomCompte.setText("Arbitre");
			break;
		case ECURIE:
			EcurieInfo e = (EcurieInfo)user.getInfo();
			nomCompte.setText(e.getNom());
			//logoCompte.setIcon(e.getLogo());
			break;
		case JOUEUR:
			JoueurInfo j = (JoueurInfo)user.getInfo();
			nomCompte.setText(j.getNom());
			//logoCompte.setIcon(j.getPhoto());
			break;
		case ORGANISATEUR:
			nomCompte.setText("Esporter");
			break;
		case VISITEUR:
			nomCompte.setText("Visiteur");
			break;
		default:
			break;
		
		}
	}
	
	public void setPanel(JPanel p) {
		BorderLayout layout = (BorderLayout)frame.getContentPane().getLayout();
		if (layout.getLayoutComponent(BorderLayout.CENTER)!=null)
			frame.getContentPane().remove(layout.getLayoutComponent(BorderLayout.CENTER));
		frame.getContentPane().add(p, BorderLayout.CENTER);
		frame.revalidate();
		frame.getContentPane().repaint();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public User getUser() {
		return user;
	}
	
	public Point getCentre() {
		return new Point(frame.getLocation().x+(frame.getWidth()/2), frame.getLocation().y+(frame.getHeight()/2));
	}
	
	public void error(Exception e) {
		Error dialog = new Error(e);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}


}
