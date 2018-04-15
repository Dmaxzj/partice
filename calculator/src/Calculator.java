import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;


public final class Calculator
{   
    private final int num1 = 12;
    private final int num2 = 3;
    private JTextField op;
    private JTextField result;

    private Calculator() {
    }

    public void showCalculator() {
    //set frame name
    JFrame frame = new JFrame("Easy Calculator");


    //creat buttons
    JButton addBtn = new JButton("+");
    JButton subBtn = new JButton("-");
    JButton mulBtn = new JButton("*");
    JButton divBtn = new JButton("/");
    JButton okBtn = new JButton("OK");


    //add click listener
    addListenerForBtn(addBtn);
    addListenerForBtn(subBtn);
    addListenerForBtn(mulBtn);
    addListenerForBtn(divBtn);

    //add calculate listener
    addCalculateHandle(okBtn);

    //creat text 
    op = new JTextField();
    result = new JTextField();
    JTextField eq = new JTextField("=");
    JTextField firstNum = new JTextField("12");
    JTextField secondNum = new JTextField("3");

    //unable the text editing
    op.setEditable(false);
    eq.setEditable(false);
    firstNum.setEditable(false);
    secondNum.setEditable(false);
    result.setEditable(false);

    //set a 2*5 layout
    frame.setLayout(new GridLayout(2, 5, 5, 5));

    //first row
    frame.add(firstNum);
    frame.add(op);
    frame.add(secondNum);
    frame.add(eq);
    frame.add(result);
    frame.add(addBtn);

    //second row
    frame.add(addBtn);
    frame.add(subBtn);
    frame.add(mulBtn);
    frame.add(divBtn);
    frame.add(okBtn);

    //config the frame
    frame.setSize(500, 500);  
    frame.setLocationRelativeTo(null);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    frame.setVisible(true);

  };

  /*
   *  when a oprator button be clicked, 
   *  showing the operator on [op] text
   */
  private void addListenerForBtn(JButton btn) {
    btn.addActionListener(new ActionListener() {     
      public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        JButton button = (JButton)source;
        op.setText(button.getText());
      }
    });
  };

  /*
   *  calculate the result when [ok] button clicker 
   *  and show the result in [result] text.
   *  if called without [op], show the err massage
   */

  private void addCalculateHandle(JButton btn) {
    btn.addActionListener(new ActionListener() {
      
      public void actionPerformed(ActionEvent e) {
        int num = 0;
      switch(op.getText()) {
        case "+":num = num1+num2;
        break;
        case "-":num = num1-num2;
        break;
        case "*":num = num1*num2;
        break;
        case "/":num = num1/num2;
        break;
        default: result.setText("Error");
        return;
      }
        result.setText(num+"");
      }

    });
  };

  public static void main(String[] args) {
    Calculator c = new Calculator();
    c.showCalculator();
  }
}
