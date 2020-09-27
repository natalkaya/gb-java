package lesson2.MyException;

import com.sun.javafx.binding.StringFormatter;

public class MyArrayException extends IllegalArgumentException {

    public final static int ARRAY_SIZE = 4;

    @Override
    public String getMessage() {
        super.getMessage();
        return StringFormatter.format("Array size should be %d x %d", ARRAY_SIZE, ARRAY_SIZE).getValue();
    }
}

