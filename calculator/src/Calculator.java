import java.awt.*;
import javax.swing.*;


public class Calculator
{
	public static void main(String[] args) {
		JFrame frame = new JFrame("my frame");
		frame.setSize(200, 100);
        // JFrame在屏幕居中
        frame.setLocationRelativeTo(null);
        // JFrame关闭时的操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 显示JFrame
        frame.setVisible(true);
	}
}
