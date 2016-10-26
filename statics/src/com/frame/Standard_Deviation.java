package com.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Standard_Deviation extends JFrame implements ActionListener{
	
	ArrayList <Integer> average = new ArrayList();
	int CL;
	int too = 0;
	int avl = 0;
	int boo = 0;
	int sequence = 0;
	int fl = 1;
	
	private JPanel dataPanel = new JPanel();
	private JTextField jtf = new JTextField();
	private JTextArea dataTextArea = new JTextArea() ;
	
	// 画布边界宽度，默认占画布宽度的 5%
    private static final double BORDER = 0.05;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    
 // 用于标识是否立即绘制画面，或者等待到下一次绘制时
    private boolean defer = false;
    
 // 默认的画布大小
    private static final int DEFAULT_SIZE = 512;
    
    // 画布大小
    private int width  = DEFAULT_SIZE;
    private int height = DEFAULT_SIZE;
 
    private double xmin, ymin, xmax, ymax;
    
	 private Graphics2D offscreen;
	 
	 int flag = 1;

	
	private static final int MAX_SAMPLES = 10000;
    private int index = 1;
    private int[] xrais = new int[MAX_SAMPLES];
    private int[] yrais = new int[MAX_SAMPLES];
    private int[] jicha = new int[MAX_SAMPLES];
    DateFormat fmt = DateFormat.getDateTimeInstance();
    int xt;
    int yt;
    
    JButton button = new JButton("输入");
    
    String result;
    
	public Standard_Deviation() throws HeadlessException {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		this.setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Standard_Deviation dw1 = new Standard_Deviation();
		
	}
	
	private void initComponents() {

        dataPanel.setBackground(new Color(255, 255, 255));
        dataPanel.setMinimumSize(new Dimension(400, 250));
        dataPanel.setPreferredSize(new Dimension(400, 250));
        getContentPane().add(dataPanel, BorderLayout.NORTH);
        getContentPane().add(jtf, BorderLayout.CENTER);
        button.setForeground(Color.red);
        button.addActionListener(this);
        getContentPane().add(button, BorderLayout.SOUTH);

        pack();
    }// //GEN-END:initComponents
	
	 public void paint(Graphics g) {
		 super.paint(g);
		 int left = dataPanel.getX() + 20;       // get size of pane
	        int top = dataPanel.getY() + 50;
	        int right = left + dataPanel.getWidth() - 20;
	        int bottom = top + dataPanel.getHeight() - 20;

	        int y0 = bottom - 20;                   // leave some room for margins
	        int yn = top;
	        int x0 = left + 33;
	        int xn = right;
	        double vscale = (yn - y0) / 1000000000.0;      // light values range from 0 to 800
	        double tscale = 1.0 / 2000.0;    
	        g.setColor(Color.BLACK);
	        //横
	        g.drawLine(x0, y0, xn, y0);
	        int tickInt = 60 / 2;
	        for (int xt = x0 + tickInt; xt < xn; xt += tickInt) {   // tick every 1 minute
	            g.drawLine(xt, y0 + 2, xt, y0 - 2);
	            int min = (xt - x0) / (60 / 2);
	            g.drawString(Integer.toString(min), xt - (min < 10 ? 3 : 7) , y0 + 20);
	        }
	        //纵
	      
	        g.drawLine(x0, yn, x0, y0);
	        g.drawString("19.00", x0 - 38 , 260);
	        g.drawString("20.68", x0 - 38 , 92);
	   
				 if(flag == 0){
					 int xp = x0+30;
				      
				        for (int i = 0; i < index; i++) {
				        	//System.out.println("i:"+i);
				        	if(i>1)
				        	{
				        		xp = x0 + (int)(xrais[i-1]);
				        		int x = x0 + (int)(xrais[i]);
				        		g.setColor(Color.BLUE);
				        		int vp =  260 - (yrais[i-1]-190000000)/100000;
				        		int v =  260 - (yrais[i]-190000000)/100000;
				        		
					            g.drawLine(xp, vp, x, v);
				        	}
				            
				            if (xp > 0) {
				               g.setColor(Color.RED);    
				               int qtoo = 260 - (too-190000000)/100000;
				               g.drawLine(x0, qtoo, xn, qtoo);
//				               System.out.println("qtoo:"+qtoo);
//				               System.out.println("too:"+too);
				               
				               int qboo = 260 - (boo-190000000)/100000;
				               g.drawLine(x0, qboo, xn, qboo);
//				               System.out.println("qboo:"+qboo);
//				               System.out.println("boo:"+boo);
				               
				               int qavl = 260 - (avl-190000000)/100000;
				               g.drawLine(x0, qavl, xn, qavl);
//				               System.out.println("qavl:"+qavl);
//				               System.out.println("avl:"+avl);
//				               fl = 0;19,19.9				            				              
				            }
				        }
				 }
		 }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 String s = e.getActionCommand();
		 if(!s.equals("输入")){
			    jtf.setText(jtf.getText()+s);
			  System.out.println("1");
			    result = jtf.getText();
			 }else if(s.equals("输入")){
				
				 //System.out.println("2");
				 String str = jtf.getText();
				 calculate(ca1(str));
				 yt = average.get(sequence);
				 int jichat = too-boo;
				 sequence++;
				 addData(xt, yt ,jichat);
				 repaint();
				 xt = xt+30;
				 flag = 0;
				 jtf.setText("");
			 }
		 }

	
	private int ca1(String str) {
		// TODO Auto-generated method stub
		double dataaverage1 = 0;
		double biaozhuncha = 0;
		int dataaverage = 0;
		double []data = new double[100];
		String []s = new String[100];
		int total = 0;
		double sum = 0;
		double sum1 = 0;
		s = str.split(",");
		for(int i = 0;i<s.length;i++){
			data[i] = Double.parseDouble(s[i]);
			sum = sum+10000000*data[i];
			total++;
		}
		
		dataaverage = (int) (sum/total);
		//System.out.println("dataaverage"+dataaverage);
		return dataaverage;
		
	}
  
	public void calculate(int d){
		too = 0;
	    avl = 0;
		boo = 0;
		average.add(d);
		long ttcl = 0;
		int to = 0;
		String []tem = new String[100];
 		int []temp = new int[100];
		for(int i = 0;i<average.size();i++){
			tem[i] =  average.get(i).toString();
			temp[i] = Integer.parseInt(tem[i]);
			avl += temp[i];
			if(temp[i] != 0){
				to++;
			}
		}
		
		avl = avl/to;
		for(int i = 0;i<average.size();i++){
	//		ttcl += (temp[i]-avl)*(temp[i]-avl);
			long a=temp[i]-avl;
			ttcl += a*a;
		}
		ttcl = ttcl/to;
		CL = (int) Math.sqrt(Math.abs(ttcl));
		
		too = avl+3*CL;
		boo = avl-3*CL;
		System.out.println("实验：");
		System.out.println("avl:"+avl);
		System.out.println("too:"+too);
		System.out.println("boo:"+boo);

		for(int i = 0;i<average.size();i++){
			if(average.get(i)>too || average.get(i)<boo){
			System.out.println("失控点："+"第"+i+"个样本"+" "+"平均值:"+average.get(i)/10000000);
			System.out.println("zy:aaaaaaa");
			}
		}
  }

    public void addData(int t, int v ,int jic) {
        xrais[index] = t;
        yrais[index++] = v;
        jicha[index] = jic;
    }
}
