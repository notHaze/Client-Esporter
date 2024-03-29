package com.esporter.client.vue.error;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.esporter.both.types.exception.ExceptionLogin;
import com.esporter.client.controleur.ControlerError;
import com.esporter.client.controleur.ControlerKeyStroke;
import com.esporter.client.vue.MasterFrame;

public class ErrorPanel extends JPanel {

	private static final long serialVersionUID = 972390881488216598L;
	private Exception e;
	private JLabel texte;
	private JCircleProgressBar progressBar;
	private Thread t;
	private boolean persistent;
	private ErrorPanel instance;
	private JPanel panel_1;
	private JButton btnRetry;
	private boolean critical;
	private JLabel Titre;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private boolean running = true;
	
	
	public void setRunning(boolean running) {
		synchronized (instance) {
			this.running = running;
			} 
		}
	  
	public ErrorPanel() {
		setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		this.setVisible(false);
		initalize();
		
		
		this.instance = this;
		
		synchronized (instance) {
			e = null;
		}

		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(200, 200));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);

		panel_3 = new JPanel();
		add(panel_3, BorderLayout.SOUTH);
		panel_3.setPreferredSize(new Dimension(200, 200));
		panel_3.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);

		panel_4 = new JPanel();
		add(panel_4, BorderLayout.WEST);
		panel_4.setPreferredSize(new Dimension(200, 200));
		panel_4.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);

		panel_5 = new JPanel();
		add(panel_5, BorderLayout.EAST);
		panel_5.setPreferredSize(new Dimension(200, 200));
		panel_5.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		t = new Thread(new Runnable() {
			
		
			@Override
			public void run() {
				boolean run;
				synchronized (instance) {
					run = running;
				}
				
				while (run) {
					synchronized (instance) {
						if (e == null) {
							try {
								System.out.println("Thread error sleep");
								instance.wait();
								System.out.println("Thread error Wake up");
							} catch (InterruptedException e1) {
								Thread.currentThread().interrupt();
							}
						} else {
							try {
								instance.wait();
							} catch (InterruptedException e1) {
								Thread.currentThread().interrupt();
							}

						}
					}
					run = running;
				}
				
			}
		});
		t.start();
	}

	public void setException(Exception e) {
		synchronized (instance) {
			this.e = e;
			if (e != null) {
				System.out.println("Thread error notify");
				instance.notify();
			}
		}

	}

	public void setTexte(String s) {
		texte.setText(s);
	}

	public void setState(Exception e, Boolean persistent, boolean critical) {
		this.persistent = persistent;
		this.critical = critical;
		if (persistent) {
			progressBar.setVisible(true);
			btnRetry.setVisible(false);
		} else {
			if (e instanceof ExceptionLogin) {
				btnRetry.setText("Réessayer");		
			}else {
				btnRetry.setText("Continuer");	
			}
			progressBar.setVisible(false);
			btnRetry.setVisible(true);
		}
		synchronized (instance) {
			setException(e);
			instance.notify();
		}
		setTexte(e.getMessage());
		
		this.revalidate();
		this.repaint();
	}

	private void initalize() {
		// setSize(frame.getPreferredSize());
		setPreferredSize(new Dimension(1920, 1080));
		setLayout(new BorderLayout(0, 0));
		// setBounds(0, 0, frame.getWidth(), frame.getHeight());
		Color dark = MasterFrame.COLOR_MASTER_BACKGROUND.darker();
		Color c = new Color(dark.getRed(), dark.getGreen(), dark.getBlue(), 150);
		setOpaque(true);
		JPanel panel = new JPanel();

		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		panel.setForeground(MasterFrame.COLOR_TEXT);
		panel.setOpaque(false);

		setBackground(c);

		Titre = new JLabel("Erreur");

		Titre.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		Titre.setForeground(MasterFrame.COLOR_TEXT);
		Titre.setFont(new Font("Tahoma", Font.PLAIN, 19));
		Titre.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(Titre, BorderLayout.NORTH);

		JPanel panelTexte = new JPanel();
		panel.add(panelTexte);

		progressBar = new JCircleProgressBar();
		texte = new JLabel();
		panelTexte.add(texte);
		panelTexte.add(progressBar);
		texte.setForeground(MasterFrame.COLOR_TEXT);
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);

		btnRetry = new JButton();
		ControlerError controler = new ControlerError();
		ControlerKeyStroke controlerKey = new ControlerKeyStroke();
		btnRetry.addActionListener(controler);
		//btnContinuer.addKeyListener(controler);
		
		
		btnRetry.setActionCommand("ERROR_CONTINUE");
		
		btnRetry.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed ENTER"), "pressedEnter");
		btnRetry.getActionMap().put("pressedEnter", controlerKey);
		btnRetry.setText("Continuer");
		panel_1.add(btnRetry);

		panelTexte.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		panelTexte.setForeground(MasterFrame.COLOR_TEXT);

		panel_1.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		panel_1.setForeground(MasterFrame.COLOR_TEXT);

	}

	public boolean isCritical() {
		return critical;
	}

	public void resize(int width, int height) {
		/*
		 * panel_2.setPreferredSize(new Dimension(width/4,height));
		 * panel_3.setPreferredSize(new Dimension(width/4,height));
		 * panel_4.setPreferredSize(new Dimension(width,height/5));
		 * panel_5.setPreferredSize(new Dimension(width,height/5)); this.revalidate();
		 * this.repaint();
		 */
	}

}
