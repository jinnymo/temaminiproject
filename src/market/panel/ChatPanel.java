package market.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import market.DTO.ChatDTO;
import market.repoIm.ChatRepoIm;

public class ChatPanel extends JPanel implements ActionListener, Runnable {

	int roomId;
	public boolean status;
	private ChatRepoIm chatRepoIm;

	private JList<String> jList;
	private JScrollPane scrolled;
	private DefaultListModel model;
	private JPanel bottomPanel;
	private JTextField text;
	private JButton sendBtn;
	private JButton endBtn;
	private PanelAdapter panelAdapter;
	private List<ChatDTO> chatArrList = new ArrayList<>();

	public ChatPanel(int roomId, PanelAdapter panelAdapter) {
		this.panelAdapter=panelAdapter;
		status = true;
		this.roomId = roomId;
		chatRepoIm = new ChatRepoIm();
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		// TODO Auto-generated method stub
		model = new DefaultListModel();
		jList = new JList<String>(model);
		scrolled = new JScrollPane(jList);
		text = new JTextField("", 13);
		sendBtn = new JButton("전송");
		endBtn = new JButton("나가기");
		bottomPanel = new JPanel();

	}

	private void setInitLayout() {
		setLayout(null);
		setSize(400, 500);
		setLocation(0, 50);
		setBackground(Color.green);

		scrolled.setSize(400, 450);
		scrolled.setLocation(0, 0);

		bottomPanel.setSize(400, 50);
		bottomPanel.setLocation(0, 450);

		sendBtn.setSize(70, 50);
		sendBtn.setLocation(0, 0);
		
		text.setSize(130, 50);
		text.setLocation(0, 0);

		sendBtn.setSize(70, 50);
		sendBtn.setLocation(330, 0);

		add(scrolled);
		add(bottomPanel);
		bottomPanel.add(endBtn);
		bottomPanel.add(text);
		bottomPanel.setVisible(true);
		bottomPanel.add(sendBtn);

	}

	private void addEventListener() {
		// TODO Auto-generated method stub
		sendBtn.addActionListener(this);
		endBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendBtn) {
			try {
				chatRepoIm.addChat(roomId, text.getText());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (e.getSource() == endBtn) {
			status = false;
			setVisible(false);
			panelAdapter.endChat();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (status) {
			try {
				model.clear();
				chatArrList = chatRepoIm.getChat(roomId);
				for (ChatDTO chatDTO : chatArrList) {
					model.addElement(chatDTO.getContent());
				}
				Thread.sleep(300);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.clear();

	}

}
