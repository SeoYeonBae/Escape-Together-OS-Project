import javax.swing.*;
import java.awt.*;

// ImageIcon에 좌표의 위치를 부여하고자 ImageIcon 클래스를 상속함
public class PosImageIcon extends ImageIcon {
	int pX;				// ImageIcon의 X좌표
	int pY;				// ImageIcon의 y좌표
	int originPX;		// 처음 지정한 좌표
	int originPY;
	int width;			// ImageIcon의 너비
	int height;			// ImageIcon의 높이
	boolean use;		// true = draw , false = draw X
	boolean isHit;		// 캐릭터와 불이 부딪히면 false
	
	public PosImageIcon(String img, int x, int y, boolean u) {
		super(img);
		pX = x;
		pY = y;
		originPX = x;
		originPY = y;
		width = this.getIconWidth();
		height = this.getIconHeight();
		use = u;
		isHit = true;
		
	}
	
	public void setImageIcon(String img) {
		new ImageIcon(img);
	}
	
	public void move(int x, int y) {
		pX += x;
		pY += y;
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pX, pY, width, height, null);
	}
	
	public Point center(PosImageIcon img) {
		return new Point(img.pX+img.width/2, img.pY+img.height/2);
	}
	
	public double distance (Point p1, Point p2) {
		return Math.sqrt(Math.pow((p1.x-p2.x), 2)+ Math.pow((p1.y-p2.y), 2));
	}
	
	public boolean collide (PosImageIcon img, int d) {
		return distance(center(this), center(img)) < d ;
	}
}