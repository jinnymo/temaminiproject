package market;

import javax.swing.JOptionPane;

import lombok.Getter;

public class Resource {

	public final static int FRAMESIZE_X = 416;
	public final static int FRAMESIZE_Y = 689;
	public final static int PANEL_FULL_SIZE_X = 400;
	public final static int PANEL_FULL_SIZE_Y = 650;

	public final static int LOGIN_COMP_X = 150;
	public final static int LOGIN_COMP_Y = 30;

	public final static int ADP_BOTTOM_BTN_X = 100;
	public final static int ADP_BOTTOM_BTN_Y = 100;

	public final static int ADP_PANEL_X = 400;
	public final static int ADP_PANEL_Y = 500;

	// 경고 + "msg" 팝업창
	public static void WarnMsgDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", JOptionPane.WARNING_MESSAGE);
	};

	// "" + "msg" 팝업창
	public static void MsgDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg, "", JOptionPane.WARNING_MESSAGE);
	};

}
