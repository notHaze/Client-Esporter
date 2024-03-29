package com.esporter.client.vue.organizer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.esporter.both.types.TypesTournament;
import com.esporter.client.controleur.ControlerCalendar;
import com.esporter.client.vue.MasterFrame;
import com.esporter.client.vue.component.RendererCalendar;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class RendererOrganizerCalendar extends JPanel implements RendererCalendar{
	
	private JLabel lblTournamentName = new JLabel();
	private JButton lblArrowIcon = new JButton();
	private final JPanel panelBtnAction = new JPanel();
	private TypesTournament tournoi;
	private final Component verticalGlueTop = Box.createVerticalGlue();
	private final JPanel panelBtn = 	new JPanel();
	private final JButton btnModify = new JButton();
	private final Component verticalGlueBot = Box.createVerticalGlue();
	private final JLabel lblSep = new JLabel(" | ");
	private final JButton btnRemove = new JButton("Supprimer");
	
	
	public RendererOrganizerCalendar (TypesTournament tournoi, int id) {
		ControlerCalendar controler = new ControlerCalendar();
		if (tournoi.isFull()) {
			lblSep.setVisible(false);
		}
		this.tournoi = tournoi;
		setBorder(new MatteBorder(0, 0, 1, 0, MasterFrame.COLOR_TEXT));
		setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		setLayout(new BorderLayout(5,5));
		JPanel panelText = new JPanel(new GridLayout(1,0));
		panelText.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		panelText.setPreferredSize(new Dimension(500,100));
		lblTournamentName.setForeground(MasterFrame.COLOR_TEXT);
		panelText.add(lblTournamentName);
		add(panelText, BorderLayout.WEST);
		panelBtnAction.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		panelText.add(panelBtnAction);
		panelBtnAction.setLayout(new BoxLayout(panelBtnAction, BoxLayout.Y_AXIS));
		
		panelBtnAction.add(verticalGlueTop);
		
		panelBtn.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		
		panelBtnAction.add(panelBtn);
		panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.X_AXIS));
		btnModify.addActionListener(controler);
		btnModify.setActionCommand("CALENDAR RENDERER MODIFY "+id);
		
		if (tournoi.isFull()) {
			btnModify.setText("Le tournoi a commencé");
			btnModify.setEnabled(false);
		}else {
			btnModify.setText("Modifier");
		}
		btnModify.setHorizontalAlignment(SwingConstants.CENTER);
		btnModify.setForeground(MasterFrame.COLOR_TEXT);
		btnModify.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, MasterFrame.COLOR_TEXT), new EmptyBorder(3, 11, 3, 10)));
		btnModify.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		btnModify.setAlignmentX(0.5f);
		
		panelBtn.add(btnModify);
		
		lblSep.setForeground(MasterFrame.COLOR_TEXT);
		panelBtn.add(lblSep);
		btnRemove.addActionListener(controler);
		btnRemove.setActionCommand("CALENDAR RENDERER REMOVE "+id);
	
		if (tournoi.isFull()) {
			btnRemove.setVisible(false);
		}
		btnRemove.setHorizontalAlignment(SwingConstants.CENTER);
		btnRemove.setForeground(MasterFrame.COLOR_TEXT);
		btnRemove.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, MasterFrame.COLOR_TEXT), new EmptyBorder(3, 11, 3, 10)));
		btnRemove.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		btnRemove.setAlignmentX(0.5f);
		panelBtn.add(btnRemove);
		
		panelBtnAction.add(verticalGlueBot);

		lblArrowIcon.addActionListener(controler);
		lblArrowIcon.setActionCommand("CALENDAR RENDERER POOL "+id);
		add(lblArrowIcon, BorderLayout.EAST);
		
		lblTournamentName.setText(this.tournoi.getName()+ " - " +new SimpleDateFormat("MM/dd/yyyy").format(this.tournoi.getRegisterDate()));
		
		lblArrowIcon.setBorder(null);
		lblArrowIcon.setBackground(MasterFrame.COLOR_MASTER_BACKGROUND);
		try {
			lblArrowIcon.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("../images/Chevron.png"))));
		} catch (IOException e) {

		}
		
		
		lblArrowIcon.setVisible(false);
		if (this.tournoi.isFull()) {
			lblArrowIcon.setVisible(true);
		}
		
	}


	@Override
	public TypesTournament getTournament() {
		return tournoi;
	}
		
}
	
