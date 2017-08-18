package edu.com;

import javax.swing.filechooser.FileNameExtensionFilter;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.Time;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

public class mp3Realize implements Runnable, ActionListener, ControllerListener {

	Player player;
	File currentFile;
	layout mp3Layout = new layout();//����mp3�������
	int currentIndex;
	boolean isPause = false;
	Thread thread;
	Component mediaControl; // ���������ſ������
	String s;

	int mediatime;//���Ž���ʱ��
	int mode;//����ģʽ
	

	public mp3Realize() {

		thread = new Thread(this);
		thread.start();
		mp3Layout.setVisible(true);

		/*
		 * ��Ӹ���������
		 */
		mp3Layout.addSong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (e.getSource() == mp3Layout.addSong) {
					addSong();
				}
			}

		});

		/*
		 * �˳���ť
		 */

		mp3Layout.Exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if (e.getSource() == mp3Layout.Exit) {
					System.exit(0);
				}
			}
		});

		/*
		 * ��� ˵����ť������
		 */
		mp3Layout.declaration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String text0 = "             Maker: ��־ΰ\n";
				String text1 = "             Vision: 1.0\n";
				String text = text0 + text1;
				if (e.getSource() == mp3Layout.declaration) {
					showMessage(text);
				}
			}

		});
		/*
		 * ʹ�ð���˵���¼�������
		 */
		
		mp3Layout.helpBt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				String text0 = " 1�����ȵ���˵��µ���Ӱ�ť�����wav��ʽ����Ƶ,\n��Ƶ�ĸ��Ҫ����ͬһ��Ŀ¼��\n";
				String text1 = " 2��Ȼ��ѡ�񲥷�ģʽ��Ĭ�ϵ�˳�򲥷�\n";
				String text2 = " 3��ѡ�����������ţ���ʼ�������֮��\n";
				String text3 = " 4����ť�Ĺ��ܺ���ҵ���ֲ������Ĺ�������\n";
				String text4 = " 5������ѡ�У�������Ż���˫������\n";
				String text5 = " 6��ֹͣ��ť��ֹͣ���ţ�������ţ���ͷ��ʼ����\n";
				String text6 = " 7����ͣ��ť��ֹͣ���ţ�������ţ�����ֹͣ�ĵط�����\n";
				String text = text0+text1+text2+text3+text4+text5+text6;	
				if(e.getSource()==mp3Layout.helpBt){
					showMessage(text);					
				}	
			}
			
		});
		
		
		/*
		 * ˳�򲥷Ű�ť�¼�������
		 */
		
		mp3Layout.nextBt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			  if(e.getSource()==mp3Layout.nextBt)
				  mode=0;	
			}			
		});
		/*
		 * ������Ű�ť�¼�������
		 */
		
		mp3Layout.randomBt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			  if(e.getSource()==mp3Layout.randomBt)
				  mode=1;	
			}			
		});
		/*
		 * �������Ű�ť�¼�������
		 */
		mp3Layout.singleBt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			  if(e.getSource()==mp3Layout.singleBt)
				  mode=2;	
			}			
		});
		
		

		/*
		 * ���Ű�ť������
		 */

		mp3Layout.btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btPlay) {
					play();
				}
			}
		});
		/*
		 * ��ͣ��ť������
		 */

		mp3Layout.btPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btPause) {
					pause();
				}
			}
		});

		/*
		 * ֹͣ��ť������
		 */

		mp3Layout.btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btStop) {
					stopS();

				}

			}
		});
		/*
		 * ��һ�׸�����ť������
		 */

		mp3Layout.btPre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btPre) {
					if (mode == 0) {
						pre();
					} else if (mode == 1) {
						random();
					} else if (mode == 2) {
						single();
					}

				}
			}
		});
		/*
		 * ��һ�׸�����ť������
		 */

		mp3Layout.btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btNext) {
					if(mode==0){
						next();
					}else if(mode==1){
						random();
					}else if(mode==2){
						single();
					}
					
				}

			}
		});

		/*
		 * ��굥���¼�������
		 */

		mp3Layout.list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					/*if (player != null) {
						player.close();
						mediaControl.setVisible(false);
					}*/

					currentIndex = mp3Layout.list.locationToIndex(e.getPoint());
					mp3Layout.currentItem = mp3Layout.listModel.get(currentIndex);
					mp3Layout.list.setSelectedIndex(currentIndex);
					currentFile = new File(mp3Layout.currentItem.getPath());
					System.out.println("����ѡ�У�������Ű�ť����ʼ���ţ�����");
				}
			}

		});

		/*
		 * ���˫���¼�������
		 */

		mp3Layout.list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (player != null) {
						player.close();
					}
					currentIndex = mp3Layout.list.locationToIndex(e.getPoint());
					mp3Layout.currentItem = mp3Layout.listModel.get(currentIndex);
					mp3Layout.list.setSelectedIndex(currentIndex);
					currentFile = new File(mp3Layout.currentItem.getPath());
					System.out.println("˫����ʼ���ţ�����");
					play();					
				}
			}

		});

	}

	/*
	 * ����
	 */
	public void play() {
		if (isPause) {
			player.start();
			System.out.println("��ͣ���������Ų��ţ�����");
			isPause = false;

		} else {
			try {
				if (player != null) {
					player.close();
					mediaControl.setVisible(false);
				}
				player = Manager.createRealizedPlayer(currentFile.toURI().toURL());
				player.prefetch();
				player.setMediaTime(new Time(0.0));// ��Ī��ʱ��ο�ʼ����

				mediaControl = player.getControlPanelComponent(); // ȡ�ò��ſ������
				mediaControl.setBounds(25, 60, 590, 20);
				mediaControl.setBackground(Color.WHITE);
				if (mediaControl != null) {
					mp3Layout.contentPane.add(mediaControl);
				}
				player.addControllerListener(this);// ʹ��addControllerListener����Ϊ�������Ŀ�������������
													// ע��˼�������Ŀ����ֻҪ����������һ��ý���¼�����ô
													// ��applet�����Զ�����controllerUpdate������
				player.start();

				System.out.println("��ʼ���ţ�����");

			} catch (Exception e1) {
				showMessage("���ļ����󣡣���");
				return;
			}
		}
	}

	// ��ͣ
	public void pause() {
		player.stop();
		System.out.println("��ͣ����");
		isPause = true;
	}

	// ֹͣ
	public void stopS() {
		player.stop();
		mediaControl.setVisible(false);
		mediaControl.setVisible(true);
		player.deallocate();
		// player.close();
		System.out.println("ֹͣ���ţ�������Ű�ť����ͷ����");
		currentFile = new File(mp3Layout.currentItem.getPath());
	}

	// ��һ��

	public void pre() {

		if (currentIndex == 0) {
			currentIndex = mp3Layout.listModel.getSize() - 1;
		} else {
			currentIndex--;
		}
		mp3Layout.currentItem = (ListItem) mp3Layout.listModel.get(currentIndex);
		mp3Layout.list.setSelectedIndex(currentIndex);
		currentFile = new File(mp3Layout.currentItem.getPath());
		if (player != null) {
			player.close();
			player.deallocate();
			System.out.println("�رյ�ǰplayer");
		}
		isPause = false;
		System.out.println("��һ��");
		mediaControl.setVisible(false);
		play();

	}

	// ��һ��
	public void next() {

		if (currentIndex == mp3Layout.listModel.getSize() - 1) {
			currentIndex = 0;
		} else {
			currentIndex++;
		}
		mp3Layout.currentItem = mp3Layout.listModel.get(currentIndex);
		mp3Layout.list.setSelectedIndex(currentIndex);
		currentFile = new File(mp3Layout.currentItem.getPath());
		// stopS();
		if (player != null) {
			player.close();
			player.deallocate();
			System.out.println("�رյ�ǰplayer");
		}
		isPause = false;
		System.out.println("��һ��");
		mediaControl.setVisible(false);
		play();

	}
	
	/*
	 * �������
	 */
	public void random()
	{
		currentIndex=(int)(Math.random()*(mp3Layout.listModel.getSize()-1))+1;
		mp3Layout.list.setSelectedIndex(currentIndex);
		mp3Layout.currentItem = mp3Layout.listModel.get(currentIndex);
		currentFile = new File(mp3Layout.currentItem.getPath());
		if (player != null)
			player.close();
		player.deallocate();
		System.out.println("�رյ�ǰplayer");
		isPause = false;
		mediaControl.setVisible(false);
		play();
	}
	/*
	 * ��������
	 */
	public void single(){
		System.out.println("�������Ž���");
		player.setMediaTime(new Time(0));
		System.out.println("����ѭ��");
		player.start();	
		
	}
	//�б�ģ��
	public DefaultListModel<ListItem> load() {
		File file = new File("list.lst");
		DefaultListModel<ListItem> dlm = new DefaultListModel<ListItem>();
		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				Integer size = (Integer) ois.readObject();
				if (size != 0) {
					for (int i = 0; i < size; i++) {
						ListItem item = (ListItem) ois.readObject();
						dlm.addElement(item);
					}
				}
				ois.close();
				return dlm;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return dlm;
	}

	// ��Ӹ���
	public void addSong() {

		JFileChooser jfc = new JFileChooser();// �ļ���Դѡ����
		FileNameExtensionFilter filter = new FileNameExtensionFilter("�����ļ�", "wav");
		jfc.setFileFilter(filter);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setMultiSelectionEnabled(true);// ��ѡ
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] files = jfc.getSelectedFiles();
			for (File f : files) {
				ListItem item = new ListItem(f.getName(), f.getAbsolutePath());
				mp3Layout.listModel.addElement(item);
			}
		}
	}
	
	/*
	 * ��ʾ���
	 */

	public void showLrc() {
		String gc = currentFile.getParent() + File.separator + currentFile.getName().split("\\.")[0] + ".lrc";
		File gcFile = new File(gc);
		try {
			if (gcFile.isFile() && gcFile.exists()) {
				//InputStreamReader read = new InputStreamReader(new FileInputStream(gcFile), encoding);// �����ʽ
				InputStreamReader read = new InputStreamReader(new FileInputStream(gcFile));// ���ַ���ȡ
				BufferedReader bufferedReader = new BufferedReader(read);

				String preTxt = null;
				String nextTxt = null;

				

		/*	while ((preTxt = bufferedReader.readLine()) != null) {
					if(preTxt.split("]")[0].substring(1, 2) == zero){
						System.out.println(preTxt.split("]")[0].substring(1, 2));	
						break;
					}
						
				}*/
				
				while ((preTxt = bufferedReader.readLine()) != null) {
					
					/*if(preTxt.split("]")[0].substring(1, 2) == zero){
						System.out.println(preTxt.split("]")[0].substring(1, 2));
						continue;
					}*/
				
						String[] time = preTxt.split("]")[0].substring(1).split(":");
						if (Integer.parseInt(time[0]) * 60 + Double.parseDouble(time[1]) > mediatime) {
							if (nextTxt != null)
								mp3Layout.lrcLable.setText(nextTxt.split("]")[1].trim());
							break;
						}
						nextTxt = preTxt;
					}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("����ļ����ݳ���");
			e.printStackTrace();
		}

	}
	

	
	/*
	 * ��ʾ��Ϣ
	 */

	public void showMessage(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
	
   /*
    * ���Ƹ���(non-Javadoc)
    * @see javax.media.ControllerListener#controllerUpdate(javax.media.ControllerEvent)
    */
	@Override
	public void controllerUpdate(ControllerEvent e) {

		// TODO Auto-generated method stub
		if (e instanceof PrefetchCompleteEvent) {
			System.out.println("��ʼ����");
			player.start();
			return;
		}
		if (e instanceof EndOfMediaEvent) {
			if(mode==0){//˳�򲥷�    ��һ��
				System.out.println("˳�򲥷�");
				next();	
			}else if(mode==1){//�������
				System.out.println("�����ʼ����");
				random();
				
			}else if(mode==2){//��������
				single();	
				
			}
		
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	/*	// TODO Auto-generated method stub
		if(e.getSource()==mp3Layout.nextBt){
			mode=0;			
		}else if(e.getSource()==mp3Layout.randomBt){
			mode=1;
		}else if(e.getSource()==mp3Layout.singleBt){
			mode=2;
		}*/
	}
	
	
	
	/*
	 * 
	 * ͼƬ����ת
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
            final int degree) {
    	
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        
        int type = bufferedimage.getColorModel().getTransparency();
        
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
	*/
	
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (player != null) {
				mediatime = (int) player.getMediaTime().getSeconds();
				try {
					showLrc();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

}
