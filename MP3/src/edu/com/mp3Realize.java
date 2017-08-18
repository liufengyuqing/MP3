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
	layout mp3Layout = new layout();//创建mp3界面对象
	int currentIndex;
	boolean isPause = false;
	Thread thread;
	Component mediaControl; // 进度条播放控制组件
	String s;

	int mediatime;//播放进度时间
	int mode;//播放模式
	

	public mp3Realize() {

		thread = new Thread(this);
		thread.start();
		mp3Layout.setVisible(true);

		/*
		 * 添加歌曲监听器
		 */
		mp3Layout.addSong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (e.getSource() == mp3Layout.addSong) {
					addSong();
				}
			}

		});

		/*
		 * 退出按钮
		 */

		mp3Layout.Exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (e.getSource() == mp3Layout.Exit) {
					System.exit(0);
				}
			}
		});

		/*
		 * 软件 说明按钮监听器
		 */
		mp3Layout.declaration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String text0 = "             Maker: 刘志伟\n";
				String text1 = "             Vision: 1.0\n";
				String text = text0 + text1;
				if (e.getSource() == mp3Layout.declaration) {
					showMessage(text);
				}
			}

		});
		/*
		 * 使用帮助说明事件监听器
		 */
		
		mp3Layout.helpBt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				String text0 = " 1、首先点击菜单下的添加按钮，添加wav格式的音频,\n音频的歌词要放在同一个目录下\n";
				String text1 = " 2、然后选择播放模式，默认的顺序播放\n";
				String text2 = " 3、选择歌曲点击播放，开始你的音乐之旅\n";
				String text3 = " 4、按钮的功能和商业音乐播放器的功能类似\n";
				String text4 = " 5、单击选中，点击播放或者双击播放\n";
				String text5 = " 6、停止按钮：停止播放，点击播放，从头开始播放\n";
				String text6 = " 7、暂停按钮：停止播放，点击播放，接着停止的地方播放\n";
				String text = text0+text1+text2+text3+text4+text5+text6;	
				if(e.getSource()==mp3Layout.helpBt){
					showMessage(text);					
				}	
			}
			
		});
		
		
		/*
		 * 顺序播放按钮事件监听器
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
		 * 随机播放按钮事件监听器
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
		 * 单曲播放按钮事件监听器
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
		 * 播放按钮监听器
		 */

		mp3Layout.btPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btPlay) {
					play();
				}
			}
		});
		/*
		 * 暂停按钮监听器
		 */

		mp3Layout.btPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btPause) {
					pause();
				}
			}
		});

		/*
		 * 停止按钮监听器
		 */

		mp3Layout.btStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == mp3Layout.btStop) {
					stopS();

				}

			}
		});
		/*
		 * 上一首歌曲按钮监听器
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
		 * 下一首歌曲按钮监听器
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
		 * 鼠标单击事件监听器
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
					System.out.println("单击选中，点击播放按钮，开始播放！！！");
				}
			}

		});

		/*
		 * 鼠标双击事件监听器
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
					System.out.println("双击开始播放！！！");
					play();					
				}
			}

		});

	}

	/*
	 * 播放
	 */
	public void play() {
		if (isPause) {
			player.start();
			System.out.println("暂停结束，接着播放！！！");
			isPause = false;

		} else {
			try {
				if (player != null) {
					player.close();
					mediaControl.setVisible(false);
				}
				player = Manager.createRealizedPlayer(currentFile.toURI().toURL());
				player.prefetch();
				player.setMediaTime(new Time(0.0));// 从莫个时间段开始播放

				mediaControl = player.getControlPanelComponent(); // 取得播放控制组件
				mediaControl.setBounds(25, 60, 590, 20);
				mediaControl.setBackground(Color.WHITE);
				if (mediaControl != null) {
					mp3Layout.contentPane.add(mediaControl);
				}
				player.addControllerListener(this);// 使用addControllerListener将册为播放器的控制器监视器。
													// 注册此监视器的目的是只要播放器载入一个媒体事件，那么
													// 该applet将会自动调用controllerUpdate方法。
				player.start();

				System.out.println("开始播放！！！");

			} catch (Exception e1) {
				showMessage("打开文件错误！！！");
				return;
			}
		}
	}

	// 暂停
	public void pause() {
		player.stop();
		System.out.println("暂停播放");
		isPause = true;
	}

	// 停止
	public void stopS() {
		player.stop();
		mediaControl.setVisible(false);
		mediaControl.setVisible(true);
		player.deallocate();
		// player.close();
		System.out.println("停止播放，点击播放按钮，从头播放");
		currentFile = new File(mp3Layout.currentItem.getPath());
	}

	// 上一首

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
			System.out.println("关闭当前player");
		}
		isPause = false;
		System.out.println("上一首");
		mediaControl.setVisible(false);
		play();

	}

	// 下一首
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
			System.out.println("关闭当前player");
		}
		isPause = false;
		System.out.println("下一首");
		mediaControl.setVisible(false);
		play();

	}
	
	/*
	 * 随机播放
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
		System.out.println("关闭当前player");
		isPause = false;
		mediaControl.setVisible(false);
		play();
	}
	/*
	 * 单曲播放
	 */
	public void single(){
		System.out.println("单曲播放结束");
		player.setMediaTime(new Time(0));
		System.out.println("单曲循环");
		player.start();	
		
	}
	//列表模型
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

	// 添加歌曲
	public void addSong() {

		JFileChooser jfc = new JFileChooser();// 文件资源选择器
		FileNameExtensionFilter filter = new FileNameExtensionFilter("音乐文件", "wav");
		jfc.setFileFilter(filter);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setMultiSelectionEnabled(true);// 多选
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] files = jfc.getSelectedFiles();
			for (File f : files) {
				ListItem item = new ListItem(f.getName(), f.getAbsolutePath());
				mp3Layout.listModel.addElement(item);
			}
		}
	}
	
	/*
	 * 显示歌词
	 */

	public void showLrc() {
		String gc = currentFile.getParent() + File.separator + currentFile.getName().split("\\.")[0] + ".lrc";
		File gcFile = new File(gc);
		try {
			if (gcFile.isFile() && gcFile.exists()) {
				//InputStreamReader read = new InputStreamReader(new FileInputStream(gcFile), encoding);// 编码格式
				InputStreamReader read = new InputStreamReader(new FileInputStream(gcFile));// 按字符读取
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
			System.out.println("歌词文件内容出错");
			e.printStackTrace();
		}

	}
	

	
	/*
	 * 显示信息
	 */

	public void showMessage(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
	
   /*
    * 控制更新(non-Javadoc)
    * @see javax.media.ControllerListener#controllerUpdate(javax.media.ControllerEvent)
    */
	@Override
	public void controllerUpdate(ControllerEvent e) {

		// TODO Auto-generated method stub
		if (e instanceof PrefetchCompleteEvent) {
			System.out.println("开始播放");
			player.start();
			return;
		}
		if (e instanceof EndOfMediaEvent) {
			if(mode==0){//顺序播放    下一首
				System.out.println("顺序播放");
				next();	
			}else if(mode==1){//随机播放
				System.out.println("随机开始播放");
				random();
				
			}else if(mode==2){//单曲播放
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
	 * 图片的旋转
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
