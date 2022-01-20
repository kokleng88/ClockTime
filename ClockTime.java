import java.awt.event.*;
import java.awt.*;
import java.awt.FontMetrics;
import javax.swing.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import java.util.Calendar; 
import java.util.logging.Logger; 
import java.lang.*;

class ClockTime extends JFrame 
{
	private static final Logger LOGGER = Logger.getLogger( ClockTime.class.getName() );


	JPanel pnl = new JPanel();
	Font ckFont = new Font("Tahoma", Font.BOLD, 40);	
	BoxLayout boxlayout = new BoxLayout(pnl, BoxLayout.X_AXIS);
	
	Calendar cal = Calendar.getInstance();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime now = LocalDateTime.now();  
		
	Pane hour = new Pane();
    Pane min = new Pane();
    Pane second = new Pane();
	JLabel[] seperator = new JLabel[]{new JLabel(":"), new JLabel(":")};
	
	Boolean clock_mode = true;
	
	public ClockTime()
	{
		super("ClockTime");
		setSize(350,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(pnl);
		setVisible(true);
					
		pnl.setLayout(boxlayout);
		
		hour.setFont(ckFont);		
		pnl.add(hour);
		seperator[0].setFont(ckFont);
		pnl.add(seperator[0]);
		min.setFont(ckFont);
		pnl.add(min);
		seperator[1].setFont(ckFont);
		pnl.add(seperator[1]);
		second.setFont(ckFont);
		pnl.add(second);
		//pnl.setVisible(true);
		
		EventQueue.invokeLater(new Runnable() {
		
			public void terminate(){
				clock_mode = false;
			}
		
            @Override
            public void run() 
			{
				//while (clock_mode)
				//{
                	try {
							new StartClock("start");
							//StartClock.sleep((long) 15000);
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		
				
					} catch (ClassNotFoundException ex) {
                	} catch (InstantiationException ex) {
                	} catch (IllegalAccessException ex) {
                	} catch (UnsupportedLookAndFeelException ex) {
                	} 
					
				//}			
			}
		});
	}

	public class StartClock extends JPanel {

        public StartClock(String arg) {

            final Timer time = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Calendar cal = Calendar.getInstance();
                    hour.setValue(cal.get(Calendar.HOUR_OF_DAY));
                    min.setValue(cal.get(Calendar.MINUTE));
                    second.setValue(cal.get(Calendar.SECOND));

                }
            });
			//time.setRepeats(true);
            //time.setCoalesce(true);
			if (arg=="start" && clock_mode==true)
			{
            	time.start();
				//thread.sleep((long) 15000);
				return;
			}
			else if (arg=="stop")
			{
				time.stop();
				return;
			}
			else return;
        }
    }

	public class Pane extends JPanel 
	{
        private int value;

        @Override
        public Dimension getPreferredSize() {
            FontMetrics fm = getFontMetrics(getFont());
            return new Dimension(fm.stringWidth("00"), fm.getHeight());
        }

        public void setValue(int aValue) {
            if (value != aValue) {
                int old = value;
                value = aValue;
                firePropertyChange("value", old, value);
                repaint();
            }
        }

        public int getValue() {
            return value;
        }

        protected String pad(int value) {
            StringBuilder sb = new StringBuilder(String.valueOf(value));
            while (sb.length() < 2) {
                sb.insert(0, "0");
            }
            return sb.toString();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            String text = pad(getValue());
            FontMetrics fm = getFontMetrics(g.getFont());
            int x = (getWidth() - fm.stringWidth(text)) /2;
            int y = ((getHeight()- fm.getHeight())/2 ) + fm.getAscent();
            g.drawString(text, x, y);
        }       
    }    
	
	public static void main(String[] args)
	{
			new ClockTime();
    }
}
