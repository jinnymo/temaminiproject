package market.panel;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import market.MainFrame;
import market.Resource;
import market.DTO.ChatListDTO;
import market.DTO.ItemListDTO;
import market.DTO.UserDTO;
import market.repoIm.ChatRepoIm;

public class ChatListPanel extends JPanel implements ListSelectionListener,Runnable {
	private JList<ChatListDTO> jList;
	private JScrollPane scrolled;
	private DefaultListModel model;
	
	private MainFrame mContext;
	private UserDTO myUserDTO;
	
	private ChatRepoIm chatRepoIm;
	private List<ChatListDTO> chatRoomArrList = new ArrayList<>();
	private PanelAdapter panelAdapter;

	public ChatListPanel(MainFrame mContex, PanelAdapter panelAdapter) {
		this.mContext = mContex;
		this.myUserDTO = mContex.getMyUserDTO();
		this.panelAdapter = panelAdapter;
		chatRepoIm = new ChatRepoIm();
		initData();
		setInitLayout();

	}

	private void initData() {
		
		
		model = new DefaultListModel();
		jList = new JList<ChatListDTO>(model);
		scrolled = new JScrollPane(jList);
		
		setLayout(null);
		setSize(Resource.ADP_PANEL_X, Resource.ADP_PANEL_Y);
		setLocation(0,50);
		
	}

	private void setInitLayout() {
		setLayout(null);
		setSize(400, 500);
		setLocation(0, 50);
		setBackground(Color.red);
		
		scrolled.setSize(400, 450);
		scrolled.setLocation(0, 0);
		
		add(scrolled);
		
		jList.addListSelectionListener(this);
	}

	@Override
	public void run() {
		while(true) {
			model.clear();
			try {
				chatRoomArrList = chatRepoIm.ChatingList(myUserDTO.getUser_num());
				for (ChatListDTO chatListDTO : chatRoomArrList) {
					model.addElement(chatListDTO);
				}
				Thread.sleep(1000);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			model.clear();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if(!e.getValueIsAdjusting()) {	//이거 없으면 mouse 눌릴때, 뗄때 각각 한번씩 호출되서 총 두번 호출
			
			ChatListDTO selectedItem = jList.getSelectedValue();
			if (selectedItem != null) {
				//System.out.println(selectedItem.getRoomId());
				panelAdapter.startChat(selectedItem.getRoomId());
				
			}


		}		
	}



}
