package source;



/**
 * @author Can Özdemir
 * CoderLab
 * DDevilz.com
 * 
 */

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;

public class start extends JFrame {

	private static final long serialVersionUID=0L;
	private static final String version = "";
	
	private final ImageIcon upImage				=	new ImageIcon("images/up.gif");
	private final ImageIcon downImage			=	new ImageIcon("images/down.gif");
	private final ImageIcon aboutImage			=	new ImageIcon("images/info.gif");
	private final ImageIcon crackImage			=	new ImageIcon("images/calculate.gif");
	private final ImageIcon clearImage			=	new ImageIcon("images/clear.gif");
	private final ImageIcon contactImage		=	new ImageIcon("images/send_e-mail.png"); 
	private final ImageIcon emptyImage			=	new ImageIcon("images/empty.gif");
	private final ImageIcon exitImage			=	new ImageIcon("images/exit.png");
	private final ImageIcon helpImage			=	new ImageIcon("images/help.png"); 
	private final ImageIcon forwardImage		=	new ImageIcon("images/forward.gif");
	private final ImageIcon homeImage			=	new ImageIcon("images/home.gif");
	private final ImageIcon backImage			=	new ImageIcon("images/back.gif");
	private final ImageIcon minusImage			=	new ImageIcon("images/minus.gif");
	private final ImageIcon plusImage			=	new ImageIcon("images/plus.gif");
	private final ImageIcon saveImage			=	new ImageIcon("images/save.png");
	private final ImageIcon topicImage			=	new ImageIcon("images/topic.gif");
	private final ImageIcon selectHashListImage	=	new ImageIcon("images/openFolder.gif");
	private final ImageIcon selectWordListImage	=	new ImageIcon("images/open.png");
	private final ImageIcon appImage			=	new ImageIcon("images/web.png");
	private final ImageIcon deleteImage			=	new ImageIcon("images/delete.png");
	private final ImageIcon addImage			=	new ImageIcon("images/new.gif");
	private final ImageIcon searchImage			=	new ImageIcon("images/search.png");
	private final ImageIcon iconImage			=	new ImageIcon("images/url.gif");
	private final ImageIcon stopImage			=	new ImageIcon("images/stop.png");
	
	private Container contentPane;
	private Component me=this;
	
	private final JMenuBar menuBar=new JMenuBar();
	private final JPopupMenu popupMenu=new JPopupMenu();
	private final JToolBar toolBar=new JToolBar();
	
	private ButtonGroup bruteOrList;
	
	//	Açılış ekranı 
	private static Timer splashTimer = null;
	private Thread crackThread = null;
	
	private JList hashList;
	private JList wordList;
	private JList crackedResultList;
	
	private JScrollPane hashScrollList;
	private JScrollPane wordScrollList;
	
	private final ArrayList hashesList=new ArrayList();
	private final ArrayList wordsList=new ArrayList();
	private final ArrayList crackedList=new ArrayList();
	// Yardım Sayfaları listesi.
	private final ArrayList helpPages=new ArrayList();
	private final ArrayList helpPagesViewed=new ArrayList();
	private final ArrayList helpSearchResults=new ArrayList();
	
	private JButton addHash;
	private JButton selectHashFile;
	private JButton addWordListFile;
	private JButton startCracking;
	private JButton stopCracking;
	
	private JFileChooser fileChooser;
	
	private File hashFile;
	
	private final JTextField minLen=new JTextField();
	private final JTextField maxLen=new JTextField();
	
	private final JComboBox algorithm=new JComboBox(new String[]{"MD2","MD5","SHA-1","SHA-256","SHA-384","SHA-512"});
	private final JComboBox strength=new JComboBox(new String[] {"Harf [a-z]","Harf ve Rakam[a-z][0-9]", "Harf, Rakam ve Sembol [a-z][0-9][!-~]"});	
	
	private JLabel status;
	
	private JProgressBar statusProgressBar;
	
	private MessageDigest md;
	
	private byte buffer[]=new byte[8192];
	private byte digest[]=new byte[8192];
	
	private boolean bruteforce=false;
	private boolean caseSensitive=false;
	private boolean started=false;
	

