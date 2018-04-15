public class Hello
{ 
  String msg;
  Hello() {
    msg = "Hello World!";
  };
  public String printHello() {
    System.out.println(msg);
    return msg;
  }
  public static void main(String[] args)
  {
      Hello h = new Hello();
      h.printHello();
  }
}
