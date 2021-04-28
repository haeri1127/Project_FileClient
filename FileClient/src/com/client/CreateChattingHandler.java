package com.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import com.common.Protocol;

public class CreateChattingHandler implements ActionListener,ItemListener{
	private CreateChattingView ccView = null;
	private ClientSocket client = null;
	
	CreateChattingHandler(){
		
	}
	public void setInstance(CreateChattingView ccView) {
		this.ccView = ccView;
	}
	
	@Override
	
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		try {
			if (obj.equals(ccView.jbtn_create)) {
				System.out.println("1");
				if (ccView.selected_ID.size() == 0) {
					JOptionPane.showMessageDialog(null, "선택된 유저가 없습니다.", "메시지", JOptionPane.WARNING_MESSAGE);
				} else {
					String roomName = JOptionPane.showInputDialog("방 이름을 설정해주세요.");
					// 채팅방이름 중복생성 check부분.
					boolean success = true;
					for (String room : client.thread.chatRoomList.keySet()) {
						if (roomName.equals(room)) {
							JOptionPane.showInputDialog("이미 존재하는 방이름 입니다. \n 다시 작성해주세요.");
							success = false;
							break;
						}
					}
					if (success) {// 중복된 방이름 없을때.
						try {
							client.send(Protocol.createRoom, roomName, Protocol.myID, ccView.selected_ID.toString());
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							//ccView.dispose();
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		Object obj = ie.getSource();
		if (ie.getStateChange() == ie.SELECTED) {
			System.out.println("2");
			ccView.selected_ID.add(((JCheckBox) ie.getSource()).getText()); // 체크박스의 값 들어가야함.
		}

		else if (ie.getStateChange() == ie.DESELECTED) {
			ccView.selected_ID.remove(((JCheckBox) ie.getSource()).getText()); // 체크박스의 값 들어가야함.
		}
		
	}

}
