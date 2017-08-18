package edu.com;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

public class layout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	JMenu menuCai;
	JMenuItem addSong;
	JMenuItem Exit;
	JMenuBar menuBar;
	JMenu about;
	JMenuItem declaration;
	JButton btPlay;
	JButton btStop;
	JButton btPause;
	JButton btPre;
	JButton btNext;
	JList<ListItem> list;// 列表
	ListItem currentItem;// 列表项
	DefaultListModel<ListItem> listModel;// 列表模型
	JScrollPane listScrollPane;
	Component mediaControl; // 音频播放控制组件

	int currentIndex;

	JLabel Jimage;
	JMenu menuMode;
	JRadioButtonMenuItem nextBt;
	JRadioButtonMenuItem randomBt;
	JRadioButtonMenuItem singleBt;
	

	JMenu help;
	JMenuItem helpBt;
	JLabel lrcLable;//歌词
	JLabel picture;

	public layout() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 557);
		setTitle("我的音乐播放器");

		menuBar = new JMenuBar();// 菜单条
		setJMenuBar(menuBar);

		menuCai = new JMenu("\u83DC\u5355\r\n");// 菜单
		menuBar.add(menuCai);
		addSong = new JMenuItem("\u6DFB\u52A0\u6B4C\u66F2");// 添加歌曲按钮
		menuCai.add(addSong);
		Exit = new JMenuItem("\u9000\u51FA");// 退出按钮
		menuCai.add(Exit);

		menuMode = new JMenu("\u64AD\u653E\u6A21\u5F0F");
		menuBar.add(menuMode);

		nextBt = new JRadioButtonMenuItem("\u987A\u5E8F\u64AD\u653E");
		//nextBt.addActionListener((ActionListener) this);
		nextBt.setSelected(true);//设置默认为选中
		menuMode.add(nextBt);

		randomBt = new JRadioButtonMenuItem("\u968F\u673A\u64AD\u653E");
		//randomBt.addActionListener((ActionListener) this);
		menuMode.add(randomBt);

		singleBt = new JRadioButtonMenuItem("\u5355\u66F2\u64AD\u653E");
		//singleBt.addActionListener((ActionListener) this);
		menuMode.add(singleBt);
		

		ButtonGroup bg = new ButtonGroup();
		bg.add(nextBt);
		bg.add(randomBt);
		bg.add(singleBt);

		about = new JMenu("\u5173\u4E8E");// 关于
		menuBar.add(about);

		declaration = new JMenuItem("\u8F6F\u4EF6\u8BF4\u660E");// 软件说明按钮
		about.add(declaration);

		help = new JMenu("\u5E2E\u52A9");//帮助
		menuBar.add(help);
		helpBt = new JMenuItem("\u4F7F\u7528\u8BF4\u660E");//使用帮助
		help.add(helpBt);

		contentPane = new JPanel();// 界面面板
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);// 设置面板的布局，使用的是绝对布局

		btPre = new JButton("\u4E0A\u4E00\u9996");// 上一首按钮
		btPre.setBounds(24, 21, 93, 23);
		contentPane.add(btPre);

		btPlay = new JButton("\u64AD\u653E\r\n");//播放按钮
		btPlay.setBounds(148, 21, 93, 23);
		contentPane.add(btPlay);

		btPause = new JButton("\u6682\u505C");//暂停按钮
		btPause.setBounds(284, 21, 93, 23);
		contentPane.add(btPause);

		btStop = new JButton("停止");//停止按钮
		btStop.setBounds(404, 21, 93, 23);
		contentPane.add(btStop);

		btNext = new JButton("\u4E0B\u4E00\u9996");//下一首按钮
		btNext.setBounds(523, 21, 93, 23);
		contentPane.add(btNext);
		
		lrcLable = new JLabel("");//歌词
		lrcLable.setForeground(Color.BLUE);
		lrcLable.setHorizontalAlignment(SwingConstants.CENTER);
		lrcLable.setFont(new Font("楷体", Font.BOLD, 20));
		lrcLable.setBounds(295, 385, 338, 74);
		contentPane.add(lrcLable);


		listModel = load();
		list = new JList<ListItem>(listModel);// 列表 动态添加元素
		list.setBackground(Color.LIGHT_GRAY);
		listScrollPane = new JScrollPane();// 管理视口、可选的垂直和水平滚动条以及可选的行和列标题视口
		listScrollPane.setLocation(25, 100);
		listScrollPane.setSize(260, 380);

		if (list.getSelectedIndex() == -1 && listModel.size() > 0) {
			currentItem = listModel.get(0);
			list.setSelectedIndex(0);
			currentIndex = 0;
		}
		listScrollPane.setViewportView(list);
		contentPane.add(listScrollPane);

		/*
		 * 背景图片
		 */
		ImageIcon ii = new ImageIcon("image/background.jpg");// 加载图片
		Jimage = new JLabel(ii);
		// Jimage.setBounds(0, 0, -1, -1);
		Jimage.setIcon(ii);
		// Jimage.setBounds(0, 0, ii.getIconWidth(),ii.getIconHeight());
		// Jimage.setBounds(0, 0, 500, 500);
		getContentPane().add(Jimage);
		
	
		picture = new JLabel("");
		picture.setBounds(370, 161, 235, 190);
		ImageIcon picIcon=new ImageIcon("image/pic.jpg");
		//rotateImage(pic.jpg,10);
		 
		picture.setIcon(picIcon);
		contentPane.add(picture);
		
	}
	/*
	 * 旋转图片
	 */
	
	
/*    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
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
    }*/
	
	
	
	
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
	
	
}
