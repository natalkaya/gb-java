package lesson2.MyException;

public class MyArrayDataException extends NumberFormatException {

    public MyArrayDataException() {
        super();
        System.out.println("Cant cast to int");
    }

    @Override
    public String getMessage() {
        super.getMessage();
        return "Cant cast to int";
    }
}
