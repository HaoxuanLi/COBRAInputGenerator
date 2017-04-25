import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import jssc.SerialPortException;

public class ClosePort extends JFrame {

	private FlowLayout layout;
	private JButton closeport;
	private Font font;

	@SuppressWarnings("restriction")
	public ClosePort(jssc.SerialPort serialPort) {
		super("ClosePort");
		layout = new FlowLayout();
		setLayout(layout);
		font = new Font("Serif", Font.PLAIN, 40);

		closeport = new JButton("Close");
		closeport.setFont(font);
		closeport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (serialPort != null) {
					try {
						serialPort.closePort();
					} catch (SerialPortException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		add(closeport);

	}

}
