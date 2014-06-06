import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class SorterFrame extends JFrame {

	private JPanel contentPane;
	private PixelSorter sorter;
	private final int borderWidth = 5;
	
	String imgName = "me.jpg";
	private JRadioButton rdbtnBlackMode;
	private JRadioButton rdbtnBrightnessMode;
	private JRadioButton rdbtnWhiteMode;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnOriginal;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SorterFrame frame = new SorterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SorterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 750);
		contentPane = new JPanel();
		//setBounds(100, 100, width, height);
		
		contentPane.setBorder(new EmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 50, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		sorter = new PixelSorter();
		sorter.loadImage(imgName);
		GridBagConstraints gbc_sorter = new GridBagConstraints();
		gbc_sorter.gridwidth = 4;
		gbc_sorter.insets = new Insets(0, 0, 5, 0);
		gbc_sorter.fill = GridBagConstraints.BOTH;
		gbc_sorter.gridx = 0;
		gbc_sorter.gridy = 0;
		contentPane.add(sorter, gbc_sorter);
		
		rdbtnWhiteMode = new JRadioButton("White Mode");
		rdbtnWhiteMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(false);
				sorter.setMode(PixelSorter.WHITE);
			}
		});
		
		rdbtnBrightnessMode = new JRadioButton("Brightness Mode");
		rdbtnBrightnessMode.setSelected(true);
		rdbtnBrightnessMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(true);
				sorter.setMode(PixelSorter.BRIGHT);
			}
		});
		
		rdbtnBlackMode = new JRadioButton("Black Mode");
		rdbtnBlackMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(false);
				sorter.setMode(PixelSorter.BLACK);
			}
		});
		
		rdbtnOriginal = new JRadioButton("Original");
		rdbtnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slider.setEnabled(false);
				sorter.setMode(PixelSorter.ORIGINAL);
			}
		});
		buttonGroup.add(rdbtnOriginal);
		GridBagConstraints gbc_rdbtnOriginal = new GridBagConstraints();
		gbc_rdbtnOriginal.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnOriginal.gridx = 0;
		gbc_rdbtnOriginal.gridy = 1;
		contentPane.add(rdbtnOriginal, gbc_rdbtnOriginal);
		buttonGroup.add(rdbtnBlackMode);
		GridBagConstraints gbc_rdbtnBlackMode = new GridBagConstraints();
		gbc_rdbtnBlackMode.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBlackMode.gridx = 1;
		gbc_rdbtnBlackMode.gridy = 1;
		contentPane.add(rdbtnBlackMode, gbc_rdbtnBlackMode);
		buttonGroup.add(rdbtnBrightnessMode);
		GridBagConstraints gbc_rdbtnBrightnessMode = new GridBagConstraints();
		gbc_rdbtnBrightnessMode.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnBrightnessMode.gridx = 2;
		gbc_rdbtnBrightnessMode.gridy = 1;
		contentPane.add(rdbtnBrightnessMode, gbc_rdbtnBrightnessMode);
		buttonGroup.add(rdbtnWhiteMode);
		GridBagConstraints gbc_rdbtnWhiteMode = new GridBagConstraints();
		gbc_rdbtnWhiteMode.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnWhiteMode.gridx = 3;
		gbc_rdbtnWhiteMode.gridy = 1;
		contentPane.add(rdbtnWhiteMode, gbc_rdbtnWhiteMode);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				JSlider slider = (JSlider)arg0.getSource();
				
				if(!slider.getValueIsAdjusting())
					sorter.setBrightness(slider.getValue());
				
			}
		});
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(50);
		slider.setMaximum(255);
		slider.setValue(0);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridwidth = 4;
		gbc_slider.insets = new Insets(0, 0, 0, 5);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 2;
		contentPane.add(slider, gbc_slider);
		
	}

}
