package ItemManager;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JFileChooserLayout extends JPanel {

	private JFileChooser chooser = new JFileChooser();
	// 확장자 필터
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png");
	private String imagePath = null;
	private File fileUrl = null;
	//private ItemBufferedImage image;
	//private ItemDAO dao;

	public Component printFilePath() {

		JButton button = new JButton("파일 열기");

		setVisible(true); // 보이게 할지 여부
		setLayout(new FlowLayout()); // 레이아웃 변경
		add(button); // 컨테이너에 버튼 추가

		button.addActionListener(new ActionListener() { // 익명 객체
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.setFileFilter(filter); // 파일 필터 추가
				int returnVal = chooser.showOpenDialog(getParent());
				// 창 열기 정상 수행시 0 반환, 취소시 1 반환

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileUrl = chooser.getSelectedFile();
					imagePath = fileUrl.toString();

				}
			}
		});
		return button;

	}
}