	public start()
	{
		setTitle("eclipse - MD2 - Hash Cracker | ddevilz.com");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(650	,430);
		setLocation( (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-getWidth())/2 , (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-getHeight())/2 );
		setResizable(false);
		setIconImage(appImage.getImage());
		
		contentPane=getContentPane();
		
		setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {				
			}
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
			public void windowClosed(WindowEvent arg0) {
			}
			public void windowDeactivated(WindowEvent arg0) {
			}
			public void windowDeiconified(WindowEvent arg0) {
			}
			public void windowIconified(WindowEvent arg0) {
			}
			public void windowOpened(WindowEvent arg0) {
			}						
		});
				
		setMyMenuBar();
		setMyToolBar();
		setCenterPanel();
		setMyContextMenu();
		
		JPanel bottom=new JPanel();
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));
		
		JPanel row=new JPanel();
		JLabel answer=new JLabel();
		answer.setText("Cracklenen Hashler:");
		answer.setHorizontalAlignment(JTextField.CENTER);
		answer.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
		row.add(answer);
		
		crackedResultList=new JList();
		crackedResultList.setVisibleRowCount(6);
		crackedResultList.setCellRenderer(new ListCellRenderer() {
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JPanel cell=new JPanel();
				cell.setLayout(new BorderLayout());
				JLabel count=new JLabel((arg2+1)+". ");
				JLabel text=new JLabel(arg1.toString());
				cell.setToolTipText(arg1.toString());
				cell.setBackground(arg3?new Color(170,190,210):Color.white);
				cell.add(count, BorderLayout.WEST);
				cell.add(text,BorderLayout.CENTER);
				return cell;
			}
		});
		JScrollPane cr=new JScrollPane(crackedResultList);
		cr.setPreferredSize(new Dimension(256,96));
		row.add(cr);
		
		JPanel controlButtons=new JPanel();
		controlButtons.setLayout(new BoxLayout(controlButtons, BoxLayout.Y_AXIS));
		stopCracking = new JButton("İptal");
		stopCracking.setVisible(false);
		stopCracking.setIcon(stopImage);
		stopCracking.setCursor(Cursor.getDefaultCursor());
		stopCracking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopHashCracking();
			}
		});
		
		startCracking = new JButton("Başlat");
		startCracking.setIcon(crackImage);
		startCracking.setCursor(Cursor.getDefaultCursor());
		startCracking.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				startHashCracking();
			}
		});
		controlButtons.add(startCracking);
		controlButtons.add(stopCracking);
		row.add(controlButtons);
		bottom.add(row);
		
		JPanel statusBar=new JPanel();
		statusBar.setLayout(new BoxLayout(statusBar,BoxLayout.X_AXIS));
		
		status=new JLabel();
		status.setHorizontalAlignment(JTextField.CENTER);
		status.setFont(new Font("Arial",Font.BOLD,12));
		status.setText("eclipse - Hash Cracker  |  ddevilz.com "+version);
		
		statusProgressBar=new JProgressBar();		
		statusProgressBar.setMinimum(0);
		statusProgressBar.setMaximum(100);
		statusProgressBar.setStringPainted(true);		
		statusProgressBar.setToolTipText("Crackleme devam ediyor");
		
		JSplitPane spl=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,status,statusProgressBar);
		spl.setDividerSize(1);
		spl.setDividerLocation(520);
		spl.setEnabled(false);
		statusBar.add(spl);
		bottom.add(statusBar);
		
		contentPane.add(bottom);
		
		showSplash(this);
	}
	

	private void stopHashCracking()
	{
		statusProgressBar.setValue(0);
		statusProgressBar.setString("");
		status.setText("eclipse - Hash Cracker "+version);
		startCracking.setText("Başlat");
		((JMenuItem)popupMenu.getSubElements()[0]).setText("Başlat");
		((JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[4]).setText("Başlat");
		((JButton)toolBar.getComponents()[7]).setEnabled(false);//stop
		
		stopCracking.setVisible(false);
		((JMenuItem)popupMenu.getSubElements()[1]).setEnabled(false);			//stop cracking context menu
		((JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[5]).setEnabled(false); //stop menu
		
		getRootPane().setCursor(Cursor.getDefaultCursor());
		started=false;
		crackThread.stop();
		crackThread=null;		
	}
	

	private void startHashCracking()
	{
		stopCracking.setVisible(true);
		((JMenuItem)popupMenu.getSubElements()[1]).setEnabled(true);			//stop cracking context menu
		((JMenuItem)popupMenu.getSubElements()[0]).setText("Durdur");	//start cracking context menu
		((JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[5]).setEnabled(true); //stop menu
		((JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[4]).setText("Durdur");
		((JButton)toolBar.getComponents()[7]).setEnabled(true);//stop
		startCracking.setText("Durdur");
		getRootPane().setCursor(Cursor.getDefaultCursor());
		
		started=!started;
		
		if(started)
		{
			if(crackThread==null)
				crack();
			else
			{
				getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				status.setText("Hashler çözülüyor, Lütfen Bekleyin!");
				crackThread.resume();				
			}
		}
		else
		{
			startCracking.setText("Devam et");
			((JMenuItem)popupMenu.getSubElements()[0]).setText("Devam et");	//start cracking context menu
			((JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[4]).setText("Devam et");
			status.setText("Devam et butonuna tıklarsanız hash çözmeye devam eder.");
			crackThread.suspend();
		}
	}
	

	private void getHash()
	{
		String hash=JOptionPane.showInputDialog(me,"Hash girin:","Bilgilendirme",JOptionPane.QUESTION_MESSAGE);
		if(hash==null||hash.equals(""))
			return;
		hashesList.add(hash);
		hashList.setListData(hashesList.toArray());
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	

	private void getHashFile()
	{
		fileChooser=new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f)
			{		    
				return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
			}
			public String getDescription()
			{
				return "Metin Dosyası";
			}
		});
		int option=fileChooser.showOpenDialog(null);
		if(option==JFileChooser.APPROVE_OPTION)
		{
			hashFile=fileChooser.getSelectedFile();
			try {
				DataInputStream fc=new DataInputStream(new FileInputStream(hashFile));
				while(fc.available()>0)
				{
					hashesList.add(fc.readLine());
				}
				hashList.setListData(hashesList.toArray());
				fc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	

	private void getWordListFile()
	{
		fileChooser=new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f)
			{		    
		       	return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
			}
			public String getDescription()
			{
				return "Metin Dosyası";
			}
		});
		int option=fileChooser.showOpenDialog(null);
		if(option==JFileChooser.APPROVE_OPTION)
		{
			wordsList.add(fileChooser.getSelectedFile().getAbsolutePath());
			wordList.setListData(wordsList.toArray());
		}
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	
	
	private void crack()
	{
		crackedList.clear();
		crackedResultList.setListData(crackedList.toArray());
		crackThread=new Thread() {
			public void run()
			{
				try {
					buffer = new byte[8192];
					digest = new byte[8192];		
					statusProgressBar.setValue(0);
					status.getGraphics().clearRect(status.getX(),status.getY(),status.getWidth(),status.getHeight());
					status.setText("Çözülüyor, Lütfen bekleyin...");
					status.update(status.getGraphics());					
					getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
					if((!bruteforce&&wordsList.isEmpty())&&!hashesList.isEmpty())
					{
						status.setText("Lütfen wordlist seçin");
					}
					else if(hashesList.isEmpty()&&!wordsList.isEmpty())
					{
						status.setText("Lütfen hash listesini seçin");
					}
					else if(hashesList.isEmpty()&&wordsList.isEmpty())
					{
						status.setText("Lütfen hash listesini seçin"+(bruteforce?"":" ve wordlist leri seçin."));
					}
					else if(!bruteforce)	//word list attack
					{
						String hex="";
						String word="";
						DataInputStream f=null;
						boolean flag=false;
						int fsize=0,rsize=0;
						for(int j=0;j<hashesList.size();j++)//one hash at a time
						{
							for(int i=0;i<wordsList.size();i++) //one wordlist at a time
							{
								f=new DataInputStream(new FileInputStream(wordsList.get(i).toString()));
								fsize=0;
								while(f.available()>0)
								{
									f.readLine();
									fsize++;
								}
								rsize=0;
								f.close();
								f=new DataInputStream(new FileInputStream(wordsList.get(i).toString()));
								while(f.available()>0) //one word at a time from the wordlist
								{
									word=f.readLine();
									buffer=word.getBytes();
								    md.reset();
								    md.update(buffer);
							    	digest=md.digest();
							    	hex="";
							    	for (int k = 0; k < digest.length; k++) 
									{
										int b = digest[k] & 0xff;
										if (Integer.toHexString(b).length() == 1) hex = hex + "0";
										hex  = hex + Integer.toHexString(b);										
									}
									if(hex.equals(hashesList.get(j).toString()))
									{
										crackedList.add(word);
										flag=true;
										break;
									}
									statusProgressBar.setValue( ((++rsize) * 100)/fsize);
									//statusProgressBar.update(statusProgressBar.getGraphics());
								}
							}
							if(flag==false)
							{
								status.setText("Hash şifresi"+hashesList.get(j).toString()+" seçilen wordlist'in yok!");
								word="Çözülme başarısız!" +hashesList.get(j).toString();
								crackedList.add(word);
							}
						}	
						f.close();
						crackedResultList.setListData(crackedList.toArray());
					}
					else if(minLen.getText().equals("")||maxLen.getText().equals("")) // min max empty
					{
						status.setText("Lütfen kelime uzunluğu aralığını girin");
					}
					else //bruteforce
					{
						int min=Integer.parseInt(minLen.getText());
						int max=Integer.parseInt(maxLen.getText());
						int i=min;
						String word="";
						boolean flag=false;
						for(int j=0;j<hashesList.size();j++)
						{
							i=min;
							while(i<=max)
							{
								if(strength.getSelectedIndex()==0&&caseSensitive)
									word=bruteCrackCaseAlpha(i,hashesList.get(j).toString());
								else if(strength.getSelectedIndex()==0)
									word=bruteCrackAlpha(i,hashesList.get(j).toString());
								else if(strength.getSelectedIndex()==1&&caseSensitive)
									word=bruteCrackCaseAlphaNum(i,hashesList.get(j).toString());
								else if(strength.getSelectedIndex()==1)
									word=bruteCrackAlphaNum(i,hashesList.get(j).toString());
								else if(strength.getSelectedIndex()==2&&caseSensitive)
									word=bruteCrackCaseAlphaNumSymbols(i,hashesList.get(j).toString());
								else
									word=bruteCrackAlphaNumSymbols(i,hashesList.get(j).toString());
								if(word==null)
								{
									flag=false;
								}
								else
								{
									status.setText("Çözüldü!");
									crackedList.add(word);
									flag=true;
									break;
								}
								i++;
							}
							if(flag==false)
							{
								status.setText("Hash şifresi "+hashesList.get(j).toString()+" verilen aralıkta değil.");
								word="Bunu çözemez!";
								crackedList.add(word);
							}
						}
						crackedResultList.setListData(crackedList.toArray());
					}
					setMyMenuBarEnabled();
					setMyContextMenuEnabled();
					setMyToolBarButtonsEnabled();
				} 
				catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ThreadDeath e) {
					e.printStackTrace();
				}
				startCracking.getGraphics().clearRect(status.getX(),status.getY(),status.getWidth(),status.getHeight());
				startCracking.setText("Başlat");
				startCracking.update(startCracking.getGraphics());
				getRootPane().setCursor(Cursor.getDefaultCursor());
				statusProgressBar.setValue(100);
				stopCracking.setVisible(false);
				//statusProgressBar.update(statusProgressBar.getGraphics());
			}
		};
		
		try {
			crackThread.setDaemon(true);
			crackThread.start();
			//crackThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setExtendedState(JFrame.NORMAL);
		toFront();
	}
	

	private void setWordListContextMenu()
	{
		JPopupMenu menu=new JPopupMenu();
		JMenuItem item=new JMenuItem("Sil", deleteImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				deleteHash();
			}
		});
		menu.add(item);
		item=new JMenuItem("Wordlist seç", selectWordListImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getHashFile();
			}
		});
		menu.add(item);
		wordList.setComponentPopupMenu(menu);
	}
	

	private void setHashListContextMenu()
	{
		JPopupMenu menu=new JPopupMenu();
		JMenuItem item=new JMenuItem("Hash ekle", addImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getHash();
			}
		});
		menu.add(item);
		item=new JMenuItem("Hash sil", deleteImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				deleteHash();
			}
		});
		menu.add(item);
		item=new JMenuItem("Toplu halde seç", selectHashListImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getHashFile();
			}
		});
		menu.add(item);
		hashList.setComponentPopupMenu(menu);
	}
	

	private void setMyContextMenuEnabled()
	{
		JMenuItem item=(JMenuItem)popupMenu.getSubElements()[2];	//Clear Menu
		item.setEnabled(!hashesList.isEmpty()||!wordsList.isEmpty());
		item=(JMenuItem)popupMenu.getSubElements()[3];	//Save Menu
		item.setEnabled(!crackedList.isEmpty());
		item=(JMenuItem)popupMenu.getSubElements()[5];	//Save Menu
		item.setEnabled(!bruteforce);
	}
	

	private void setMyContextMenu()
	{
		popupMenu.setCursor(Cursor.getDefaultCursor());
		JMenuItem item=new JMenuItem("Başlat",crackImage);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				startHashCracking();
			}
		});
		popupMenu.add(item);
		item=new JMenuItem("İptal",stopImage);
		item.setEnabled(false);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				stopHashCracking();
			}
		});
		popupMenu.add(item);
		popupMenu.addSeparator();
		
		item = new JMenuItem("Temizle",clearImage);
		item.setEnabled(false);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				clear();
			}
		});
		popupMenu.add(item);
				
		item = new JMenuItem("Kaydet",saveImage);
		item.setEnabled(false);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				saveInFile();
			}
		});
		popupMenu.add(item);
		popupMenu.addSeparator();
		
		JMenu subMenu=new JMenu("Hash");
		item=new JMenuItem("Hash ekle", addImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHash();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Hash sil", deleteImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteHash();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Toplu halde seç", selectHashListImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHashFile();
			}
		});
		subMenu.add(item);		
		popupMenu.add(subMenu);		
		
		subMenu=new JMenu("WordList");
		item=new JMenuItem("WordList 'i sil", deleteImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteWordList();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Wordlist dosyası seç", selectWordListImage);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getWordListFile();
			}
		});
		subMenu.add(item);	
		popupMenu.add(subMenu);		
		popupMenu.addSeparator();
		
		item = new JMenuItem("Çıkış",exitImage);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				exit();
			}
		});
		popupMenu.add(item);
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) { checkPopup(e); }
			public void mouseClicked(MouseEvent e) { checkPopup(e); }
			public void mouseReleased(MouseEvent e) { checkPopup(e); }
			private void checkPopup(MouseEvent e) {
				setMyMenuBarEnabled();
				if (e.isPopupTrigger(  )) {
					setMyMenuBarEnabled();
					setMyContextMenuEnabled();
					setMyToolBarButtonsEnabled();
					popupMenu.show(e.getComponent(  ), e.getX(  ), e.getY(  ));
				}
		  	}
		});
	}
	

	private void setMyMenuBarEnabled()
	{
		JMenuItem item=(JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[0]; //Clear
		item.setEnabled(!wordsList.isEmpty()||!hashesList.isEmpty());
		item=(JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[1]; //Save
		item.setEnabled(!crackedList.isEmpty());
		item=(JMenuItem)menuBar.getSubElements()[0].getSubElements()[0].getSubElements()[3]; //Clear
		item.setEnabled(!bruteforce);
	}
	

	private void setMyMenuBar()
	{
		menuBar.setCursor(Cursor.getDefaultCursor());
		JMenu menu=new JMenu("Dosya");
		menu.setMnemonic('F');
		JMenuItem item=new JMenuItem("Temizle", clearImage);
		item.setMnemonic('C');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				clear();
			}
		});
		menu.add(item);		
		item=new JMenuItem("Kaydet", saveImage);
		item.setMnemonic('S');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				saveInFile();
			}
		});
		menu.add(item);		
		JMenu subMenu=new JMenu("Hash");
		subMenu.setMnemonic('H');
		item=new JMenuItem("Hash ekle", addImage);
		item.setMnemonic('A');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHash();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Hash sil", deleteImage);
		item.setMnemonic('D');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteHash();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Toplu halde seç", selectHashListImage);
		item.setMnemonic('S');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHashFile();
			}
		});
		subMenu.add(item);		
		menu.add(subMenu);		
		subMenu=new JMenu("WordList");
		subMenu.setMnemonic('W');
		item=new JMenuItem("WordList 'i sil", deleteImage);
		item.setMnemonic('D');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteWordList();
			}
		});
		subMenu.add(item);
		item=new JMenuItem("Wordlist seç", selectWordListImage);
		item.setMnemonic('S');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getWordListFile();
			}
		});
		subMenu.add(item);	
		menu.add(subMenu);		
		menu.addSeparator();		
		item=new JMenuItem("Başlat", crackImage);
		item.setMnemonic('t');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				startHashCracking();
			}
		});
		menu.add(item);
		item=new JMenuItem("İptal", stopImage);
		item.setMnemonic('o');
		item.setEnabled(false);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				stopHashCracking();
			}
		});
		menu.add(item);
		menu.addSeparator();
		item=new JMenuItem("Çıkış",exitImage);
		item.setMnemonic('X');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				exit();
			}
		});
		menu.add(item);		
		menuBar.add(menu);
		
		menu=new JMenu("Yardım");
		menu.setMnemonic('H');
		item=new JMenuItem("Yardım",helpImage);
		item.setMnemonic('H');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getHelpContents();
			}
		});
		menu.add(item);
		item=new JMenuItem("eclipse - Hash Cracker Hakkında",aboutImage);
		item.setMnemonic('A');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getAboutHC();
			}
		});
		menu.add(item);
		menu.addSeparator();
		item=new JMenuItem("Geliştirici hakkında", contactImage);
		item.setMnemonic('B');
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				getContactInfo();
			}
		});
		menu.add(item);
		menuBar.add(menu);
		
		setJMenuBar(menuBar);
	}
	

	private void setCenterPanel()
	{
		JPanel center = new JPanel();
		center.setBorder(BorderFactory.createEmptyBorder(1,0,0,0));
		
		JPanel hashes = new JPanel();		
		hashes.setLayout(new BoxLayout(hashes,BoxLayout.Y_AXIS));
		hashes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0,0,0,0),	"Hash Listesi", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.BOLD, 10), Color.BLACK));
		
		JPanel hashbtns=new JPanel();
		hashbtns.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
		hashList = new JList();
		hashList.setToolTipText("<html>Hash içerir<br>çözmek istediğiniz.</html>");
		hashList.setVisibleRowCount(10);
		hashList.setCellRenderer((JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) -> {
                    JPanel cell=new JPanel();
                    cell.setLayout(new BorderLayout());
                    JLabel count=new JLabel((arg2+1)+". ");
                    JLabel text=new JLabel(arg1.toString());
                    cell.setToolTipText(arg1.toString());
                    cell.setBackground(arg3?new Color(170,190,210):Color.white);
                    cell.add(count, BorderLayout.WEST);
                    cell.add(text,BorderLayout.CENTER);
                    return cell;
                });
		hashList.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_DELETE)
				{
					deleteHash();
				}
				if(arg0.getKeyCode()==KeyEvent.VK_UP)
				{
					hashListMove(0);					
				}
				if(arg0.getKeyCode()==KeyEvent.VK_DOWN)
				{
					hashListMove(1);
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
				
			}			
		});
		setHashListContextMenu();
		hashScrollList=new JScrollPane(hashList);
		hashScrollList.setPreferredSize(new Dimension(256,160));		
		hashbtns.add(hashScrollList);
		
		JPanel updown=new JPanel();
		updown.setLayout(new BoxLayout(updown, BoxLayout.Y_AXIS));
		JButton up=new JButton();
		up.setIcon(upImage);
		up.setMargin(new Insets(0,0,0,0));
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				Object cur=hashList.getSelectedValue();
				hashListMove(0);
				hashList.setSelectedValue(cur, false);
			}
		});
		updown.add(up);
		JButton down=new JButton();
		down.setIcon(downImage);
		down.setMargin(new Insets(0,0,0,0));
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				Object cur=hashList.getSelectedValue();
				hashListMove(1);
				hashList.setSelectedValue(cur, true);
			}
		});
		updown.add(down);
		hashbtns.add(updown);
		hashes.add(hashbtns);
				
		JPanel btns=new JPanel();
		btns.setLayout(new BoxLayout(btns,BoxLayout.X_AXIS));
		addHash = new JButton("Hash ekle");
		addHash.setMargin(new Insets(0,0,0,0));
		addHash.setIcon(addImage);
		addHash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHash();
			}
		});
		btns.add(addHash);		
		JButton delete=new JButton("Hash sil");
		delete.setMargin(new Insets(0,0,0,0));
		delete.setIcon(deleteImage);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteHash();
			}
		});
		btns.add(delete);		
		selectHashFile=new JButton("Metin dosyası seç");
		selectHashFile.setMargin(new Insets(0,0,0,0));
		selectHashFile.setIcon(selectHashListImage);
		selectHashFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) 
			{
				getHashFile();
			}
		});
		btns.add(selectHashFile);
		
		hashes.add(btns);
		center.add(hashes);
		
		final JPanel cracking = new JPanel();		
		cracking.setLayout(new BoxLayout(cracking, BoxLayout.Y_AXIS));
		
		final JPanel wordListPanel=new JPanel();
		wordListPanel.setLayout(new BoxLayout(wordListPanel, BoxLayout.Y_AXIS));
		wordListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0,0,1,0),	"WordList 'ler", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.BOLD, 10), Color.BLACK));
		wordListPanel.setVisible(!bruteforce);
		JPanel wordbtns=new JPanel();
		wordList=new JList();
		wordList.setVisibleRowCount(8);
		wordList.setCellRenderer(new ListCellRenderer() {
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JPanel cell=new JPanel();
				cell.setLayout(new BorderLayout());
				JLabel count=new JLabel((arg2+1)+". ");
				JLabel text=new JLabel(arg1.toString().substring(arg1.toString().lastIndexOf("\\")+1));
				cell.setToolTipText(arg1.toString());
				cell.setBackground(arg3?new Color(170,190,210):Color.white);
				cell.add(count, BorderLayout.WEST);
				cell.add(text,BorderLayout.CENTER);
				return cell;
			}			
		});
		wordList.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_DELETE)
				{
					deleteWordList();
				}
				if(arg0.getKeyCode()==KeyEvent.VK_UP)
				{
					wordListMove(0);
					
				}
				if(arg0.getKeyCode()==KeyEvent.VK_DOWN)
				{
					wordListMove(1);
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}			
		});
		setWordListContextMenu();
		wordScrollList=new JScrollPane(wordList);
		wordScrollList.setPreferredSize(new Dimension(259,131));
		wordbtns.add(wordScrollList);
		updown=new JPanel();
		updown.setLayout(new BoxLayout(updown, BoxLayout.Y_AXIS));
		up=new JButton();
		up.setIcon(upImage);
		up.setMargin(new Insets(0,0,0,0));
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				Object cur=wordList.getSelectedValue();
				wordListMove(0);
				wordList.setSelectedValue(cur, true);
			}
		});
		updown.add(up);
		down=new JButton();
		down.setIcon(downImage);
		down.setMargin(new Insets(0,0,0,0));
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				Object cur=wordList.getSelectedValue();
				wordListMove(1);
				wordList.setSelectedValue(cur, true);
			}
		});
		updown.add(down);
		wordbtns.add(updown);
		wordListPanel.add(wordbtns);
		JPanel btn=new JPanel();
		btn.setLayout(new BoxLayout(btn,BoxLayout.X_AXIS));
		addWordListFile=new JButton("WordList seç");
		addWordListFile.setMargin(new Insets(0,0,0,0));
		addWordListFile.setIcon(selectWordListImage);
		addWordListFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) 
			{
				getWordListFile();
			}
		});
		btn.add(addWordListFile);
		JButton del=new JButton("Sil");
		del.setMargin(new Insets(0,0,0,0));
		del.setIcon(deleteImage);
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				deleteWordList();
			}
		});
		btn.add(del);		
		wordListPanel.add(btn);
				
		final JPanel brutePanel=new JPanel();
		brutePanel.setLayout(new BoxLayout(brutePanel, BoxLayout.Y_AXIS));
		brutePanel.setVisible(bruteforce);
		JPanel sel=new JPanel();
		JLabel stn=new JLabel("Türü");
		sel.add(stn);
		
		strength.setPreferredSize(new Dimension(240,30));
		sel.add(strength);
		brutePanel.add(sel);
		
		final JCheckBox cs=new JCheckBox("Büyük-Küçük Harf Duyarlı");
		cs.setSelected(false);
		cs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				caseSensitive=cs.isSelected();
			}
		});
		brutePanel.add(cs);
		
		JPanel minmax=new JPanel();
		minmax.setLayout(new BoxLayout(minmax,BoxLayout.Y_AXIS));
		JPanel min=new JPanel();
		min.setLayout(new BoxLayout(min,BoxLayout.X_AXIS));
		min.setBorder(BorderFactory.createEmptyBorder(5,50,5,30));
		JLabel mn=new JLabel("Minimum Kelime uzunluğu");
		mn.setBorder(BorderFactory.createEmptyBorder(1,1,1,45));
		min.add(mn);
		minLen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
				if(minLen.getText().length()>2) arg0.consume();
				String chars="0123456789";
				char arg=arg0.getKeyChar();
				int i=0;
				for(i=0;i<chars.length();i++)
				{
					if(chars.charAt(i)==arg)break;					
				}
				if(i==chars.length()) arg0.consume();
			}			
		});
		min.add(minLen);
		
		minmax.add(min);
		JPanel max=new JPanel();
		max.setLayout(new BoxLayout(max,BoxLayout.X_AXIS));
		max.setBorder(BorderFactory.createEmptyBorder(5,50,5,30));
		JLabel mx=new JLabel("Maximum Kelime uzunluğu");
		mx.setBorder(BorderFactory.createEmptyBorder(1,1,1,40));
		max.add(mx);
		maxLen.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
				if(maxLen.getText().length()>2) arg0.consume();
				String chars="0123456789";
				char arg=arg0.getKeyChar();
				int i=0;
				for(i=0;i<chars.length();i++)
				{
					if(chars.charAt(i)==arg)break;					
				}
				if(i==chars.length()) arg0.consume();
			}			
		});
		max.add(maxLen);
		minmax.add(max);
		brutePanel.add(minmax);
				
		JPanel a=new JPanel();//RadioButtons
		a.setLayout(new BoxLayout(a,BoxLayout.X_AXIS));
		a.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		bruteOrList=new ButtonGroup();
		final JRadioButton brute=new JRadioButton("Brute Force");
		brute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				if(brute.isSelected())
					bruteforce=true;
				wordListPanel.setVisible(!bruteforce);
				brutePanel.setVisible(bruteforce);
				setMyMenuBarEnabled();
				setMyContextMenuEnabled();
				setMyToolBarButtonsEnabled();
			}
		});
		bruteOrList.add(brute);
		a.add(brute);
		final JRadioButton list=new JRadioButton("Word List",true);
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				if(list.isSelected())
					bruteforce=false;
				wordListPanel.setVisible(!bruteforce);
				brutePanel.setVisible(bruteforce);
				setMyMenuBarEnabled();
				setMyContextMenuEnabled();
				setMyToolBarButtonsEnabled();
			}
		});
		bruteOrList.add(list);
		a.add(list);
		cracking.add(a);
		cracking.add(brutePanel);
		cracking.add(wordListPanel);		
		center.add(cracking);
		contentPane.add(center);
	}
	

	private void deleteHash()
	{
		if(hashList.isSelectionEmpty())
			return;
		hashesList.remove(hashList.getSelectedIndex());
		hashList.setListData(hashesList.toArray());
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	

	private void deleteWordList()
	{
		if(wordList.isSelectionEmpty())
			return;
		wordsList.remove(wordList.getSelectedIndex());
		wordList.setListData(wordsList.toArray());
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	

	private void wordListMove(int ud)
	{
		if(wordsList.size()>1)
		{
			if(ud==1 && !((wordsList.size()-1)<wordList.getSelectedIndex()+1))			//down
			{	Object cur=wordsList.get(wordList.getSelectedIndex());
				Object next=wordsList.get(wordList.getSelectedIndex()+1);
				wordsList.set(wordList.getSelectedIndex(),next);
				wordsList.set(wordList.getSelectedIndex()+1, cur);
				wordList.setListData(wordsList.toArray());
			}
			else if(ud==0 && !(wordList.getSelectedIndex()-1<0))		
			{
				Object prev=wordsList.get(wordList.getSelectedIndex()-1);
				Object cur=wordsList.get(wordList.getSelectedIndex());
				wordsList.set(wordList.getSelectedIndex(),prev);
				wordsList.set(wordList.getSelectedIndex()-1, cur);
				wordList.setListData(wordsList.toArray());
			}
		}
	}
	

	private void hashListMove(int ud)
	{
		if(hashesList.size()>1)
		{
			if(ud==1 && !((hashesList.size()-1)<hashList.getSelectedIndex()+1))			//down
			{	Object cur=hashesList.get(hashList.getSelectedIndex());
				Object next=hashesList.get(hashList.getSelectedIndex()+1);
				hashesList.set(hashList.getSelectedIndex(),next);
				hashesList.set(hashList.getSelectedIndex()+1, cur);
				hashList.setListData(hashesList.toArray());
			}
			else if(ud==0 && !(hashList.getSelectedIndex()-1<0))		
			{
				Object prev=hashesList.get(hashList.getSelectedIndex()-1);
				Object cur=hashesList.get(hashList.getSelectedIndex());
				hashesList.set(hashList.getSelectedIndex(),prev);
				hashesList.set(hashList.getSelectedIndex()-1, cur);
				hashList.setListData(hashesList.toArray());
			}
		}
	}
	

	private void setMyToolBarButtonsEnabled()
	{
		JButton btn=(JButton)toolBar.getComponents()[0];
		btn.setEnabled(!wordsList.isEmpty()||!hashesList.isEmpty());
		btn=(JButton)toolBar.getComponents()[1];		
		btn.setEnabled(!crackedList.isEmpty());
		btn=(JButton)toolBar.getComponents()[4];		
		btn.setEnabled(!bruteforce);
	}
	

	private void setMyToolBar()
	{
		toolBar.setFloatable(false);
		toolBar.setCursor(Cursor.getDefaultCursor());
		JButton btn=new JButton("",clearImage);
		btn.setToolTipText("Herşeyi temizlemek için tıklayın");
		btn.setEnabled(false);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				clear();
			}
		});
		toolBar.add(btn);
		
		btn=new JButton("",saveImage);
		btn.setEnabled(false);
		btn.setToolTipText("Sonucu kaydetmek için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				saveInFile();
			}
		});
		toolBar.add(btn);
		toolBar.addSeparator();
		
		btn=new JButton("",selectHashListImage);
		btn.setToolTipText("Hash dosyasını seçin");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHashFile();
			}
		});
		toolBar.add(btn);
		
		btn=new JButton("",selectWordListImage);
		btn.setToolTipText("WordList dosyasını seçin");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getWordListFile();
			}
		});
		toolBar.add(btn);
		toolBar.addSeparator();
				
		btn=new JButton("",crackImage);
		btn.setToolTipText("Hashleri çözmek için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				startHashCracking();
			}
		});
		toolBar.add(btn);
		btn=new JButton("",stopImage);
		btn.setEnabled(false);
		btn.setToolTipText("Hashleri çözümünü durdurmak için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				stopHashCracking();
			}
		});
		toolBar.add(btn);
		toolBar.addSeparator();
		
		btn=new JButton("",helpImage);
		btn.setToolTipText("Yardım İçeriğini almak için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getHelpContents();
			}			
		});
		toolBar.add(btn);
		
		btn=new JButton("",aboutImage);
		btn.setToolTipText("Hash hesaplaması için lütfen tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getAboutHC();
			}				
		});
		toolBar.add(btn);
		
		btn=new JButton("",contactImage);
		btn.setToolTipText("İletişim için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				getContactInfo();
			}				
		});
		toolBar.add(btn);

		toolBar.addSeparator();

		algorithm.setToolTipText("Şifreleme türünü seçin");
		algorithm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				status.setText(algorithm.getSelectedItem().toString()+"-"+" şifreleme türü seçildi");
				setTitle("eclipse - "+algorithm.getSelectedItem().toString()+ " - Hash Cracker | ddevilz.com");
			}
		});
		toolBar.add(algorithm);
		toolBar.addSeparator();
		
		btn=new JButton("",exitImage);
		btn.setToolTipText("Uygulamadan çıkmak için tıklayın");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				exit();
			}
		});
		toolBar.add(btn);
		contentPane.add(toolBar);
	}
	

	private void clear()
	{
		crackedList.clear();
		crackedResultList.setListData(crackedList.toArray());
		hashesList.clear();
		hashList.setListData(hashesList.toArray());
		wordsList.clear();
		wordList.setListData(wordsList.toArray());
		setMyMenuBarEnabled();
		setMyContextMenuEnabled();
		setMyToolBarButtonsEnabled();
	}
	

	private void saveInFile()
	{
		fileChooser=new JFileChooser();
		PrintWriter out=null;
		DataInputStream in=null;
		int option=-9999;
		boolean done=false;
		String fileName="";
		ArrayList data=new ArrayList();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int i=0;
		for(i=0;i<crackedList.size();i++) if(!crackedList.get(i).toString().equals("Böyle bir hash yok...")) break;
		if(i==crackedList.size()) return;
		if(!hashesList.isEmpty()&&(bruteforce||!wordsList.isEmpty()))
		{	
			try {
				while(!done || (option!=JOptionPane.CANCEL_OPTION)) {
					option=fileChooser.showSaveDialog(me);
					if(option==JFileChooser.APPROVE_OPTION&&!fileChooser.getSelectedFile().exists())
						fileName=(fileChooser.getSelectedFile().toString().endsWith(".txt")?fileChooser.getSelectedFile().toString():fileChooser.getSelectedFile().toString()+".txt");
					else
						if(option==JFileChooser.APPROVE_OPTION) fileName=fileChooser.getSelectedFile().toString();
					if(option==JFileChooser.APPROVE_OPTION)
					{
						if(!fileChooser.getSelectedFile().exists())
						{
							out=new PrintWriter(new FileOutputStream(fileName));
							for(i=0;i<crackedList.size();i++)
							{
								if(!crackedList.get(i).toString().equals("Böyle bir hash yok..."))
									out.println("Crack sonucu \""+hashesList.get(i)+"\" bu kullanıldı \""+algorithm.getSelectedItem().toString()+"\"  \""+crackedList.get(i).toString()+"\"");
							}
							out.close();
							status.setText(fileName+" kaydedildi");
							done=true;
							break;
						}
						else
						{
							option=JOptionPane.showConfirmDialog(me,"Dosya zaten varsa, dosyayı üzerine yazmak istediğinizden emin misiniz?","Onay",JOptionPane.YES_NO_CANCEL_OPTION); 
							if(option==JOptionPane.YES_OPTION)
							{
								String opn[]={"Üzerine yaz", "Ekle", "İptal"};
								option=JOptionPane.showOptionDialog(me,"Dosya zaten varsa, dosyayı üzerine yazmak istediğinizden emin misiniz?","Onay",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,opn,opn[0]);
								if(option==JOptionPane.YES_OPTION)
								{
									out=new PrintWriter(new FileOutputStream(fileName)); 
									for(i=0;i<crackedList.size();i++)
									{
										if(!crackedList.get(i).toString().equals("Böyle bir hash yok..."))
											out.println("Crack sonucu \""+hashesList.get(i)+"\" bu kullanıldı \""+algorithm.getSelectedItem().toString()+"\"  \""+crackedList.get(i).toString()+"\"");
									}
									out.close();
									status.setText(fileName+" kaydedildi");
									done=true;
									break;
								}
								else if(option==JOptionPane.NO_OPTION)
								{
									in=new DataInputStream(new FileInputStream(fileName));
									while(in.available()>0) data.add(in.readLine());
									in.close();
									out=new PrintWriter(new FileOutputStream(fileName));
									i=0;
									while(i<data.size()) {out.println(data.get(i).toString());i++;} 
									for(i=0;i<crackedList.size();i++)
									{
										if(!crackedList.get(i).toString().equals("Böyle bir hash yok..."))
											out.println("Crack sonucu \""+hashesList.get(i)+"\" bu kullanıldı \""+algorithm.getSelectedItem().toString()+"\"  \""+crackedList.get(i).toString()+"\"");
									}									
									out.close();
									status.setText(fileName+" kaydedildi");
									done=true;
									break;
								}
								else
									break;
							}
							else if(option==JOptionPane.NO_OPTION)
							{
								done=false;
								continue;
							}
							else
								break;
						}
					}
					else
						break;									
				}
			}
			catch (FileNotFoundException e) {
				status.setText("Dosya açılamıyor");
				e.printStackTrace();
			}
			catch (IOException e) {
				status.setText("Dosya açılamıyor");
				e.printStackTrace();
			}
			catch (ArrayIndexOutOfBoundsException e) {
				status.setText("Dosya açılamıyor");
				e.printStackTrace();
			}
		}
		else //if(hashesList.isEmpty()||wordsList.isEmpty())
		{
			status.setText("Hash hesaplamak için girin");
		}
	}
	

	private void getHelpContents()
	{
		helpPagesViewed.clear();
		final JDialog helpContents=new JDialog(this);
		
		DefaultMutableTreeNode root;
		DefaultMutableTreeNode innerNode;
		DefaultMutableTreeNode child;
		JScrollPane treeScroller;
		JScrollPane areaPane;
		JSplitPane split;
		JTabbedPane tabs;
		JToolBar toolBar;
		
		final JList resultList=new JList();
		final JTextField searchText=new JTextField(10);
		final JTree contents;
		final JEditorPane htmlArea=new JEditorPane();
		final JButton searchButton=new JButton(searchImage);
				
		UIManager.put("Tree.closedIcon",emptyImage);
		UIManager.put("Tree.collapsedIcon",plusImage);
		UIManager.put("Tree.expandedIcon",minusImage);
		UIManager.put("Tree.leafIcon",topicImage);
		UIManager.put("Tree.openIcon",emptyImage);
		
		helpPages.add("file:///"+new File("HelpContents/Index.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/Introduction.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/Notice.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/AdaptiveLicense.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/OpenSoftwareLicense.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/Support.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/GettingStarted.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/GettingAhead.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/Concepts.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/FAQ.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("HelpContents/ChangeLog.htm").getAbsolutePath());
		helpPages.add("file:///"+new File("doc/index.html").getAbsolutePath());
		
		helpContents.setLayout(new BorderLayout());
		helpContents.setTitle("Yardım");
		helpContents.setSize(800,600);
		helpContents.setLocationRelativeTo(me);
		helpContents.setModal(true);
		helpContents.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				searchText.requestFocusInWindow();
			}
			public void windowClosing(WindowEvent arg0) {
				helpContents.dispose();
			}						
		});
		helpContents.getRootPane().setDefaultButton(searchButton);
				
		toolBar=new JToolBar();
		JButton btn = new JButton("",backImage);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)
			{
				if(helpPagesViewed.isEmpty())
					return;
				else if(helpPagesViewed.indexOf(htmlArea.getPage())==0)
					return;
				try {
						htmlArea.setPage(helpPagesViewed.get(helpPagesViewed.indexOf(htmlArea.getPage())-1).toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		toolBar.add(btn);
		btn = new JButton("",forwardImage);
		toolBar.add(btn);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(helpPagesViewed.isEmpty())
					return;
				else if(helpPagesViewed.indexOf(htmlArea.getPage())==helpPagesViewed.size()-1)
					return;
				else {
					try {
						htmlArea.setPage(helpPagesViewed.get(helpPagesViewed.indexOf(htmlArea.getPage())+1).toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}				
			}
		});
		toolBar.addSeparator();
		btn=new JButton("",homeImage);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try {
					htmlArea.setPage(helpPages.get(0).toString());
					helpPagesViewed.add(new URL(helpPages.get(0).toString()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
		toolBar.add(btn);
		toolBar.addSeparator();
		/*btn=new JButton("Print");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		toolBar.add(btn);*/
		btn=new JButton("Çıkış");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				helpContents.dispose();
			}
		});
		toolBar.add(btn);
		toolBar.setFloatable(false);
		helpContents.add(toolBar,BorderLayout.NORTH);
		
		root=new DefaultMutableTreeNode("eclipse - Hash Cracker | ddevilz.com");
				
		innerNode =new DefaultMutableTreeNode("Genel");
		child =new DefaultMutableTreeNode("Giriş");
		innerNode.add(child);
	
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Destek");
		innerNode.add(child);
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Başlarken");
		child =new DefaultMutableTreeNode("Pencere Başlığı");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Menü Çubuğu");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Araç Çubuğu");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Alanlar");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Bağlam Menüsü");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Modlar");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Durum Çubuğu");
		innerNode.add(child);
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Hadi");
		child =new DefaultMutableTreeNode("Yardım al");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash cracker hakkında bilgi");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Developer İletişim");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Metin girişi");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash hesaplama");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash algoritması seçme");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Pencereyi temizlemek");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Sonuçları kaydetmek");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Seçme Modu");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash dosyasını seçme");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash kopyalama");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Çıkma");
		innerNode.add(child);
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Kavramlar");
		child =new DefaultMutableTreeNode("Hash");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash Algoritmaları");
		innerNode.add(child);
		child =new DefaultMutableTreeNode("Hash dosyası");
		innerNode.add(child);
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Değişiklik Günlüğü");
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Sorular");
		root.add(innerNode);
		
		innerNode =new DefaultMutableTreeNode("Java Belgeleri");
		root.add(innerNode);
		
		contents=new JTree(root);
		contents.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		contents.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0)
			{
				String selection=contents.getSelectionPath().getLastPathComponent().toString();
				try {
					if(selection.equals("Hash Calculator"))
						htmlArea.setPage(helpPages.get(0).toString());
					else if(selection.equals("Overview")) {}						
					else if(selection.equals("Introduction"))
						htmlArea.setPage(helpPages.get(1).toString());
					else if(selection.equals("Notice"))
						htmlArea.setPage(helpPages.get(2).toString());
					else if(selection.equals("Licenses")) {}
					else if(selection.equals("Adaptive Public License"))
						htmlArea.setPage(helpPages.get(3).toString());
					else if(selection.equals("Open Software License"))
						htmlArea.setPage(helpPages.get(4).toString());
					else if(selection.equals("Support"))
						htmlArea.setPage(helpPages.get(5).toString());
					else if(selection.equals("Getting Started"))
						htmlArea.setPage(helpPages.get(6).toString());
					else if(selection.equals("Window Title"))
						htmlArea.setPage(helpPages.get(6).toString()+"#title");
					else if(selection.equals("Menu Bar"))
						htmlArea.setPage(helpPages.get(6).toString()+"#menu");
					else if(selection.equals("Tool Bar"))
						htmlArea.setPage(helpPages.get(6).toString()+"#tool");
					else if(selection.equals("Fields"))
						htmlArea.setPage(helpPages.get(6).toString()+"#fields");
					else if(selection.equals("Context Menu"))
						htmlArea.setPage(helpPages.get(6).toString()+"#menu");
					else if(selection.equals("Modes"))
						htmlArea.setPage(helpPages.get(6).toString()+"#modes");
					else if(selection.equals("Status Bar"))
						htmlArea.setPage(helpPages.get(6).toString()+"#status");
					else if(selection.equals("Getting Ahead"))
						htmlArea.setPage(helpPages.get(7).toString());
					else if(selection.equals("Getting help"))
						htmlArea.setPage(helpPages.get(7).toString()+"#help");
					else if(selection.equals("Getting Information About Hash Calculator"))
						htmlArea.setPage(helpPages.get(7).toString()+"#about");
					else if(selection.equals("Getting Developer’s Contact Info"))
						htmlArea.setPage(helpPages.get(7).toString()+"#contact");
					else if(selection.equals("Inputting text"))
						htmlArea.setPage(helpPages.get(7).toString()+"#input");
					else if(selection.equals("Calculating Hash"))
						htmlArea.setPage(helpPages.get(7).toString()+"#calculating");
					else if(selection.equals("Selecting Hashing Algorithm"))
						htmlArea.setPage(helpPages.get(7).toString()+"#selectingalgo");
					else if(selection.equals("Clearing everything"))
						htmlArea.setPage(helpPages.get(7).toString()+"#clearing");
					else if(selection.equals("Saving result in a File"))
						htmlArea.setPage(helpPages.get(7).toString()+"#saving");
					else if(selection.equals("Choosing Mode"))
						htmlArea.setPage(helpPages.get(7).toString()+"#choosing");
					else if(selection.equals("Selecting a File to hash"))
						htmlArea.setPage(helpPages.get(7).toString()+"#selectingfile");
					else if(selection.equals("Copying the hash"))
						htmlArea.setPage(helpPages.get(7).toString()+"#copyhash");
					else if(selection.equals("Exiting"))
						htmlArea.setPage(helpPages.get(7).toString()+"#exit");
					else if(selection.equals("Concepts"))
						htmlArea.setPage(helpPages.get(8).toString());
					else if(selection.equals("Hash"))
						htmlArea.setPage(helpPages.get(8).toString()+"#hash");
					else if(selection.equals("Hashing Algorithms"))
						htmlArea.setPage(helpPages.get(8).toString()+"#algo");
					else if(selection.equals("Hash File"))
						htmlArea.setPage(helpPages.get(8).toString()+"#file");
					else if(selection.equals("Frequently Asked Questions (FAQ's)"))
						htmlArea.setPage(helpPages.get(9).toString());
					else if(selection.equals("Change Log"))
						htmlArea.setPage(helpPages.get(10).toString());
					else if(selection.equals("Java Docs"))
						htmlArea.setPage(helpPages.get(11).toString());
					helpPagesViewed.add(htmlArea.getPage());
				} catch (IOException e) {
						e.printStackTrace();
				}				
			}
		});
		treeScroller=new JScrollPane(contents);
		tabs=new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=((JTabbedPane)e.getSource()).getSelectedIndex();
				if(index==0)
				{
					htmlArea.getHighlighter().removeAllHighlights();
					searchText.setText("");
					helpSearchResults.clear();
					resultList.setListData(helpSearchResults.toArray());
				}
			}			
		});
		tabs.addTab("Contents",treeScroller);
		
		JPanel searchPanel=new JPanel();		
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		JLabel lbl=new JLabel();
		lbl.setOpaque(false);
		lbl.setText("<html><font color='red'>Search is case-sensitive</font><br>Enter text to search:</html>");
		searchPanel.add(lbl);
		JPanel srch=new JPanel();
		srch.setLayout(new BoxLayout(srch,BoxLayout.X_AXIS));
		srch.add(searchText);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				search(searchText.getText(), resultList);
			}
		});
		searchButton.setMargin(new Insets(0,0,0,0));
		srch.add(searchButton);
		searchPanel.add(srch);
		
		resultList.setVisibleRowCount(40);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.setCellRenderer(new ListCellRenderer() {
			public Component getListCellRendererComponent(JList arg0, Object arg1, int arg2, boolean arg3, boolean arg4) {
				JPanel panel=new JPanel();
				panel.setPreferredSize(new Dimension(arg0.getSize().width-20,20));
				panel.setLayout(new BorderLayout());
				JLabel count=new JLabel((arg2+1)+". ");
				panel.add(count,BorderLayout.WEST);
				JLabel label=new JLabel();
				StringTokenizer tk=new StringTokenizer(arg1.toString(),"@");
				String text=tk.nextToken();
				label.setText(text.substring(text.lastIndexOf('\\')+1, text.length()-4));
				panel.add(label,BorderLayout.CENTER);
				panel.setBackground(arg3?new Color(170,190,210):Color.white);
				panel.setToolTipText("<html><table><td>"+count.getText()+"</td><td width='10'></td><td>"+label.getText()+"</td></table><html>");
				return panel;
			}
		});
		resultList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if(!resultList.isSelectionEmpty())
					{	int index=resultList.getSelectedIndex();
						StringTokenizer st=new StringTokenizer(helpSearchResults.get(index).toString(),"@");
						String url=st.nextToken();
						if(url.equals(htmlArea.getPage().toString()))
							htmlArea.putClientProperty("done",Boolean.TRUE);
						htmlArea.setPage(url);
						if(htmlArea.getClientProperty("done").equals(Boolean.TRUE))
						{
							int position=Integer.parseInt(st.nextToken());
							String searchText=st.nextToken();
							htmlArea.getHighlighter().removeAllHighlights();
							htmlArea.getHighlighter().addHighlight(position, position+searchText.length(), new DefaultHighlightPainter(Color.red.brighter().brighter().brighter().brighter()));
							htmlArea.setCaretPosition(position);
						}							
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}			
		});
		JScrollPane listPane=new JScrollPane(resultList);
		searchPanel.add(listPane);
		tabs.addTab("Arama", searchPanel);
		
		htmlArea.setEditable(false);
		htmlArea.addHyperlinkListener(new HyperlinkListener () {
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						htmlArea.getHighlighter().removeAllHighlights();
						if(event.getURL().toString().startsWith("mailto"))
							openURL(event.getURL().toString());
						else
						{
							htmlArea.setPage(event.getURL());
							helpPagesViewed.add(event.getURL());
						}
					} catch(IOException ioe) {
						ioe.printStackTrace();
					}					
				}
			}
		});
		htmlArea.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(e.getPropertyName().equals("page"))
				{
					try {
						htmlArea.getHighlighter().removeAllHighlights();
						if(!resultList.isSelectionEmpty())
						{	htmlArea.putClientProperty("done", Boolean.TRUE);
							int index=resultList.getSelectedIndex();
							StringTokenizer st=new StringTokenizer(helpSearchResults.get(index).toString(),"@");
							st.nextToken();
							int position=Integer.parseInt(st.nextToken());
							String searchText=st.nextToken();
							htmlArea.getHighlighter().addHighlight(position, position+searchText.length(), new DefaultHighlightPainter(Color.red.brighter().brighter().brighter().brighter()));
							htmlArea.setCaretPosition(position);
						}
						else
							htmlArea.putClientProperty("done", Boolean.FALSE);
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				}					
			}
		});
		htmlArea.setAutoscrolls(true);
		htmlArea.setDoubleBuffered(true);
		
		try {
			htmlArea.setPage(helpPages.get(0).toString());
			helpPagesViewed.add(new URL(helpPages.get(0).toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		areaPane=new JScrollPane(htmlArea);
				
		split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tabs,areaPane);
		split.setDividerLocation(200);
		split.setDividerSize(1);
		helpContents.add(split,BorderLayout.CENTER);
		
		helpContents.setVisible(true);
	}
	

	private void openURL(String url) {
		String osName = System.getProperty("os.name");
		final String errMsg = "Error attempting to launch web browser";
		try {
			if (osName.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] {String.class});
				openURL.invoke(null, new Object[] {url});
			}
			else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else {
				
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] {"which", browsers[count]}).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] {browser, url});
				}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, errMsg + ":\n" + e.getLocalizedMessage());
		}
	}
	

	private void search(String searchText, JList resultList)
	{
		helpSearchResults.clear();
		resultList.setListData(helpSearchResults.toArray());
		if(searchText.length()<4)
			return;
		FileInputStream file=null;
		HTMLEditorKit htmlEditorKit;
		HTMLDocument document;
		String docText;
		int pos=0;
		try {	
			System.out.println(helpPages.size());
			for(int i=0;i<helpPages.size();i++)
			{
				file=new FileInputStream(helpPages.get(i).toString().substring(helpPages.get(i).toString().indexOf("//")+1));
				htmlEditorKit=new HTMLEditorKit();
				document = (HTMLDocument) htmlEditorKit.createDefaultDocument();
				document.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
				htmlEditorKit.read(file, document, 0);
				docText=document.getText(document.getStartPosition().getOffset(), document.getEndPosition().getOffset());
				pos=0;
				while( (pos=docText.indexOf(searchText, pos+searchText.length()))!=-1)
				{
					helpSearchResults.add(helpPages.get(i).toString()+"@"+pos+"@"+searchText);
					resultList.setListData(helpSearchResults.toArray());
					resultList.update(resultList.getGraphics());
				}
				file.close();				
			}			
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		} finally {
			if(file!=null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private void getAboutHC()
	{
		JPanel p=new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		JLabel hc=new JLabel("<html>" +
				"<center><font color='blue' size='5'>eclipse - Hash Cracker"+"</font></center>" +
				"</html>");
		hc.setBorder(BorderFactory.createEtchedBorder());
		hc.setIconTextGap(25);
		hc.setIcon(iconImage);
		JLabel rest=new JLabel("<html><center>Hash çözümleme aracı<br>" +
				"MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512 Crackleme<br>" +
				"ddevilz.com</center>" +
				"<center>Geliştirici:<br>" +
				"<font size='4' color=#AA0022>Can Özdemir</font></center><br>" +
				"</font>" +
				"</html>");
		p.add(hc);
		p.add(rest);
		JOptionPane.showMessageDialog(me, p, "eclipse - Hash Cracker Hakkında"+version, JOptionPane.PLAIN_MESSAGE);
	}
	

	private void getContactInfo()
	{
		String text = "<html><center><table border='0' cellspacing='0' cellpadding='0'>" +
		"<font face='Comic Sans MS' size='+2'>" +
		"<td width='10'></td>" +
		"<td><b>İletişim <font color='red'>Can Özdemir</font>:<br>" +
		"</font>Skype: can27ozdemir<br>" +
		"<font face='Comic Sans MS'>CoderLab<br> ddevilz.com" +
		"<center><img src='file:///"+new File("images/D.gif").getAbsolutePath()+"'></center>" +
		"</font></td></table>" +
		"</center></html>";
		JEditorPane cinfo=new JEditorPane("text/html", text);
		cinfo.addHyperlinkListener(new HyperlinkListener () {
			public void hyperlinkUpdate(HyperlinkEvent event) {
				if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					openURL(event.getURL().toString());										
				}
			}
		});
		cinfo.setOpaque(false);
		cinfo.setEditable(false);
		cinfo.setPreferredSize(new Dimension(50,150));		
		JOptionPane.showMessageDialog(me, cinfo,"İletişim",JOptionPane.PLAIN_MESSAGE);				
	}
	

	private void exit()
	{
		int option=JOptionPane.showConfirmDialog(me,"Çıkmak istediğinden eminmisiniz?","Bilgilendirme",JOptionPane.OK_CANCEL_OPTION);
		if(option==JOptionPane.OK_OPTION)
		{	dispose();System.exit(0); } 
		else
			status.setText("Çıkış iptal edildi");
	}
	

	private void showSplash(final start hc)
	{
		final JWindow splashWin=new JWindow();
		splashWin.setAlwaysOnTop(true);
		splashWin.setSize(280,160);
		splashWin.setLocationRelativeTo(null);
		
		final JPanel splashPanel=new JPanel();
		splashPanel.setSize(250,130);
		splashPanel.setLayout(new BorderLayout());
		splashPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		final JPanel splash=new JPanel();
		splash.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc=new GridBagConstraints();
		
		gbc.gridx=0;gbc.gridy=0;
		JLabel iconLabel=new JLabel("",iconImage,SwingConstants.CENTER);
		iconLabel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		iconLabel.setHorizontalTextPosition(JLabel.CENTER);
		iconLabel.setVerticalTextPosition(JLabel.CENTER);
		splash.add(iconLabel,gbc);
		
		gbc.gridx++;
		JLabel h=new JLabel("<html>eclipse - Hash Cracker "+"<br></font></html>");
		splash.add(h,gbc);
		
		gbc.gridx=0;gbc.gridy++;
		final JTextField text=new JTextField("Yükleniyor",7);
		text.setEditable(false);
		text.setBorder(BorderFactory.createEmptyBorder());
		text.setFont(new Font("Comic Sans MS",Font.BOLD,12));
		splash.add(text,gbc);
		
		gbc.gridx++;
		final JProgressBar pb=new JProgressBar();
		pb.setMinimum(0);
		pb.setMaximum(100);
		pb.setSize(110,10);
		splash.add(pb,gbc);
		splashPanel.add(splash, BorderLayout.CENTER);
		
		JLabel label=new JLabel("<html><font size='1'> CoderLab" +
								"<font color='blue'> ddevilz.com</font><br><br>" +
								"</font></html>");
		label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
		splashPanel.add(label, BorderLayout.SOUTH);
		
		splashTimer = new Timer(500,new ActionListener() {
			int count=0;
			int progress=0;
			long start = System.currentTimeMillis(),end=0;
			public void actionPerformed(ActionEvent e)
			{
				pb.setValue(progress);
				text.setText(text.getText()+".");
				count++;
				if(count==4)
				{
					text.setText("Yükleniyor");
					count=0;
				}
				end = System.currentTimeMillis();
				progress+=(end-start)/400;
				if(end-start > 6000)
				{
					hc.setVisible(true);
					hc.setEnabled(false);
				}
				if(end-start > 9000)
				{
					endTimer();
					splashWin.dispose();
					hc.setEnabled(true);
				}
			}
		});		
		splashTimer.start();
		splashWin.getContentPane().add(splashPanel);
		splashWin.setVisible(true);
	}
	
	private static void endTimer()
	{
		splashTimer.stop();
	}
	
	private String bruteCrackAlpha(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="a";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='z')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==26)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;			
		}
		return null;
	}
	
	private String bruteCrackAlphaNum(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="a";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			statusProgressBar.setValue(0);
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='9')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==26)
					j[w.length-l]=-49;
				else if(j[w.length-l]==-39)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;
		}
		return null;
	}
	
	private String bruteCrackAlphaNumSymbols(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="[";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			statusProgressBar.setValue(0);
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='@')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==36)
					j[w.length-l]=-58;
				else if(j[w.length-l]==-26)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;
		}
		return null;
	}
	
	private String bruteCrackCaseAlphaNumSymbols(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="!";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			statusProgressBar.setValue(0);
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='@')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==94)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;
		}
		return null;
	}
	
	private String bruteCrackCaseAlpha(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="a";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			statusProgressBar.setValue(0);
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='Z')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==-6)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
				if(j[w.length-l]==26)
					j[w.length-l]=-32;				
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;
		}
		return null;
	}
	
	private String bruteCrackCaseAlphaNum(int size, String hexVal) throws NoSuchAlgorithmException
	{
		int h=0,start=0,end=(int)Math.pow(26, size);
		String word="",out="",hex="";
		for(int i=0;i<size;i++)
			word+="a";
		byte w[]=word.getBytes(),o[]=word.getBytes();
		byte j[]=new byte[size],buffer[]=new byte[1024],digest[]=new byte[1024];
		md=MessageDigest.getInstance(algorithm.getSelectedItem().toString());
		while(h<w.length)
		{
			statusProgressBar.setValue(0);
			for(int l=0;l<w.length;l++)
				o[l]=(byte)(w[l]+j[l]);
			j[w.length-1]++;
			h=0;
			for(int l=0;l<w.length;l++)
				h=(o[l]=='9')?h+1:h-1;
			for(int l=1;l<=w.length;l++)
			{
				if(j[w.length-l]==26)
					j[w.length-l]=-32;
				else if(j[w.length-l]==-6)
					j[w.length-l]=-49;
				else if(j[w.length-l]==-39)
				{
					j[w.length-l]=0;
					if(l<w.length)	j[w.length-l-1]+=1;
				}
			}
			out="";
			for(int l=0;l<w.length;l++)
				out=out+(char)o[l];
			
			buffer=out.getBytes();
			md.update(buffer);
			digest=md.digest();
			hex="";
			for (int i = 0; i < digest.length; i++)
			{
				int b = digest[i] & 0xff;
				if (Integer.toHexString(b).length() == 1) hex = hex + "0";
				hex  = hex + Integer.toHexString(b);
			}
			statusProgressBar.setValue(((++start)*100)/end);
			statusProgressBar.setString(out);
			if(hexVal.equals(hex))
				return out;
		}
		return null;
	}
	
	public static void main(String[] args) {
		new start();
	}
}