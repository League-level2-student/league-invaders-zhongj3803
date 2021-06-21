import javax.swing.JFrame;
import javax.swing.JPanel;

public class LeagueInvaders {
	JFrame frame;
	GamePanel panel;
	public static final int WIDTH=500;
	public static final int HEIGHT=800;
	
	LeagueInvaders (JFrame frame){
		this.frame=frame;
		panel=new GamePanel();
	}
	
	void setup() {
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.setSize(WIDTH,HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		LeagueInvaders leagueInvaders = new LeagueInvaders(new JFrame());
		leagueInvaders.setup();
	}
}