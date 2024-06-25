package market.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import market.MainFrame;
import market.Resource;

public class PanelAdapter extends JPanel implements ActionListener {

	private JPanel topPanel;
	private JTextField searchTF;
	private JButton searchBtn;
	private JButton itemListBtn;
	private JButton chatListBtn;
	private JButton addItemBtn;
	private JButton myInfoBtn;
	private JButton updateBtn;
	private ItemListPanel itemListPanel;
	private ChatListPanel chatListPanel;
	private addItemPanel addItemPanel;
	private myInfoPanel myInfoPanel;
	private MainFrame mContext;
	private ChatPanel cPanel;

	public PanelAdapter(MainFrame mContext) {
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();

	}

	private void initData() {

		setSize(Resource.PANEL_FULL_SIZE_X, Resource.PANEL_FULL_SIZE_Y);
		setLocation(0, 0);
		setLayout(null);
		setBackground(Color.blue);
		setVisible(true);

		itemListPanel = new ItemListPanel(mContext, this);
		chatListPanel = new ChatListPanel();
		addItemPanel = new addItemPanel(mContext);
		myInfoPanel = new myInfoPanel(mContext, this);

		topPanel = new JPanel();
		searchTF = new JTextField("검색");
		// 나중에 JButton 사용하지 않고 이미지 아이콘으로 변경
		searchBtn = new JButton("검색버튼");
		itemListBtn = new JButton("상품버튼");
		chatListBtn = new JButton("채팅버튼");
		addItemBtn = new JButton("상품등록버튼");
		myInfoBtn = new JButton("내정보버튼");
		updateBtn = new JButton("새로고침버튼");

		topPanel.setSize(Resource.PANEL_FULL_SIZE_X, 50);
		searchTF.setSize(200, 30);
		searchBtn.setSize(30, 30);
		updateBtn.setSize(30, 30);
		itemListBtn.setSize(Resource.ADP_BOTTOM_BTN_X, Resource.ADP_BOTTOM_BTN_Y);
		chatListBtn.setSize(Resource.ADP_BOTTOM_BTN_X, Resource.ADP_BOTTOM_BTN_Y);
		addItemBtn.setSize(Resource.ADP_BOTTOM_BTN_X, Resource.ADP_BOTTOM_BTN_Y);
		myInfoBtn.setSize(Resource.ADP_BOTTOM_BTN_X, Resource.ADP_BOTTOM_BTN_Y);

		topPanel.setLocation(0, 0);
		itemListBtn.setLocation(0, 550);
		chatListBtn.setLocation(100, 550);
		addItemBtn.setLocation(200, 550);
		myInfoBtn.setLocation(300, 550);

		searchTF.setLocation(20, 10);
		searchBtn.setLocation(300, 10);
		updateBtn.setLocation(450, 10);

		topPanel.setBackground(Color.orange);

	}

	private void setInitLayout() {

		add(topPanel);
		topPanel.add(searchTF);
		topPanel.add(searchBtn);
		topPanel.add(updateBtn);
		add(itemListBtn);
		add(chatListBtn);
		add(addItemBtn);
		add(myInfoBtn);

		add(itemListPanel);
		add(chatListPanel);
		add(addItemPanel);
		add(myInfoPanel);

		itemListPanel.setVisible(true);
		chatListPanel.setVisible(false);
		addItemPanel.setVisible(false);
		myInfoPanel.setVisible(false);

	}

	public void addItemDetailPanel(ItemDetilPanel itemDetilPanel) {
		itemListPanel.setVisible(false);
		chatListPanel.setVisible(false);
		addItemPanel.setVisible(false);
		myInfoPanel.setVisible(false);
		add(itemDetilPanel);

		searchBtn.setEnabled(false);
		itemListBtn.setEnabled(false);
		chatListBtn.setEnabled(false);
		addItemBtn.setEnabled(false);
		myInfoBtn.setEnabled(false);
		updateBtn.setEnabled(false);
	}

	public void startChat(int roomId) {
		startChatControlPanel();
		cPanel = new ChatPanel(roomId, this);
		add(cPanel);
		Thread temp = new Thread(cPanel);
		temp.start();

	}

	public void endChat() {
		endItemDetailPanel();
		cPanel = null;
	}

	public void startChatControlPanel() {
		itemListPanel.setVisible(false);
		chatListPanel.setVisible(false);
		addItemPanel.setVisible(false);
		myInfoPanel.setVisible(false);

		searchBtn.setEnabled(false);
		itemListBtn.setEnabled(false);
		chatListBtn.setEnabled(false);
		addItemBtn.setEnabled(false);
		myInfoBtn.setEnabled(false);
		updateBtn.setEnabled(false);
	}

	public void endItemDetailPanel() {
		itemListPanel.setVisible(true);
		chatListPanel.setVisible(false);
		addItemPanel.setVisible(false);
		myInfoPanel.setVisible(false);

		searchBtn.setEnabled(true);
		itemListBtn.setEnabled(true);
		chatListBtn.setEnabled(true);
		addItemBtn.setEnabled(true);
		myInfoBtn.setEnabled(true);
		updateBtn.setEnabled(true);
	}

	private void addEventListener() {
		itemListBtn.addActionListener(this);
		chatListBtn.addActionListener(this);
		addItemBtn.addActionListener(this);
		myInfoBtn.addActionListener(this);
		updateBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemListBtn) {
			itemListPanel.setVisible(true);
			chatListPanel.setVisible(false);
			addItemPanel.setVisible(false);
			myInfoPanel.setVisible(false);

		} else if (e.getSource() == chatListBtn) {
			itemListPanel.setVisible(false);
			chatListPanel.setVisible(true);
			addItemPanel.setVisible(false);
			myInfoPanel.setVisible(false);
		} else if (e.getSource() == addItemBtn) {
			itemListPanel.setVisible(false);
			chatListPanel.setVisible(false);
			addItemPanel.setVisible(true);
			myInfoPanel.setVisible(false);
		} else if (e.getSource() == myInfoBtn) {
			itemListPanel.setVisible(false);
			chatListPanel.setVisible(false);
			addItemPanel.setVisible(false);
			myInfoPanel.setVisible(true);
		} else if (e.getSource() == updateBtn) {
			itemListPanel.upDateList();
		}
		revalidate();
		repaint();

	}

}
