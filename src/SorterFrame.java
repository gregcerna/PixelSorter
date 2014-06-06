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


public class SorterFrame extends JFrame {

	private JPanel contentPane;
	private PixelSorter sorter;
	private final int borderWidth = 5;
	
	String imgName = "cat.jpg";

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
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		//setBounds(100, 100, width, height);
		
		contentPane.setBorder(new EmptyBorder(borderWidth, borderWidth, borderWidth, borderWidth));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		sorter = new PixelSorter();
		sorter.loadImage(imgName);
		GridBagConstraints gbc_sorter = new GridBagConstraints();
		gbc_sorter.insets = new Insets(0, 0, 5, 0);
		gbc_sorter.fill = GridBagConstraints.BOTH;
		gbc_sorter.gridx = 0;
		gbc_sorter.gridy = 0;
		contentPane.add(sorter, gbc_sorter);
		
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sorter.draw();
			}
		});
		GridBagConstraints gbc_btnSort = new GridBagConstraints();
		gbc_btnSort.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSort.gridx = 0;
		gbc_btnSort.gridy = 1;
		contentPane.add(btnSort, gbc_btnSort);
		
	}

}
