import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.Vector;
import java.lang.ProcessBuilder;
import java.io.*;

public class WCETSettings_GUI extends JFrame {
	private FlowLayout layout;
	private Font font;
	private JButton browse;
	private JButton deploy;
	private JButton erase;
	private JComboBox toollist;
	private JComboBox interfacelist;
	private JComboBox devicelist;
	private JLabel toollabel;
	private JLabel interfacelabel;
	private JLabel devicelabel;
	private static String[] toolname = { "AVRDRAGON", "AVR-ICE", "AVRMK-II" };
	private static String[] interfacename = { "PID", "JTAG" };
	private static String[] devicename = { "AT90CAN32", "AT90CAN64", "AT90CAN128" };
	private JTextField filetextfield;
	private JFileChooser filechooser;
	private ProcessBuilder makeprocessbuilder;
	private Process makeprocess;
	private ProcessBuilder programprocessbuilder;
	private Process programprocess;

	private String toolstring;
	private String interfacestring;
	private String devicestring;
	private String deployfilestring;
	private String path;

	public WCETSettings_GUI() throws IOException, InterruptedException {
		super("Cobra-WCET");
		layout = new FlowLayout();
		setLayout(layout);
		font = new Font("Serif", Font.PLAIN, 40);

		filechooser = new JFileChooser();
		setFileChooserFont(filechooser.getComponents());

		filechooser.setCurrentDirectory(new java.io.File("."));
		filechooser.setDialogTitle("Select Directiory");
		filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		browse = new JButton("Browse");
		browse.setFont(font);
		browse.addActionListener(new ActionListener() {
			private int returnvalue;

			public void actionPerformed(ActionEvent event) {
				returnvalue = filechooser.showOpenDialog(null);

				if (returnvalue == JFileChooser.APPROVE_OPTION) {
					path = (filechooser.getSelectedFile().getAbsolutePath());
					makeprocessbuilder = new ProcessBuilder("make");
					makeprocessbuilder.directory(new File(path));
				}
			}
		});

		toolstring = toolname[0];
		interfacestring = interfacename[0];
		devicestring = devicename[0];

		toollabel = new JLabel("tool");
		toollabel.setFont(font);

		toollist = new JComboBox(toolname);
		toollist.setFont(font);
		toollist.setBackground(Color.WHITE);
		toollist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					toolstring = toolname[toollist.getSelectedIndex()];
				}
			}
		});

		interfacelabel = new JLabel("interface");
		interfacelabel.setFont(font);

		interfacelist = new JComboBox(interfacename);
		interfacelist.setFont(font);
		interfacelist.setBackground(Color.WHITE);
		interfacelist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					interfacestring = interfacename[interfacelist.getSelectedIndex()];
				}
			}
		});

		devicelabel = new JLabel("device");
		devicelabel.setFont(font);

		devicelist = new JComboBox(devicename);
		devicelist.setFont(font);
		devicelist.setBackground(Color.WHITE);
		devicelist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					devicestring = devicename[devicelist.getSelectedIndex()];
				}
			}
		});

		deploy = new JButton("Deploy");
		deploy.setFont(new Font("Serif", Font.PLAIN, 40));
		deploy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				for (File file : new File(path).listFiles()) {
					if (file.getName().endsWith(".elf")) {
						deployfilestring = file.getName();
						System.out.println(deployfilestring);
					}

				}

				String[] deploycommand = { "C:\\Program Files (x86)\\Atmel\\Studio\\7.0\\atbackend\\atprogram", "-t",
						toolstring, "-i", interfacestring, "-d", devicestring, "program", "-f", deployfilestring };
				programprocessbuilder = new ProcessBuilder(deploycommand);
				programprocessbuilder.directory(new File(path));

				try {
					makeprocess = makeprocessbuilder.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					programprocess = programprocessbuilder.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Scanner scan = new Scanner(makeprocess.getInputStream());
				while (scan.hasNextLine()) {
					System.out.println(scan.nextLine());
				}

				Scanner scan2 = new Scanner(programprocess.getInputStream());
				while (scan2.hasNext()) {
					System.out.println(scan2.nextLine());
				}

			}

		});

		add(browse, BorderLayout.CENTER);
		add(toollabel);
		add(toollist);
		add(interfacelabel);
		add(interfacelist);
		add(devicelabel);
		add(devicelist);
		add(deploy, BorderLayout.EAST);

	}

	public void setFileChooserFont(Component[] comp) {
		for (Component x : comp) {
			if (x instanceof Container)
				setFileChooserFont(((Container) x).getComponents());
			try {
				x.setFont(new Font("Serif", Font.PLAIN, 30));
			} catch (Exception e) {
			} // do nothing
		}
	}

}
